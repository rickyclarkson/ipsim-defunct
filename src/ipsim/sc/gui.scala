package ipsim.gui

import ipsim.awt.Point
import ipsim.awt.Implicits._
import java.awt.{Container, BorderLayout, Frame, Cursor}
import java.awt.event.{MouseListener, MouseMotionListener, KeyEvent}
import javax.swing.{ JFrame,JOptionPane, JLabel, JButton, JFileChooser, JTabbedPane, JPanel, JScrollPane, JMenuBar, ImageIcon, JMenu, KeyStroke,
                     JTextArea, ProgressMonitor, SwingWorker }
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.event.{ChangeListener, ChangeEvent}
import ipsim.network.Network
import java.io.File
import ipsim.swing.{CustomJOptionPane, Dialogs}
import ipsim.io.IOUtility
import ipsim.swing.Buttons
import ipsim.network.connectivity.ConnectivityTest
import ipsim.network._

object Global {
 implicit val random = new Random
 val frame: JFrame = MainFrame()
 val tabbedPane = TabbedPane()
 tabbedPane.addChangeListener(new ChangeListener { def stateChanged(e: ChangeEvent) =
  if (tabbedPane.wrapped.getSelectedIndex >= 0) statusBar.setText(networkContext.problem.map(_.toString).getOrElse("No problem set")) } )
 val statusBar = new JLabel
 val editProblemButton = new JButton("Edit Problem") { setFocusable(false) }
 val network = Network()
 val fileChooser = new JFileChooser { setFileFilter(new FileNameExtensionFilter("IPSim files", Array("ipsim"))) }
 var filename: Option[File] = None
 def filename_(newValue: Option[File]) = { filename = newValue
                                           frame setTitle (filename map (_.getName) getOrElse "Untitled") }
 def networkContext = network.contexts.stream(tabbedPane selectedIndex)
 def networkView = tabbedPane.selectedComponent.asInstanceOf[NetworkView]
 val appVersion = "1.7"
 val defaultTimeToLive = 30
 val customerNumber = "100" }

object UserMessages {
 def message(message: String) = JOptionPane.showMessageDialog(Global.frame, message)
 def error(message: String) = JOptionPane.showMessageDialog(Global.frame, message, message, JOptionPane.ERROR_MESSAGE)
 def confirm(message: String) = JOptionPane.showConfirmDialog(Global.frame, message, message, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION }

sealed abstract class UserPermissions(val allowClearingLog: Boolean, val allowDownloadingNewConfig: Boolean, val allowFullTests: (Boolean, String),
                                      val allowBreakingNetwork: Boolean)

case object Freeform extends UserPermissions(true, true, (true, ""), false)
case object PracticeTestSimulatingActualTest extends UserPermissions(false, false,
                                                                     (false, "Not allowed during a practice test (duplicating test conditions)"), false)
case object PracticeTest extends UserPermissions(true, true, (true, ""), false)
case object ActualTest extends UserPermissions(false, false, (false, "Not allowed during a test"), false)

object GetChildOffset {
 import ipsim.network.{PacketSource,Network, Card, Computer, Cable, Hub}
 def getChildOffset(parent: PacketSource, child: PacketSource)(implicit network: Network) = {

  implicit def pair2Mappable[T, U](pair: (T, U)) = new {
   def map[F, G](f: T => F, g: U => G) = (f(pair._1), g(pair._2))
   def fold[R](f: (T, U) => R) = f(pair._1, pair._2) }

  parent match { case card: Card => Point.origin
                 case computer: Computer => {
                  def by[T](f: T => Int) = ((x: T), (y: T)) => f(x)>f(y)
                  val cards=computer.cards.partition(_.withDrivers.isDefined).map(_.toList.sort(by(_.withDrivers.get.ethNumber)),(x => x)).fold(_++_)
                  val index=cards.indexOf(child.asInstanceOf[Card])
                  val angle=index*2*java.lang.Math.PI/cards.size
                  Point(50*Math.cos(angle),-50*Math.sin(angle)) }
                 case cable: Cable => throw null
                 case hub: Hub => Point.origin } } }

case class TabbedPane {
 val wrapped = new JTabbedPane
 def selectedIndex = wrapped.getSelectedIndex
 def selectedComponent = wrapped.getSelectedComponent.asInstanceOf[Container] find (_.isInstanceOf[NetworkView]) map (_.asInstanceOf[NetworkView])
 def indexOfComponent(view: NetworkView) =
  0 until wrapped.getTabCount find (a => wrapped.getComponent(a).asInstanceOf[Container].find(_ match { case nv: NetworkView => nv == view
                                                                                                        case _ => false } ).isDefined) get
 def setSelectedComponent(view: NetworkView) = wrapped setSelectedIndex indexOfComponent(view)
 def tabCount = wrapped.getTabCount
 def addTab(title: String, view: NetworkView) = wrapped.addTab(title, new JScrollPane(view))
 def removeAll = wrapped.removeAll
 def addChangeListener(changeListener: ChangeListener) = wrapped addChangeListener changeListener }

case class MainFrame(implicit private val random: Random) extends JFrame {
 implicit val boilerplate: JFrame = this
 import java.lang.System.setProperty
 import ipsim.swing.{Bar, Menu, Item}
 setProperty("swing.aatext", "true")
 setProperty("apple.laf.useScreenMenuBar", "true")
 setTitle("IPSim")
 val rootPanel = new JPanel(new BorderLayout)
 setSize(800, 600)
 setExtendedState(Frame.MAXIMIZED_BOTH)
 setIconImage((new ImageIcon(classOf[MainFrame] getResource "/images/icon.png")).getImage)
 Bar(this) { Menu("Help") mnemonic 'H' apply
             { Item("Contents") mnemonic 'C' listener (MenuHandler.helpContents _) accelerator KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0) } apply
             { Item("About") mnemonic 'A' listener (() => MenuHandler.helpAbout) } }
           { Menu("Tools") mnemonic 'T' apply
             { Item("Test Connectivity") mnemonic 'C' accelerator KeyEvent.VK_C listener (MenuHandler.testConnectivity _) } apply
             { Item("Test Conformance") mnemonic 'F' accelerator KeyEvent.VK_F listener (() => MenuHandler.testConformance) } apply
             { Item("Clear All ARP Tables") mnemonic 'A' listener (MenuHandler.clearAllArpTables _) } } }

import java.awt.Dimension          
import java.awt.event.MouseEvent
trait ToggleListeners { def off: Unit
                        def on: Unit }

import UserMessages._
case class NetworkView extends JPanel {
 var ignorePaints: Boolean = false
 def ignorePaints_(b: Boolean) = { ignorePaints = b
                                   repaint() }
 val toggleListeners: ToggleListeners = new ToggleListeners { var mouseListeners: Array[MouseListener] = null
                                                              var motionListeners: Array[MouseMotionListener] = null
                                                              def off = { mouseListeners = getMouseListeners
                                                                          motionListeners = getMouseMotionListeners
                                                                          mouseListeners foreach removeMouseListener
                                                                          motionListeners foreach removeMouseMotionListener }
                                                              def on = { mouseListeners foreach addMouseListener
                                                                         motionListeners foreach addMouseMotionListener } }

 def mouseTracker: MouseTracker = new MouseTracker { var position: Option[Point] = None
                                                     var lastMousePressedEvent: Option[MouseEvent] = None
                                                     def mouseEvent(event: MouseEvent) = { position = Some(Point(event.getX, event.getY))
                                                                                           if (MouseEvent.MOUSE_PRESSED == event.getID)
                                                                                            lastMousePressedEvent = Some(event) } }

 def unzoomedPreferredSize(implicit network: Network) = { val iterable = network.depthFirstIterable
                                                          val visibleSize = getVisibleRect.getSize
                                                          var maximumX = visibleSize.width
                                                          var maximumY = visibleSize.height
                                                          
                                                          for { component <- iterable
                                                                position <- Positions.positions(component) } {
                                                           if (position.x > maximumX) maximumX = position.x.toInt
                                                           if (position.y > maximumY) maximumY = position.y.toInt }
                                                          new Dimension(maximumX, maximumY) } }

trait MouseTracker { var position: Option[Point]
                     var lastMousePressedEvent: Option[MouseEvent]
                     def mouseEvent(event: MouseEvent): Unit }

import ipsim.network.conformance.ConformanceTests
object MenuHandler {
 import ipsim.network.{NetworkContext, Network}
 import Global.{network, tabbedPane, editProblemButton, frame, filename, fileChooser}
 val networkContext = Global.networkContext
 implicit val boilerplate = frame
 def networkNew(implicit random: Random) = { val context = NetworkContext()
                                             network.contexts append context
                                             val view = NetworkView()
                                             tabbedPane.addTab("Network "+(tabbedPane.tabCount + 1), view)
                                             tabbedPane setSelectedComponent view
                                             editProblemButton setText "Edit Problem"
                                             InitialDialog.initialDialog(random, frame, network) setVisible true
                                             frame.repaint() }
 def fileOpen(implicit random: Random) = 
  if ((!network.modified || networkModifiedDialog) && fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
   network.loadFromFile(fileChooser.getSelectedFile)
   tabbedPane.removeAll
   for ((context, a) <- network.contexts.stream.zipWithIndex)
    tabbedPane.addTab("Network "+(a+1), NetworkView())
   Global.filename=filename
   frame.repaint() }

 def fileClose(implicit random: Random) = { tabbedPane.removeAll
                                            network.contexts.clear
                                            network.clearAll
                                            network.modified = false
                                            network.zoomLevel = 1.0
                                            network.log = Nil
                                            Global.filename = None
                                            networkNew }
 import java.io.IOException
 def fileSave: Unit = filename match { case Some(filename) => network.saveToFile(filename)
                                       case None => fileSaveAs }

 def fileSaveAs: Unit = if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
  val filename = fileChooser.getSelectedFile
  if (!filename.exists || CustomJOptionPane.showYesNoCancelDialog("This file exists.  Overwrite?", "Overwrite?") == JOptionPane.YES_OPTION) {
   network.saveToFile(filename)
   Global.filename = Some(filename) } }

 def fileExit = if (!(network.modified && !networkModifiedDialog)) { frame setVisible false
                                                                     frame.dispose
                                                                     System exit 0 }

 def clearAllArpTables = { network.log += "Cleared all ARP tables." 
                           network.all flatMap (_.asComputer) foreach (_.arpTable.clear) }
 def testConnectivity = ConnectivityTestDialog()
 def testConformance = {
  if (!network.userPermissions.allowFullTests._1) { NetworkContext.errors(network.userPermissions.allowFullTests._2) } else {
   val results = new ConformanceTests()(network).allChecks
   val message = networkContext.problem match {
    case None => "No problem is set, so some checks cannot be performed\n\n" + results.percent + "%\n\n" + results.summary
    case Some(problem) => results.percent + "% Conformance\n\n" + results.summary }
   JOptionPane.showMessageDialog(frame, message, "Conformance Test", if (results.percent == 100) JOptionPane.INFORMATION_MESSAGE
                                                                     else JOptionPane.WARNING_MESSAGE) } }
 def helpContents = HelpFrame() setVisible true
 def helpAbout = CustomJOptionPane.showNonModalMessageDialog("About IPSim",
                                                             """IPSim is a network simulator, for teaching and assessment of
                                                             skills in static subnetting.<p><p>Version """+Global.appVersion+
                                                             "<p>Build date: "+InitialDialog.version+
                                                             """<p>Design by Andrew Young and Ricky Clarkson<p>Programming by
                                                             Ricky Clarkson (contributions from Andrew Young)<p><p>(c) University
                                                             of Salford 2002-2008""")
 def practiceTest(implicit random: Random) = {
  val result = CustomJOptionPane.showLabelsAndConfirmation(frame, "Select a difficulty level", "Select a difficulty level",
                                                           Array("Easy", "Medium", "Hard"), 1, "Duplicate Test Conditions")
  if (result.choice == null) networkNew else if (result.confirmationTicked) {
   editProblemButton setText "Upload Solution"
   network.userPermissions = PracticeTestSimulatingActualTest } else network.userPermissions = PracticeTest

  import ipsim.network.ProblemDifficulty
  networkContext.problem = Some(ProblemDifficulty.valueOf(result.choice.toUpperCase))
  JOptionPane.showMessageDialog(frame, networkContext.problem.toString) }

 import ipsim.web.Web
 import ipsim.network.{IPAddress, Problem, NetBlock}
 def loadAssessmentProblem(implicit random: Random) = {
  val testNumber = JOptionPane.showInputDialog(frame, "Enter the test number given to you by your tutor")
  if (testNumber != null && testNumber.length!=0) {
   network.testName = Some(testNumber)
   Web.getProblem(testNumber).map(string => {
    val strings = string split "\n"
    val netSizesString = strings(1).split("=")(1)
    val netSizes = netSizesString split ","
    val chosenSize = netSizes((Math.random * netSizes.length).toInt)
    val subnetOptionString = strings(2).split("=")(1)
    val subnetOptions = subnetOptionString split ","
    val chosenSubnetOption = subnetOptions((Math.random * subnetOptions.length).toInt)
    var generateNetworkNumber: IPAddress = null
    var giveUp = 0
    do { giveUp += 1
         generateNetworkNumber = Problem.generateNetworkNumber(random, Integer.parseInt(chosenSize)) }
    while (giveUp<100 && 0==(generateNetworkNumber.rawValue & 0xFF00))

    val mask = NetMask.fromPrefixLength(Integer.parseInt(chosenSize)).get
    val problem = Problem(NetBlock(generateNetworkNumber, mask), Integer.parseInt(chosenSubnetOption))
    networkContext.problem = Some(problem)
    network.userPermissions = ActualTest
    var set = false

    do { val userName = JOptionPane.showInputDialog(frame, "Please enter your University email address, e.g., N.Other@student.salford.ac.uk")
         if (userName != null && !(0==userName.length)) { network.emailAddress = Some(userName)
                                                          Web.putSUProblem(userName, problem.toString)
                                                          set = true } } while (!set) } ) } }
 import ipsim.lang.Implicits._
 def networkModifiedDialog = {
  val result = CustomJOptionPane.showYesNoCancelDialog (
   (if (network.userPermissions == ActualTest)
    """<html>CLOSING THE NETWORK WITHOUT UPLOADING WILL CAUSE YOU TO LOSE YOUR WORK.
    <br>CLICK CANCEL, THEN UPLOAD SOLUTION.<br>IF YOU ARE UNSURE, RAISE YOUR HAND.<br><br>""" else "") + "This network has been modified.  Save changes?",
   "Confirm Lose Data?")
  if (result == JOptionPane.YES_OPTION) fileSave then !network.modified else result != JOptionPane.CANCEL_OPTION }

 import Global.networkView
 def zoomOut = network.zoomLevel /= 1.1 then networkView.repaint()
 def zoomIn = network.zoomLevel *= 1.1 then networkView.repaint()
 def zoomToFit = { val view = Global.networkView
                   val optimumSize = view.unzoomedPreferredSize(Global.network)
                   val actualSize = view.getVisibleRect.getSize
                   val zoomFactorX = optimumSize.width.toDouble / actualSize.width.toDouble
                   val zoomFactorY = optimumSize.height.toDouble / actualSize.height.toDouble
                   network.zoomLevel = 0.9 / Math.max(zoomFactorX, zoomFactorY)
                   view.repaint() }
 def zoomOneToOne = { network.zoomLevel = 1.0
                      networkView.repaint() }

 def downloadConfiguration = { val name = { def getName: String = { val got = JOptionPane.showInputDialog(frame, "Enter the name of the configuration")
                                                                    if (got == null) null else if (got.length == 0) getName else got }
                                            getName }
                               val namedConfig = Web getNamedConfiguration name
                               namedConfig.fold(p => { val tmp = networkView.ignorePaints
                                                       try { networkView.ignorePaints = true
                                                             network loadFromString p._2 } finally { networkView.ignorePaints = tmp }
                                                             frame setTitle ("IPSim - " + p._1) }, reason => error(reason)) } }

import java.awt.{Toolkit, FlowLayout}
import ipsim.swing.ScrollableEditorPane
import ipsim.swing.{Panel, Button}
case class HelpFrame extends JFrame { setSize(Toolkit.getDefaultToolkit.getScreenSize.width, 600)
                                      setLocationRelativeTo(null)
                                      setTitle("IPSim Help")
                                      setLayout(new BorderLayout)
                                      val pane = new JScrollPane
                                      val htmlPane = ScrollableEditorPane(pane)
                                      htmlPane.left.map(pane2 => { pane2 setEditable false
                                                                   pane2 setCaretPosition 0
                                                                   val hyperactive=new Hyperactive(pane2)
                                                                   pane2 addHyperlinkListener new Hyperactive(pane2)
                                                                   pane2 setAutoscrolls true
                                                                   pane.getViewport add pane2
                                                                   add(pane, BorderLayout.CENTER)
                                                                   Panel(this, BorderLayout.NORTH, new FlowLayout(FlowLayout.LEFT))(
                                                                    Button("Contents") listener hyperactive.goHome _)(
                                                                    Button("Back") listener hyperactive.back _) } ) }
                                                                   
                                                                   
import javax.swing.event.{HyperlinkEvent,HyperlinkListener}
import javax.swing.text.html.{HTMLDocument,HTMLFrameHyperlinkEvent}

class Hyperactive(editorPane: javax.swing.JEditorPane) extends javax.swing.event.HyperlinkListener {
 val history=new java.util.Stack[java.net.URL]
 def goHome=editorPane.setPage(classOf[Hyperactive].getResource("/help/index.html"))
 def back=if (!history.empty) editorPane.setPage(history.pop)
 def hyperlinkUpdate(event: HyperlinkEvent)=if (event.getEventType==HyperlinkEvent.EventType.ACTIVATED)
                                             event match { case event2: HTMLFrameHyperlinkEvent => 
                                                            editorPane match { case document: HTMLDocument =>
                                                                                editorPane.asInstanceOf[HTMLDocument].processHTMLFrameHyperlinkEvent(event2) }
                                                           case _ => { history.push(editorPane.getPage)
                                                                       editorPane.setPage(event.getURL) } } }
import java.lang.Integer
import ipsim.swing.Swing.ButtonGroup
object InitialDialog {
 val version = IOUtility readWholeResource InitialDialog.getClass.getResource("/timestamp").toString
 import javax.swing.JDialog

 def initialDialog(implicit random: Random, frame: JFrame, network: Network): JDialog = {
  val dialog = new JDialog(frame)
  val contentPane = new JLabel(new ImageIcon(classOf[Object] getResource "/images/initial-dialog-background.jpg"))
  dialog setContentPane contentPane

  import ipsim.swing.{Label, RadioButton}
  val introLabel = Label(<html><body><center>
                           <h1>Welcome to IPSim { Global.appVersion } </h1>
                           <p>IPSim is Copyright 2008 University of Salford.  All Rights Reserved.<br/>
                              (Registered to the University of Salford)<br/>
                              Please choose from the following options, or click on the Help button.<br/>
                              Build-date { version }</p>
                          </center></body></html>.toString) horizontalAlignment javax.swing.SwingConstants.CENTER
  val freeformRadioButton = RadioButton("Practice") mnemonic KeyEvent.VK_P opaque false selected true
  val font = freeformRadioButton.getFont.deriveFont(14F)
  freeformRadioButton setFont font
  
  val takeTestRadioButton = RadioButton("Actual Setup Test") mnemonic KeyEvent.VK_T font font opaque false
  val practiceTroubleshootingTest = RadioButton("Practice Troubleshooting Test") opaque false font font
  val actualTroubleshootingTest = RadioButton("Actual Troubleshooting Test") opaque false font font
  val practiceTestRadioButton = RadioButton("Practice Setup Test") font font mnemonic KeyEvent.VK_C opaque false
  val okButton = Button(" OK ") mnemonic KeyEvent.VK_O
  val helpButton = Button(" Help ") mnemonic KeyEvent.VK_H
  val cancelButton = Button(" Exit ") mnemonic KeyEvent.VK_X listener (MenuHandler.fileExit _)
  val padding = 10

  AnyLayout.useAnyLayout(contentPane, 0.5F, 0.5F, new SizeCalculator {
   def getHeight = (List(introLabel, freeformRadioButton, practiceTestRadioButton, takeTestRadioButton, okButton) map
    (_.getPreferredSize.height) reduceLeft ((_: Int)+(_: Int)))+padding*5
   def getWidth = {
    val widestRadioButtonOnLeft = List(freeformRadioButton, practiceTestRadioButton) map (_.getPreferredSize.width) reduceLeft Math.max
    val widestRadioButtonOnRight = List(practiceTestRadioButton, practiceTroubleshootingTest) map (_.getPreferredSize.height) reduceLeft Math.max
    List(padding * 2 + introLabel.getPreferredSize.width, padding * 3 + widestRadioButtonOnLeft + widestRadioButtonOnRight,
         padding * 4 + List(okButton, helpButton, cancelButton).map(_.getPreferredSize.width).reduceLeft(_+_)) reduceLeft Math.max } },
                         ConstraintUtility typicalDefaultConstraint new Runnable { def run = throw null } )
  val constraints = PercentConstraintsUtility.newInstance(contentPane)
  val introLabelConstraint = ConstraintUtility topCentre padding
  contentPane add (introLabel, introLabelConstraint)
  val paddingFunction = fpeas.function.FunctionUtility.constant[LayoutContext, Integer](padding)
  val freeformConstraint = ConstraintBuilder.buildConstraint setLeft paddingFunction setTop (new fpeas.function.Function[LayoutContext, Integer] {
   def run(layoutContext: LayoutContext) = layoutContext.getLayoutInfo(introLabel).getFarOffset.intValue + padding * 3 }
                                                                                            ) setWidth preferredSize setHeight preferredSize
  contentPane add (freeformRadioButton, freeformConstraint)
  val practiceTestConstraints = RelativeConstraints below (freeformRadioButton, padding)
  contentPane add (practiceTestRadioButton, practiceTestConstraints)
  contentPane add (takeTestRadioButton, RelativeConstraints.below(practiceTestRadioButton, padding))
  val takeTestConstraints = ConstraintBuilder.buildConstraint setLeft
   new fpeas.function.Function[LayoutContext, Integer] {
    def run(layoutContext: LayoutContext) = layoutContext.getParentSize.intValue - 10 - layoutContext.getPreferredSize.intValue } setTop
   new fpeas.function.Function[LayoutContext, Integer]{ def run(layoutContext: LayoutContext) = layoutContext.getLayoutInfo(practiceTestRadioButton).getOffset } setWidth preferredSize setHeight preferredSize

  contentPane add (practiceTroubleshootingTest, takeTestConstraints)
  contentPane add (actualTroubleshootingTest, RelativeConstraints below (practiceTroubleshootingTest, padding))
  val group = ipsim.swing.Swing.ButtonGroup(freeformRadioButton, practiceTestRadioButton, takeTestRadioButton)

  constraints add (okButton, 10, 85, 15, 10, false, false)

  okButton.listener(() => {
   val doLater = if (practiceTestRadioButton isSelected) (() => MenuHandler.practiceTest) else
    if (takeTestRadioButton isSelected) () => MenuHandler.loadAssessmentProblem else
     if (practiceTroubleshootingTest isSelected) () => JOptionPane.showMessageDialog(frame,"Practice Troubleshooting Test not yet supported") else
      if (actualTroubleshootingTest isSelected) () => JOptionPane.showMessageDialog(frame, "Actual Troubleshooting Test not yet supported")
      else () => network.userPermissions = Freeform

   dialog setVisible false
   dialog.dispose
   doLater.apply })
  constraints add (helpButton, 40, 85, 20, 10, false, false)
  helpButton listener (() => { dialog.dispose
                               network.userPermissions = Freeform
                               MenuHandler.helpContents } )
  constraints.add (cancelButton, 70, 85, 20, 10, false, false)
  dialog setResizable false
  dialog.pack
  dialog centreOnParent frame

  dialog } }

import ipsim.swing.SwingUtilities.invokeLater
import ipsim.lang.Implicits._
object ConnectivityTestDialog { def apply() = {
 implicit val frame = Global.frame
 val dialog = Dialogs.createDialogWithEscapeKeyToClose("Connectivity Test")
 dialog.setSize(700, 400)
 AnyLayout.useAnyLayout(dialog.getContentPane, 0.5f, 0.5f, absoluteSize(700, 400), null)
 val constraints = PercentConstraintsUtility.newInstance(dialog.getContentPane)
 dialog.centreOnParent(frame)
 val resultsLabel = new JLabel
 constraints.add(resultsLabel, 5, 5, 90, 5, false, false)
 constraints.add(new JLabel("Connectivity Problems:"), 5, 15, 90, 5, false, false)
 val problemList = new JTextArea
 constraints.add(new JScrollPane(problemList), 5, 25, 90, 60, true, true)
 constraints.add(Buttons.closeButton("Close", dialog), 80, 90, 15, 6, true, true)
 val original = frame.getCursor
 frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR))
 val monitor = new ProgressMonitor(frame, "Testing connectivity                ", "Testing connectivity             ", 0, 100)
 monitor setMillisToPopup 0
 monitor setMillisToDecideToPopup 0
 monitor setProgress 1
 implicit val network = Global.network

 new SwingWorker[Unit, Object] { override def doInBackground = try { val results = ConnectivityTest.testConnectivity(monitor.setNote _,
                                                                                                                     monitor.setProgress _)
                                                                     problemList setText results.outputs.reduceLeft(_ + "\n" + _)
                                                                     resultsLabel setText results.percentConnected+"% connected."
                                                                     network.log += "Tested the connectivity, "+resultsLabel.getText
                                                                     problemList setText results.outputs.foldRight("")(_ + '\n' + _)
                                                                     invokeLater {
                                                                      monitor setProgress 100
                                                                      monitor.close
                                                                      dialog setVisible true } } catch { case e: RuntimeException =>
                                                                       invokeLater(throw e) } finally { frame setCursor original } then null }.execute } }

object NetworkView {
 def pointAt(x: Int, y: Int)(implicit network: Network, context: NetworkContext) = {
  val iterable = network.depthFirstIterable(context)
  var answer: PacketSource = null
  var distance = java.lang.Double.MAX_VALUE
  for (next <- iterable) { val middle = Positions.centreOf(next)
                           val xDistance = middle.x - x
                           val yDistance = middle.y - y
                           val newDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance)
                           if (newDistance < distance) { distance = newDistance
                                                         answer = next } }
  answer }

 def topLevelPointAt(x: Int, y: Int)(implicit network: Network, context: NetworkContext) = {
  var answer: Option[(PacketSource, Int)] = None
  var distance = java.lang.Double.MAX_VALUE
  for { (nextObject, a) <- network.depthFirstIterable(context).toList.zipWithIndex
        position <- nextObject.positions
        if position.isLeft
        point = Positions.position(position, nextObject)
        xDistance = point.x - x
        yDistance = point.y - y
        newDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance)
        if newDistance < distance } { distance = newDistance
                                     answer = Some(nextObject, a) } 
  answer }

 import java.awt.Dimension
 def preferredSize(implicit network: Network) = {
  var maximumX = 0
  var maximumY = 0
  for { component <- network.depthFirstIterable
        position <- Positions.positions(component) } { if (position.x + 200 > maximumX) maximumX = (position.x + 200).toInt
                                            if (position.y + 200 > maximumY) maximumY = (position.y + 200).toInt }
  new Dimension((maximumX*network.zoomLevel).toInt, (maximumY*network.zoomLevel).toInt) } }

import ipsim.lang.{Runnables, Throwables}
import ipsim.web.Web
import ipsim.awt.Components
object ExceptionReportDialog { def handle(exception: Throwable)(implicit frame: JFrame, network: Network) = {
 val dialog = Dialogs.createDialogWithEscapeKeyToClose("Error Report")
 dialog setModal true
 dialog setTitle "Error Report"
 val constraints = PercentConstraintsUtility newInstance dialog.getContentPane
 AnyLayout useAnyLayout (dialog.getContentPane, 0.5F, 0.5F, constraints.getSizeCalculator,
                         ConstraintUtility typicalDefaultConstraint Runnables.throwRuntimeException)
 constraints add (new JLabel("An error has been detected."), 10, 10, 90, 5, false, false)
 constraints add (new JLabel("You can ignore it, upload the event log to our servers, or quit"), 10, 20, 90, 5, false, false)
 constraints add (new JLabel("Error Summary:"), 10, 30, 30, 5, false, false)
 constraints add (new JLabel("Details:"), 10, 40, 80, 5, false, false)

 val details = new JTextArea(Throwables.asString(exception))
 details setEditable false
 details setCaretPosition 0

 constraints add (new JScrollPane(details), 10, 45, 80, 30, true, true)
 constraints add (Buttons.closeButton("Ignore", dialog), 10, 80, 20, 10, false, false)
 constraints add (new Button("Upload").listener(() => Web putException (frame, Throwables.asString(exception), network.saveToString)),
                  40, 80, 30, 10,false, false)
 constraints add (new Button("Quit").listener(() => MenuHandler.fileExit), 80, 80, 15, 10, false, false)

 dialog setSize (3 * frame.getWidth / 4, 3 * frame.getHeight/4)
 Components centreOnParent (dialog, frame)
 dialog setVisible true } }

import javax.swing.JPopupMenu
object CreateContextMenu {
 def createContextMenu(component: PacketSource)(implicit frame: JFrame, network: Network, context: NetworkContext, view: NetworkView): JPopupMenu =
  component.fold(card => CardHandler.createContextMenu(frame, network, view, card),
                 computer => ComputerHandler.createContextMenu(view, context, computer),
                 cable => EthernetCableHandler.createContextMenu(cable),
                 hub => HubHandler.createContextMenu(hub)) }

import ipsim.swing.Swing._

import UserMessages.message
import ipsim.network.Implicits._
import java.awt.Graphics2D
import ipsim.awt.{TextMetrics, HorizontalAlignment, VerticalAlignment}

object CardHandler {
 def createContextMenu(frame: JFrame, network: Network, view: NetworkView, card: Card) = {
  implicit val boilerplate = network
  val menu=new JPopupMenu
  menu add item("Install/Uninstall Device Drivers", 'I', { if (card.withDrivers!=null) card.uninstallDeviceDrivers else card.installDeviceDrivers(network)
                                                           view.repaint() } )
  menu add item("Disconnect cable from card", 'C', card cable network fold (
   message("There is no cable to disconnect"),
   cable => cable.positions = 0 to cable.positions.size map (index => Left(Positions.position(cable, index))) toList))
  menu add item("Delete", 'D', card.cardDrivers fold (message("You must remove the drivers first"),
                                                      cardDrivers => { network.log += "Deleted "+card.toString+'.'
                                                                       network delete card
                                                                       view.repaint() } ))
  menu }

 val icon = new ImageIcon(CardHandler.getClass getResource "/images/card.png")

 def render(card: Card, graphics: Graphics2D)(implicit network: Network, view: NetworkView) = {
  val position = card.positions(0).toPoint
  val cardImage = icon.getImage
  val imageWidth = cardImage.getWidth(null) / 2
  val imageHeight = cardImage.getHeight(null) / 2
  graphics drawImage (cardImage, position.x.toInt - imageWidth, position.y.toInt - imageHeight, view)
  val mousePos = view.mouseTracker.position map (_ / network.zoomLevel)
  val mouseIsNear = mousePos fold (false, pos => (pos.x - position.x).abs < 40 && (pos.y - position.y).abs < 40)
  if (card.positions(0).isLeft && !card.cardDrivers.isDefined && mouseIsNear)
   TextMetrics drawString (graphics, "Card "+card.cardDrivers.get.ethNumber, Point(position.x, position.y.toInt + imageHeight / 2 + 5),
                           HorizontalAlignment.centre, VerticalAlignment.top, true) }

 def componentMoved(card: Card, pointIndex: Int)(implicit network: Network) = {
  (for { component2 <- Global.network.depthFirstIterable
         if component2.asComputer.isDefined
         a <- 0 to component2.positions.size
         if ObjectRenderer.isNear(card, pointIndex, component2, a) } yield (a, component2)).headOption foreach
   (parentAndIndex => card.positions = card.positions.set(parentAndIndex._1, Right(parentAndIndex._2))) } }

import java.awt.BasicStroke
object ComputerHandler {
 val icon = new ImageIcon(ComputerHandler.getClass getResource "/images/computer.png")
 val ispIcon = new ImageIcon(ComputerHandler.getClass getResource "/images/isp.png")

 def createContextMenu(view: NetworkView, context: NetworkContext, computer: Computer)(implicit frame: JFrame, network: Network) = {
  val menu = new JPopupMenu
  menu add item("Ping...", 'P', new PingDialog(computer).wrapped setVisible true)
  menu add item("Traceroute...", 'T', TracerouteDialog.newTracerouteDialog(computer) setVisible true)
  if (!network.cardDrivers(computer).isEmpty) menu.addSeparator
  menu }
 def render(computer: Computer, graphics: Graphics2D)(implicit network: Network, view: NetworkView) = {
  val position = computer.positions(0).toPoint
  val basicStroke = new BasicStroke(8)
  graphics setStroke new BasicStroke(8)
  for (card <- computer.children(network)) { val position2 = card.positions(0).toPoint
                                    graphics drawLine (position.x.toInt, position.y.toInt, position2.x.toInt, position2.y.toInt) }
  val computerImage = computer.isISP ? ispIcon.getImage ! icon.getImage
  val imageWidth = computerImage.getWidth(null) / 2
  val imageHeight = computerImage.getHeight(null) / 2
  graphics drawImage (computerImage, position.x.toInt - imageWidth, position.y.toInt - imageHeight, view) } }
                                                           

import ipsim.swing.Dialogs.createDialogWithEscapeKeyToClose
import anylayout.extras.SizeCalculatorUtility
import ipsim.swing.Swing._
import ipsim.swing.Implicits._
import java.io.PrintWriter

import ipsim.network.connectivity.ping.{Pinger, PingResults}
case class PingDialog(computer: Computer)(implicit frame: JFrame, network: Network) {
 val wrapped = createDialogWithEscapeKeyToClose("Ping")
 val constraints = PercentConstraintsUtility newInstance wrapped.getContentPane
 wrapped setSize (600, 400)
 wrapped centreOnParent frame
 AnyLayout useAnyLayout (wrapped.getContentPane, 0.5F, 0.5F, SizeCalculatorUtility absoluteSize (600, 400),
                         ConstraintUtility typicalDefaultConstraint Runnables.throwRuntimeException)
 constraints add (new JLabel("IP Address"), 5, 5, 25, 10, true, true)
 val ipAddressTextField = IPAddressTextField.newInstance
 ipAddressTextField setIPAddress IPAddress.zero
 constraints add (ipAddressTextField.component, 5, 15, 25, 10, false, false)

 val pingButton = new JButton("Ping")
 pingButton setMnemonic 'P'
 constraints add (pingButton, 35, 15, 25, 10, false, false)
 val textArea = new JTextArea(10, 10)
 textArea setEditable false
 pingButton listener { val ipAddress = ipAddressTextField.ipAddress
                       val documentWriter = DocumentWriter documentWriter textArea.getDocument
                       val printWriter = new PrintWriter(documentWriter)
                       val pingResults = Pinger ping (computer, ipAddress, Global.defaultTimeToLive)
                       network.log += pinged(computer, ipAddress, pingResults)
                       try { printWriter println pingResults.addString(new StringBuilder, "\n").toString } finally { printWriter.close } }
 constraints add (new JScrollPane(textArea), 10, 30, 80, 60, true, true)
 val closeButton = Buttons.closeButton("Close", wrapped)
 closeButton setMnemonic 'C'
 constraints add (closeButton, 75, 15, 25, 10, false, false)

 def pinged(source: Computer, dest: IPAddress, pingResults: Iterable[PingResults]) = {
  var string = source.toString
  string = Character.toUpperCase(string charAt 0) + string substring 1
  val tempDescription = new StringBuilder(string)
  tempDescription append " pinged " append dest append ", "
  if (pingResults.size == 1) {
   val results = pingResults(0)
   tempDescription append (if (results == null) "" else "with a result of \""+results+"\".") } else
    tempDescription append pingResults.size append " results returned"
  tempDescription toString } }
 
import javax.swing.WindowConstants
import anylayout.extras.SizeCalculatorUtility
import anylayout.extras.ConstraintUtility.typicalDefaultConstraint
import ipsim.lang.Runnables.throwRuntimeException
import ipsim.swing.Buttons.closeButton
import ipsim.swing.Implicits._
import ipsim.network.connectivity.traceroute.Traceroute
import ipsim.io.PimpIO._
import java.io.PrintWriter

object TracerouteDialog { def newTracerouteDialog(computer: Computer)(implicit network: Network, frame: JFrame) = {
 val dialog = createDialogWithEscapeKeyToClose("Traceroute")
 dialog setDefaultCloseOperation WindowConstants.DISPOSE_ON_CLOSE
 dialog setLocation (200, 100)
 dialog setSize (400, 400)
 val pane = dialog.getContentPane
 val constraints = PercentConstraintsUtility newInstance pane
 AnyLayout useAnyLayout(pane, 0.5F, 0.5F, SizeCalculatorUtility absoluteSize (200, 100), typicalDefaultConstraint(throwRuntimeException))
 constraints add (new JLabel("IP Address"), 10, 5, 25, 5, false, false)
 val ipAddressTextField = IPAddressTextField.newInstance
 constraints add (ipAddressTextField.component, 30, 5, 25, 5, false, false)
 val button = new JButton("Traceroute")
 constraints add (button, 60, 5, 30, 5, false, false)
 constraints add (new JLabel("Output:"), 5, 15, 30, 5, false, false)
 val outputArea = new JTextArea(5, 5)
 val outputPanel = new JPanel(new BorderLayout)
 outputPanel add outputArea
 constraints add (new JScrollPane(outputPanel), 10, 25, 80, 65, true, true)
 val close = closeButton("Close", dialog)
 constraints add (close, 70, 90, 20, 10, false, false)
 button listener (() => {
  outputArea setText ""
  if (ipAddressTextField.component.getText.length == 0) message("Cannot traceroute without an IP address")
  else { val address = IPAddress valueOf ipAddressTextField.component.getText
         address fold (message("Malformed IP Address"), address => {
          val results = Traceroute trace (computer, address, 30)
          using(DocumentWriter.documentWriter(outputArea.getDocument))( dw => using(new PrintWriter(dw))(_ println results.toString))
          network.log += "Tracerouted from " + computer.toString + " to " + address + ", " + results.size + " results received." } ) } } )
 dialog } }

import javax.swing.JTextField
import ipsim.swing.ValidatingDocumentListener
import java.awt.Color
object IPAddressTextField { def newInstance = {
 val textField = new JTextField(15)
 val validator = new IPAddressValidator(IPAddress.zero)
 val listener = new ValidatingDocumentListener(textField, textField.getBackground, Color.pink, validator)

 textField.getDocument addDocumentListener listener
 new IPAddressTextField(validator) } }

import ipsim.swing.DocumentValidator
import javax.swing.text.Document
case class IPAddressValidator(var address: IPAddress) extends DocumentValidator {
 def isValid(document: Document) = { val string = document getText (0, document getLength)
                                     val tmp = IPAddress.valueOf(string)
                                      tmp.foreach(x => address = tmp.get)
                                     !tmp.isEmpty } }
                                                                                  
case class IPAddressTextField(validator: IPAddressValidator) {
 val component = new JTextField
 def ipAddress = validator.address
 def setIPAddress(address: IPAddress) = { validator.address = address
                                          component.setText(if (address.isZero) "" else address.toString) } }

import java.io.Writer
object DocumentWriter { def documentWriter(document: Document) =
 new Writer { override def write(cbuf: Array[Char], off: Int, len: Int) = document insertString (document.getLength, new String(cbuf, off, len), null)
              override def flush = ()
              override def close = () } }

import ipsim.gui.UserMessages.confirm
import javax.swing.JList
import java.util.Vector
import java.awt.Graphics2D
import ipsim.network.{CableType, straightThrough, crossover}
object EthernetCableHandler {
 val icon = new ImageIcon(EthernetCableHandler.getClass getResource "/images/cable.png")
 def render(cable: Cable, graphics2d: Graphics2D)(implicit network: Network) = {
  val initialStroke = graphics2d.getStroke
  val initialColor = graphics2d.getColor
  graphics2d setColor Color.gray.brighter

  import java.awt.BasicStroke
  graphics2d setStroke new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
  val position1 = Positions.position(cable, 0)
  val position2 = Positions.position(cable, 1)
  graphics2d drawLine (position1.x.toInt, position1.y.toInt, position2.x.toInt, position2.y.toInt)
  graphics2d setStroke initialStroke
  graphics2d setColor initialColor }

 def componentMoved(cable: Cable, points: Array[Int])(implicit context: NetworkContext, network: Network) =
  for { pointIndex <- points
        next <- context.visibleComponentsVar
        if next.asCard.isDefined || next.asHub.isDefined
        if ObjectRenderer isNear (cable, pointIndex, next, 0) } cable.positions = cable.positions.set(pointIndex, Right(next))

 def createContextMenu(cable: Cable)(implicit frame: JFrame, network: Network, context: NetworkContext) = {
  val menu = new JPopupMenu
  menu add item("Delete", 'D', if (confirm("Really delete this Ethernet cable?")) {
   network.log += ("Deleted " + cable + '.')
   context.visibleComponentsVar = context.visibleComponentsVar filter (_ != cable)
   network.modified = true } )
  menu add item("Test Cable", 'T', { val result = cable.cableType
                                     network.log += ("Tested a cable, result: " + result)
                                     message(result.toString) } )
  menu add item("Change Cable Type", 'C', { val list = new JList
                                            list setListData new Vector[CableType] { add(straightThrough)
                                                                                     add(crossover) }
                                            if (cable.cableType == broken) list.clearSelection else list.setSelectedValue(cable.cableType, true)
                                            
                                            val cableType = list.getSelectedValue.asInstanceOf[CableType]
                                            if (confirm("CableType") && cableType != null) cable.cableType = cableType } )
  menu } }

import javax.swing.JRadioButtonMenuItem
import ipsim.network.Implicits._
object HubHandler { val icon = new ImageIcon(HubHandler.getClass getResource "/images/hub.png")
                    def render(hub: Hub, graphics: Graphics2D)(implicit network: Network, view: NetworkView) = if (!hub.positions.isEmpty) {
                     val position = hub.positions(0).toPoint
                     val hubImage = icon.getImage
                     val imageWidth = hubImage getWidth null
                     val imageHeight = hubImage getHeight null
                     graphics drawImage (hubImage, position.x.toInt - imageWidth / 2, position.y.toInt - imageHeight / 2, view)
                     val originalColor = graphics.getColor
                     graphics setColor Color.green.brighter
                     if (hub power) graphics fillOval (position.x.toInt - imageWidth / 3 - 4, position.y.toInt - imageHeight / 3 + 4, 8, 8)
                     graphics setColor originalColor }
                    def createContextMenu(hub: Hub)(implicit frame: JFrame, network: Network, view: NetworkView) = {
                     val menu = new JPopupMenu
                     menu add item("Delete", 'D', if (confirm("Really delete this hub?")) { network.log += ("Deleted "+hub+'.')
                                                                                            network delete hub
                                                                                            network.modified = true
                                                                                            view.repaint() } )
                     val powerItem = new JRadioButtonMenuItem("Toggle Power")
                     powerItem setMnemonic 'T'
                     powerItem setSelected hub.power
                     powerItem listener { network.modified = true
                                          network.log = network.log + ((if (powerItem isSelected) "En" else "Dis") + "abled power on "+hub+'.')
                                          view.repaint() }
                     menu add powerItem
                     menu } }

import java.awt.RenderingHints
object ObjectRenderer {
 def render(component: PacketSource, graphics: Graphics2D)(implicit network: Network, view: NetworkView) = {
  val map = new java.util.HashMap[RenderingHints.Key, Object]
  map put (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
  graphics setRenderingHints map
  RenderComponent renderComponent (component, graphics) }
 def isNear(component1: PacketSource, index1: Int, component2: PacketSource, index2: Int)(implicit network: Network) =
  (Positions.position(component1, index1) - Positions.position(component2, index2)).abs < 45
 def centre(component: PacketSource)(implicit network: Network) = component.positions.foldLeft(Point(0, 0))(_ + _.toPoint) / component.positions.size }

object RenderComponent {
 def renderComponent(component: PacketSource, graphics: Graphics2D)(implicit network: Network, view: NetworkView) =
  component.fold(card => CardHandler.render(card, graphics),
                 computer => ComputerHandler render (computer, graphics),
                 cable => EthernetCableHandler render (cable, graphics),
                 hub => HubHandler render (hub, graphics)) }

import javax.swing.event.MouseInputListener
object NetworkViewMouseListener { def createNetworkViewMouseListener = new MouseInputListener {
 case class PointRecordDead(obj: PacketSource, index: Int)
 var startDrag: (PacketSource, Int) = null
 var changed = Point(0, 0)
 def mouseClicked(event: MouseEvent) = Global.networkView.mouseTracker mouseEvent zoomMouseEvent(event)
 def popupTriggered(event: MouseEvent) = if (event.isPopupTrigger) {
  val component = NetworkView.pointAt(event getX, event getY)(Global.network, Global.networkContext)
  if (component != null)
   InvokeContextMenu.invokeContextMenu(CreateContextMenu.createContextMenu(component)(Global.frame, Global.network, Global.networkContext,
                                                                                      Global.networkView),component)(
                                                                                       Global.network, Global.networkView) }
 def zoomMouseEvent(event: MouseEvent) = new MouseEvent(event.getComponent, event.getID, event.getWhen, event.getModifiers,
                                                        (event.getX / Global.network.zoomLevel).toInt, (event.getY / Global.network.zoomLevel).toInt,
                                                        event.getClickCount, event.isPopupTrigger, event.getButton)
 def mouseDragged(_originalEvent: MouseEvent) = {
  changed = Point(0, 0)
  Global.networkView.mouseTracker mouseEvent _originalEvent
  val originalEvent = zoomMouseEvent(_originalEvent)
  if (startDrag == null && originalEvent.getButton == MouseEvent.BUTTON1)
   startDrag = NetworkView.topLevelPointAt(originalEvent.getX, originalEvent.getY)(Global.network, Global.networkContext).getOrElse(null)
  if (startDrag != null) { startDrag._1.positions = startDrag._1.positions.set(startDrag._2, Left(Point(originalEvent.getX, originalEvent.getY)))
                           val visibleRect = Global.networkView.getVisibleRect
                           if (changed != Point(0, 0)) { Positions.translateAllWhenNecessary(visibleRect)(Global.network)
                                                         Global.networkView scrollRectToVisible visibleRect }
                           jiggleLayout
                           Global.networkView.invalidate
                           Global.networkView.validate
                           Global.networkView.repaint() } }
 def mouseEntered(event: MouseEvent) = Global.networkView.mouseTracker mouseEvent event
 def mouseExited(event: MouseEvent) = mouseEntered(event)
 def mouseMoved(event: MouseEvent) = { mouseEntered(event)
                                       Global.networkView.repaint() }
 def mousePressed(originalEvent: MouseEvent) = { val event = zoomMouseEvent(originalEvent)
                                                 Global.networkView.mouseTracker mouseEvent originalEvent
                                                 val point = NetworkView.topLevelPointAt(event.getX, event.getY)(Global.network, Global networkContext)
                                                 if (event isPopupTrigger) popupTriggered(event) else if (event.getButton == MouseEvent.BUTTON1)
                                                  startDrag = point.getOrElse(null)
                                                 Global.networkView.requestFocus }
 def mouseReleased(originalEvent: MouseEvent) = {
  val event = zoomMouseEvent(originalEvent)
  if (event isPopupTrigger) popupTriggered(event) else {
   val mouseDragOccured = Global.networkView.mouseTracker.lastMousePressedEvent fold (false, e => event.getX != e.getX || event.getY != e.getY)
   Global.networkView.mouseTracker mouseEvent event
   if (startDrag != null && mouseDragOccured) ComponentMoved.componentMoved(startDrag._1, Array(startDrag._2))(Global.network, Global.networkContext)
   jiggleLayout
   startDrag = null } }
 
 def jiggleLayout = { Global.networkView.setPreferredSize(NetworkView.preferredSize(Global.network))
                      val scrollPane = Global.networkView.getParent.getParent
                      scrollPane.invalidate
                      scrollPane.validate
                      Global.networkView.repaint() } } }

object InvokeContextMenu { def invokeContextMenu(menu: JPopupMenu, component: PacketSource)(implicit network: Network, view: NetworkView) = {
 val centre = ObjectRenderer.centre(component) * network.zoomLevel
 menu show (view, centre.x.toInt, centre.y.toInt) } }

object ComponentMoved { def componentMoved(component: PacketSource, points: Array[Int])(implicit network: Network, context: NetworkContext) = component fold (
 card => { CardHandler componentMoved (card, points(0))
           card.positions(0).right map (parent => network.log += ("Connected "+card+" to "+parent+'.')) },
 computer => (),
 cable => EthernetCableHandler componentMoved (cable, points),
 hub => () ) }

import anylayout.AnyLayout.useAnyLayout
import javax.swing.SwingConstants

object ProblemDialog { def createProblemDialog(implicit frame: JFrame, context: NetworkContext) = {
 val dialog = createDialogWithEscapeKeyToClose("Edit Problem")
 dialog setSize (400, 220)
 dialog centreOnParent frame
 val constraints = PercentConstraintsUtility newInstance dialog.getContentPane
 useAnyLayout(dialog getContentPane, 0.5F, 0.5F, constraints getSizeCalculator, typicalDefaultConstraint(throwRuntimeException))
 constraints add (new JLabel("Network Number"), 10, 10, 35, 10, false, false)
 val ipAddressTextField = IPAddressTextField.newInstance
 val problem = context.problem
 val networkNumber = problem.fold(IPAddress.zero, _.netBlock networkNumber)
 val subnetMask = problem.fold(NetMask.zero, _.netBlock.netMask)
 val numberOfSubnets = problem.fold(ProblemConstants.MIN_SUBNETS, _.numberOfSubnets)
 ipAddressTextField setIPAddress networkNumber
 constraints add (ipAddressTextField.component, 50, 10, 30, 10, false, false)
 constraints add (new JLabel("Network Mask", SwingConstants RIGHT), 10, 30, 35, 10, false, false)
 val subnetMaskTextField = new SubnetMaskTextField
 subnetMaskTextField setNetMask subnetMask
 constraints add (subnetMaskTextField, 50, 30, 35, 10, false, false)
 constraints add (new JLabel("Number of Subnets"), 10, 50, 35, 10, false, false)
 val numberOfSubnetsTextField = new JTextField(2)
 numberOfSubnetsTextField setText String.valueOf(numberOfSubnets)
 constraints add (numberOfSubnetsTextField, 50, 50, 30, 15, false, false)
 constraints add (new JButton("OK") listener {
  val subnets = Integer parseInt (numberOfSubnetsTextField getText)
  val problem = subnetMaskTextField.netMask flatMap (netMask => Some(Problem(NetBlock(ipAddressTextField ipAddress, netMask), subnets)))
  context.problem = problem
  dialog setVisible false
  dialog dispose }, 15, 85, 20, 15, false, false)
 dialog } }

case class SubnetMaskTextField extends JTextField {
 private val validator = IPAddressValidator(IPAddress zero)
 getDocument addDocumentListener new ValidatingDocumentListener(this, getBackground, Color pink, validator)
 def netMask = NetMask.netMask(validator.address rawValue)
 def setNetMask(mask: NetMask) = { validator.address = IPAddress(mask rawValue)
                                   setText(if (mask isZero) "" else mask toString) }
 override def getPreferredSize = new Dimension(TextMetrics.size(getFont, "999.999.999.999/99").x toInt, super.getPreferredSize.getHeight toInt)
 def isValidText = validator isValid getDocument }

import ipsim.network.route.Route
object AddDefaultRouteDialogUtility { def newInstance(computer: Computer)(implicit frame: JFrame, network: Network) = {
 val dialog = createDialogWithEscapeKeyToClose("Add a Default Route")
 dialog setSize (400, 200)
 dialog centreOnParent frame
 val pane = dialog.getContentPane
 val constraints = PercentConstraintsUtility newInstance pane
 useAnyLayout(pane, 0.5F, 0.5F, constraints getSizeCalculator, ConstraintUtility typicalDefaultConstraint Runnables.throwRuntimeException)
 constraints add (new JLabel("IP Address"), 10, 10, 25, 15, false, false)
 val ipAddressTextField = IPAddressTextField.newInstance
 val okButton = new JButton("OK")
 constraints add (okButton, 10, 70, 15, 15, false, false)
 okButton listener { val zero = NetBlock.zero
                     val ipAddress = ipAddressTextField.ipAddress
                     if (computer isLocallyReachable ipAddress) { val route = Route(zero, ipAddress)
                                                                  computer.routingTable add (Some(computer), route)
                                                                  network.log += ("Added a default route to "+computer+" of "+ipAddress+'.')
                                                                  network.modified = true
                                                                  dialog setVisible false
                                                                  dialog dispose } else { error("Gateway unreachable")
                                                                                          dialog requestFocus } }
 val cancelButton = closeButton("Cancel", dialog)
 constraints add (cancelButton, 70, 70, 25, 15, false, false)
 dialog } }

import javax.swing.JDialog
import ipsim.swing.Implicits._
object RoutingTableEntryEditDialog {
 def createRoutingTableEntryEditDialog(computer: Computer, entry: RouteInfo, realRoute: Option[Route], parent: Option[RoutingTableDialog])(
  implicit network: Network, frame: JFrame) = { val dialog = createDialogWithEscapeKeyToClose("Edit Route") size (400, 200)
                                                dialog setTitle "Edit Route"
                                                createRouteEditor(dialog, computer, entry, realRoute, parent) }

 def createRouteEditor(dialog: JDialog, computer: Computer, entry: RouteInfo, realRoute: Option[Route], parent: Option[RoutingTableDialog])(
  implicit network: Network) = {
  val constraints = PercentConstraintsUtility newInstance dialog.getContentPane
  useAnyLayout(dialog getContentPane, 0.5F, 0.5F, SizeCalculatorUtility absoluteSize (400, 200), typicalDefaultConstraint(Runnables.throwRuntimeException))
  constraints add (new JLabel("Destination Network"), 5, 5, 45, 10, false, false)
  val networkNumberTextField = IPAddressTextField.newInstance
  networkNumberTextField setIPAddress entry.destination.networkNumber
  constraints add (networkNumberTextField.component, 50, 5, 45, 10, false, false)
  constraints add (new JLabel("Destination Subnet Mask"), 5, 20, 45, 10, false, false)

  val subnetMaskTextField = new SubnetMaskTextField
  subnetMaskTextField setNetMask entry.destination.netMask
  constraints add (subnetMaskTextField, 50, 20, 30, 10, false, false)
  constraints add (new JLabel("Gateway"), 5, 40, 45, 10, false, false)
  val ipAddressTextField = IPAddressTextField.newInstance
  ipAddressTextField setIPAddress entry.gateway
  constraints add (ipAddressTextField.component, 50, 40, 30, 10, false, false)

  val okButton = new JButton("OK")
  val entry1 = RouteInfo(entry.destination, entry.gateway)

  okButton listener {
   
   val networkNumber = networkNumberTextField.ipAddress
   subnetMaskTextField.netMask foreach (netMask => {
    val newEntry = RouteInfo(NetBlock(networkNumber, netMask), ipAddressTextField.ipAddress)
    val realEntry = Route(newEntry.destination, newEntry.gateway)
    realRoute.fold( {
     computer.routingTable add (Some(computer), realEntry)
     network.log = network.log + ("Added an explicit route to " + computer + " to get to the " + entry1.destination.asCustomString + "network, via the "
                                  + entry1.gateway + " gateway.") },
                    route => { val previous = RouteInfo(route.netBlock, route.gateway)
                               computer.routingTable replace (route, Route(newEntry destination, newEntry gateway))
                               network.log += ("Changed a route (" + previous + " to " + newEntry + " on " + computer) } )
   
    parent.foreach(_.populateElements)
    dialog setVisible false
   dialog dispose } ) }
  constraints add (okButton, 20, 80, 20, 15, false, false)
  constraints add (closeButton("Cancel", dialog), 60, 80, 20, 15, false, false)
  dialog } }

case class RouteInfo(destination: NetBlock, gateway: IPAddress) { override def toString = 
 "Destination: "+(if (destination.networkNumber.isZero || destination.netMask.isZero) "default" else destination.asCustomString) + " Gateway: " + 
 (if (gateway.isZero) "default" else gateway.toString) }

trait RoutingTableDialog { def populateElements: Unit
                           def getDialog: JDialog
                           def editButtonClicked: Unit
                           def deleteButtonClicked: Unit }

object RoutingTableDialog {
 def createRoutingTableDialog(computer: Computer)(implicit network: Network, frame: JFrame) = {
  val dialog = createDialogWithEscapeKeyToClose("Routing Table")
  val entries = new JList
  val editButton = new JButton("Edit...")
  val routingTableDialog = new RoutingTableDialog {
   val thiss=this
   var list: List[Either[String, Route]] = Nil
   def populateElements = { import computer.routingTable
                            import routingTable.{routes => allRoutes}
                            val cards: List[CardDrivers] = computer.sortedCards
                            list = ((for { card <- cards
                                          if card.ipAddress.isZero
                                          destination = IPAddress(card.netMask & card.ipAddress)
                                          netMask = card.netMask
                                          buffer = "Destination: " + destination + " netmask " + netMask + " Gateway: *" } yield Left(buffer)) ++
                                    (allRoutes map (Right(_)))).reverse
                           
                            entries setListData list.map(_ fold(x => x, _ toString)).toArray[Object]
                            dialog.invalidate
                            dialog.validate
                            dialog.repaint() }
   def editButtonClicked =
    if (entries.getSelectedIndex == -1) noEntrySelected else list(entries getSelectedIndex).right.toOption foreach (entry => 
     RoutingTableEntryEditDialog.createRoutingTableEntryEditDialog(computer, RouteInfo(entry netBlock, entry gateway), Some(entry),
                                                                   Some(thiss)) setVisible true)
   def deleteButtonClicked = {
    if (entries.getSelectedIndex == -1) noEntrySelected else
     list(entries getSelectedIndex).right.toOption foreach (route => {
      val previous = route.asCustomString
      computer.routingTable remove route
      network.log += ("deleted a route (" + previous + ") from " + computer) } )
    populateElements }

   def noEntrySelected = error("Select an item before clicking on Edit or Delete")
   def getDialog = dialog }
  dialog setSize (600, 400)
  routingTableDialog.populateElements
  dialog centreOnParent frame
  val constraints = PercentConstraintsUtility newInstance dialog.getContentPane
  AnyLayout useAnyLayout (dialog getContentPane, 0.5F, 0.5F, constraints getSizeCalculator,
                          ConstraintUtility typicalDefaultConstraint Runnables.throwRuntimeException)
  constraints add (new JLabel("Routing Table"), 5, 5, 30, 5, false, false)
  constraints add (entries, 5, 15, 90, 60, true, true)
  constraints add (editButton, 10, 85, 20, 10, false, false)
  editButton listener routingTableDialog.editButtonClicked
  val deleteButton = new JButton("Delete")
  constraints add (deleteButton, 40, 85, 20, 10, false, false)
 deleteButton listener routingTableDialog.deleteButtonClicked
 val closeButton = new JButton("Close")
 constraints add (closeButton, 70, 85, 20, 10, false, false)
 closeButton listener { dialog setVisible false
                        dialog dispose }
 routingTableDialog } }
                           
object EditIPAddressDialogFactory { def newInstance(computer: Computer, ethNo: Int)(implicit frame: JFrame, network: Network) = {
 val dialog = createDialogWithEscapeKeyToClose("Edit IP Address") size (400, 220)
 dialog centreOnParent frame
 val constraints = PercentConstraintsUtility newInstance dialog.getContentPane
 useAnyLayout(dialog getContentPane, 0.5F, 0.5F, constraints getSizeCalculator, typicalDefaultConstraint(throwRuntimeException))
 constraints add (new JLabel("IP Address"), 10, 5, 25, 15, false, false)
 val ipAddressTextField = IPAddressTextField.newInstance
 val card = computer.cardsWithDrivers.find(_.ethNumber == ethNo).get
 ipAddressTextField setIPAddress card.ipAddress
 constraints add (ipAddressTextField.component, 40, 5, 25, 15, false, true)
 constraints add (new JLabel("Subnet Mask"), 10, 45, 25, 15, false, false)
 val subnetMaskTextField = new SubnetMaskTextField
 subnetMaskTextField setNetMask card.netMask
 constraints add (subnetMaskTextField, 40, 45, 25, 15, false, false)
 val okButton = new JButton("Ok") listener { val before = card.ipAddress
                                             val beforeNetMask = card.netMask
                                             val cardBefore = card.toString
                                             card.ipAddress = ipAddressTextField.ipAddress
                                             card.netMask = (NetMask valueOf subnetMaskTextField.getText).get
                                             val after = card.ipAddress.toString
                                             val afterNetmask = card.netMask.toString
                                             network.log += (if (before.isZero)
                                              "Assigned IP address "+ after + " and subnet mask " + afterNetmask + " to " + cardBefore + '.' else
                                               "Changed the IP address of " + card + " from " + before + " to " + after + " and the netmask from " +
                                                             beforeNetMask + " to " + afterNetmask + '.')
                                            computer.routingTable.routes filter (route => !computer.isLocallyReachable(route.gateway)) foreach (route =>
                                             computer.routingTable remove route)
                                            network.modified = true
                                            dialog setVisible false
                                            dialog.dispose }
 constraints add (okButton, 10, 80, 25, 15, false, false)
 constraints add (closeButton("Cancel", dialog), 60, 80, 25, 15, false, false)
 dialog } }

object NetworkComponentUtility {
 def create(component: PacketSource)(implicit context: NetworkContext, network: Network) = {
  network.modified = true
  context.visibleComponentsVar = component :: context.visibleComponentsVar
  network.log += ("Created "+component+".")
  component }
 def pointsToStringWithoutDelimiters(component: PacketSource) = {
  val length = component.positions.size
  if (length == 1) component.positions(0).fold("at " + _, "connected to " + _) else
   if (length == 2) "from " + component.positions(0).fold(_.toString, _.toString) + " to " + component.positions(1).fold(_.toString, _.toString) } }
import javax.swing.UIManager
object NetBlockTextField {
 def createNetBlockTextField = {
  val textField = new JTextField {
   override def getPreferredSize = new Dimension(TextMetrics.size(getFont, "999.999.999.999/99").x.toInt + 13, super.getPreferredSize.getHeight.toInt) }
  val validator = new DocumentValidator { var block: NetBlock = null
                                          def isValid(document: Document) = {
                                           val netBlock = NetBlock.valueOf(document getText (0, document getLength))
                                           netBlock foreach (e => block = e)
                                           netBlock.isDefined } }                                                                             
  val listener = new ValidatingDocumentListener(textField, UIManager getColor "TextField.background", Color pink, validator)
  textField.getDocument addDocumentListener listener
  new NetBlockTextField { def netBlock = validator.block
                          def isValid = validator isValid textField.getDocument
                          val component = textField } } }

abstract class NetBlockTextField { def netBlock: NetBlock
                                   def isValid: Boolean
                                   def component: JTextField }

import java.awt.{FocusTraversalPolicy, Component}
import javax.swing.BorderFactory
import javax.swing.border.BevelBorder

object ScrapbookDialogUtility {
 def createScrapbook(frame: JFrame)(implicit network: Network) = {
  val panel = new JPanel
  val constraints = PercentConstraintsUtility newInstance panel
  AnyLayout useAnyLayout (panel, 0.5f, 0.5f, constraints getSizeCalculator, typicalDefaultConstraint(Runnables throwRuntimeException))
  constraints add (new JLabel("Network Number"), 2, 2, 28, 5, false, false)
  val networkNumberField = NetBlockTextField.createNetBlockTextField useAndReturn (x => constraints.add(x.component, 2, 8, 28, 10, false, false))
  constraints add (new JLabel("Netmask"), 2, 15, 28, 5, false, false)
  val netMaskField = new SubnetMaskTextField useAndReturn (x => constraints add (x, 2, 20, 28, 20, false, false))
  val elements: List[ScrapbookElement] = { val es: List[ScrapbookElement] = (0 to 5 map (x => ScrapbookElement.createElement)).toList
                                           for ((e, a) <- es zipWithIndex) { e.panel setBorder BorderFactory.createBevelBorder(BevelBorder LOWERED)
                                                                             e.panel setOpaque false
                                                                             constraints add (e panel, 30, 20 * a, 70, 20, true, true) }
                                           es }
  val clearNumbers = new JButton("Clear Numbers") listener {
   if (confirm("Do you really want to clear all the numbers?")) { List(networkNumberField.component, netMaskField) foreach (_ setText "")
                                                                  elements flatMap (_.textFields) foreach (_ setText "") } }
  constraints add (clearNumbers, 2, 80, 25, 10, false, false)

  val checkNumbersButton = new JButton("Check Numbers") listener { val results = checkNumbers(networkNumberField, netMaskField, elements)
                                                                   network.log += ("Scrapbook - " + results._1)
                                                                   message(results._2) }
  constraints add (checkNumbersButton, 2, 70, 25, 10, false, false)

  val list: List[Component] = List(networkNumberField.component, netMaskField, checkNumbersButton, clearNumbers) ++
   (elements flatMap ((element: ScrapbookElement) => List[Component](element.subnetTextField.component, element.netMaskTextField) ++ (element.ipAddressTextFields map (_.component))))
  val policy = new FocusTraversalPolicy {
   override def getComponentAfter(aContainer: Container, aComponent: Component) = list((list.indexOf(aComponent) + 1) % list.size)
   override def getComponentBefore(aContainer: Container, aComponent: Component) = { val index = list indexOf aComponent
                                                                                     list(if (index == 0) list.size - 1 else index) }
   override def getFirstComponent(aContainer: Container) = list.first
   override def getLastComponent(aContainer: Container) = list.last
   override def getDefaultComponent(aContainer: Container) = list.first }
  List(panel) ++ elements.map(_ panel) foreach (c => { c setFocusTraversalPolicy policy
                                                       c setFocusTraversalPolicyProvider true })
  panel }

 def checkNumbers(networkNumberField: NetBlockTextField, netMaskField: SubnetMaskTextField, elements: List[ScrapbookElement]): (String, String) = {
  var checked = 0
  var errors = 0
  val errorMessage = ("one or more fields have invalid data.", "One or more fields have invalid data.")
  val description = new StringBuilder
  if (!networkNumberField.component.getText.isEmpty) {
   if (!networkNumberField.isValid) errorMessage else {
    val block = networkNumberField.netBlock
    if (!netMaskField.getText.isEmpty) {
     if (netMaskField.isValidText && block.netMask != netMaskField.netMask)
      description.append("The netmask " + netMaskField.netMask + " does not correspond with the network " + block.asStringContainingSlash) }
    else return errorMessage
    checked += 1
    for { element <- elements
          if !element.subnetTextField.component.getText.isEmpty } {
           if (!element.subnetTextField.isValid) return errorMessage
           val subnet = element.subnetTextField.netBlock
           val netNum = IPAddress(subnet.networkNumber & subnet.netMask)
           checked += 1
           val length1 = block.netMask.prefixLength
           val length2 = subnet.netMask.prefixLength
           if (length1 == -1 || length2 == -1) description append "One of the subnet masks is invalid\n" else
            if (!block.contains(netNum) || length1 >= length2) {
             errors += 1
             description append "The subnet " append element.subnetTextField.component.getText
             description append " is not a subnet of the network " append networkNumberField.component.getText append '\n' }
           description append checkScrapbookIPs(block, element.ipAddressTextFields) } }
    for { element <- elements
          if !element.subnetTextField.component.getText.isEmpty } if (!element.subnetTextField.isValid) return errorMessage else {
           val subnet = element.subnetTextField.netBlock
           description append checkScrapbookIPs(subnet, element.ipAddressTextFields)
           checked += 1
           if (!element.netMaskTextField.getText.isEmpty) { if (!element.netMaskTextField.isValidText) return errorMessage
                                                            if (element.netMaskTextField.netMask == subnet.netMask) {
                                                             errors += 1
                                                             description append "The netmask " append element.netMaskTextField.netMask
                                                             description append " does not correspond with the subnet "
                                                             description append subnet.asStringContainingSlash } } }
    for { element1 <- elements
          if !element1.subnetTextField.component.getText.isEmpty
          netBlock1 = element1.subnetTextField.netBlock
          element2 <- elements
          netBlock2 = element2.subnetTextField.netBlock
          if !element2.subnetTextField.component.getText.isEmpty
          if element1 != element2 } { checked += 1
                                      if (element1.subnetTextField.netBlock contains element2.subnetTextField.netBlock.networkNumber) {
                                       errors += 1
                                       description append "The network " append netBlock1.asStringContainingSlash append " contains the network "
                                       description append netBlock2.asStringContainingSlash
                                       description append ", therefore they are not disjoint and cannot be used as separate networks.\n" } }
    if (!netMaskField.getText.isEmpty && !netMaskField.isValidText) return errorMessage
    if (description.isEmpty) description append "All the numbers are ok"
    ("checked " + checked + " numbers, " + errors + " errors found", description.toString) } else errorMessage }
 def checkScrapbookIPs(netBlock: NetBlock, fields: Iterable[IPAddressTextField]) = {
  val answer = new StringBuilder
  for { ipAddressTextField <- fields
        if ! ipAddressTextField.component.getText.isEmpty
        ipAddress = ipAddressTextField.ipAddress
        withSlash = netBlock.asStringContainingSlash } {
         if (! netBlock.contains(ipAddress))
          answer append "The IP address " append ipAddress append " is not in the network " + withSlash + '\n'
         if (netBlock.networkNumber == ipAddress) {
          answer append "The IP address " append ipAddress append " has all 0s as the host number, and cannot be used as an IP address on the network "
          answer append withSlash append '\n' }
         val allOnes = NetMask.fromPrefixLength(32).get.rawValue
         if (ipAddress.rawValue == (ipAddress.rawValue | netBlock.netMask.rawValue ^ allOnes)) {
          answer append "The IP address " append ipAddress append " has all 1s as the host number, and is the broadcast address for the network "
          answer append withSlash append '\n' } }
  answer.toString } }

sealed trait ScrapbookElement { def subnetTextField: NetBlockTextField
                                def ipAddressTextFields: Iterable[IPAddressTextField]
                                def panel: JPanel
                                def netMaskTextField: SubnetMaskTextField
                                def textFields: Iterable[JTextField] = ipAddressTextFields.map(_.component) ++ List(subnetTextField.component) }  
import ipsim.swing.LabelledTextField
object ScrapbookElement { def createElement = {
 val panel = new JPanel
 val constraints = PercentConstraintsUtility newInstance panel
 AnyLayout useAnyLayout (panel, 0.5f, 0.5f, constraints getSizeCalculator, typicalDefaultConstraint(throwRuntimeException))
 val subnetNumberTextField = NetBlockTextField.createNetBlockTextField
 val subnetNumber = LabelledTextField.createLabelledTextField(<html>Subnet<br />Number</html>.toString, subnetNumberTextField.component)
 constraints add (subnetNumber panel, 5, 5, 45, 50, false, false)
 val netMaskTextField = new SubnetMaskTextField
 val netMask = LabelledTextField.createLabelledTextField("Netmask", netMaskTextField)
 constraints add (netMask panel, 5, 55, 70, 40, false, false)
 val ipAddressFields: Iterable[IPAddressTextField] = for (i <- 1 to 4) yield {
  val ipAddressTextField = IPAddressTextField.newInstance
  val panel = LabelledTextField.createLabelledTextField2("IP Address " + i, ipAddressTextField.component).panel
  constraints add (panel, 50, 33 * i - 33 + 3, 50, 30, false, false)
  ipAddressTextField }
 val _panel = panel
 val _netMaskTextField = netMaskTextField
 new ScrapbookElement { val ipAddressTextFields = ipAddressFields
                        val subnetTextField = subnetNumberTextField
                        val panel = _panel
                        val netMaskTextField = _netMaskTextField } } }
