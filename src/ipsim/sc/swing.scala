package ipsim.swing

import anylayout.{SizeCalculator, AnyLayout, LayoutContext}
import anylayout.extras.{ConstraintUtility, ConstraintBuilder, RelativeConstraints, LayoutContextUtility}
import ConstraintBuilder.{buildConstraint, preferredSize}
import ConstraintUtility.{topLeft, bottomLeft, bottomRight}
import LayoutContextUtility.getFarOffset
import RelativeConstraints.halfwayBetween
import Dialogs.createDialogWithEscapeKeyToClose
import java.awt.{Component, Dimension}
import java.awt.event.{ActionEvent, ActionListener, KeyEvent, KeyAdapter, InputEvent}
import javax.swing.{ JFrame,JLabel,JButton,JOptionPane,UIManager,SwingConstants,JCheckBox, WindowConstants, JDialog, KeyStroke, JComponent, JMenuBar,
                     JMenu, JMenuItem }
import UIManager.getIcon
import SwingConstants.LEFT
import scala.collection.mutable
import ipsim.lang.Implicits._
import java.awt.Window

object SwingUtilities { def invokeLater(f: => Unit)=javax.swing.SwingUtilities invokeLater new Runnable { def run=f } }

object CustomJOptionPane {
 val padding: Int = 10
 val paddingFunction = new Function[LayoutContext, Integer] { def run(context: LayoutContext)=padding }

 def showYesNoCancelDialog(message: String, title: String)(implicit frame: JFrame) = {
  val dialog=createDialogWithEscapeKeyToClose(title)
  dialog setModal true
  val messageLabel=new JLabel(message,getIcon("OptionPane.questionIcon"),LEFT)
  def buttonWithMnemonic(text: String, code: Int) = new JButton(text) match { case b => b.setMnemonic(code)
                                                                                        b }
  val yesButton=buttonWithMnemonic("Yes", KeyEvent.VK_Y)
  val noButton=buttonWithMnemonic("No", KeyEvent.VK_N)
  val cancelButton=buttonWithMnemonic("Cancel", KeyEvent.VK_C)

  val messageConstraint=topLeft(paddingFunction,paddingFunction)
  val yesConstraint=bottomLeft(paddingFunction,paddingFunction)
  val cancelConstraint=bottomRight(paddingFunction,paddingFunction)
  val noConstraint=halfwayBetween(yesButton,cancelButton)

  val sizeCalculator=new SizeCalculator {
   def getWidth = {
    val yesWidth=yesButton.getPreferredSize.width
    val noWidth=noButton.getPreferredSize.width
    val cancelPreferredSize=cancelButton.getPreferredSize
    Math.max(padding+messageLabel.getPreferredSize.width+padding,padding+yesWidth+padding+noWidth+padding+cancelPreferredSize.width+padding) }
   def getHeight = padding*3+messageLabel.getPreferredSize.height+cancelButton.getPreferredSize.height }

  AnyLayout.useAnyLayout(dialog.getContentPane,0.5F,0.5F,sizeCalculator,null)

  for (p <- List(messageLabel -> messageConstraint,yesButton -> yesConstraint, noButton -> noConstraint, cancelButton -> cancelConstraint))
   dialog.add(p._1,p._2)

  var returnValue=JOptionPane.CANCEL_OPTION
  val returnValues=new mutable.HashMap[JButton, Integer]

  for (p <- List(yesButton -> JOptionPane.YES_OPTION, noButton -> JOptionPane.NO_OPTION, cancelButton -> JOptionPane.CANCEL_OPTION))
   returnValues.put(p._1,p._2)

  val keyListener=new KeyAdapter {
   override def keyTyped(e: KeyEvent) = List('y' -> yesButton,'n' -> noButton,'c' -> cancelButton).find(e.getKeyChar==_._1) match {
    case Some(p) => p._2 doClick 100
    case None => () }

   override def keyPressed(e: KeyEvent) = if (e.getKeyCode==KeyEvent.VK_ESCAPE) cancelButton doClick 100 }

  for (b <- List(yesButton, noButton, cancelButton)) {
   b addKeyListener keyListener
   b addActionListener new ActionListener { def actionPerformed(e: ActionEvent) = { returnValue = returnValues(b).intValue
                                                                                    dialog setVisible false } } }

  dialog.pack
  dialog setLocationRelativeTo frame
  dialog setVisible true
  returnValue }

 def showLabelsAndConfirmation(frame: JFrame, title: String, message: String, choices: Array[String], theDefault: Int, toConfirm: String) = {
  val dialog=createDialogWithEscapeKeyToClose(title)(frame)
  var choice: String=null
  val buttons=choices.map(new JButton(_: String)).toList
  val checkBox=new JCheckBox
  val messageLabel=new JLabel(message,UIManager.getIcon("OptionPane.questionIcon"),SwingConstants.LEFT)
  val confirmationLabel = new JLabel(toConfirm)

  val calculator=new SizeCalculator { def getWidth = { val messageSize=messageLabel.getPreferredSize
                                                       val max=getPreferredSize(buttons)
                                                       val aPreferredSize=confirmationLabel.getPreferredSize
                                                       val width=List(messageSize.width, max.width, aPreferredSize.width).foldLeft(0)(Math.max(_,_))
                                                       width+padding*2 }
                                      def getHeight = { val messageSize = messageLabel.getPreferredSize
                                                        val max=getPreferredSize(buttons)
                                                        max.height+padding*4+messageSize.height+confirmationLabel.getPreferredSize.height } }
  dialog setModal true
  AnyLayout.useAnyLayout(dialog.getContentPane, 0.5F, 0.5F, calculator, null)
  val messageConstraint = ConstraintUtility topCentre padding
  val confirmationConstraint = buildConstraint.setLeft(paddingFunction).setTop(add(getFarOffset(messageLabel),paddingFunction)).setWidth(preferredSize).setHeight(preferredSize)
  val firstButtonConstraint = buildConstraint.setLeft(paddingFunction).setTop(new Function[LayoutContext, Integer] {
   def run(context: LayoutContext) = context.getLayoutInfo(confirmationLabel).getFarOffset.intValue+padding } ).setWidth(preferredSize).setHeight(preferredSize)

  val checkBoxConstraint = RelativeConstraints.rightOf(confirmationLabel, padding)
  dialog.add(messageLabel, messageConstraint)
  dialog.add(buttons(0),firstButtonConstraint)

  for (p <- buttons.tail.zip(buttons)) dialog.add(p._1,RelativeConstraints.rightOf(p._2, padding))

  buttons(theDefault).requestFocusInWindow
  dialog.add(confirmationLabel,confirmationConstraint)
  dialog.add(checkBox,checkBoxConstraint)
  dialog.pack

  dialog setLocationRelativeTo frame

  for (b <- buttons)
   b.addActionListener { new ActionListener { def actionPerformed(e: ActionEvent) = { choice=b.getText
                                                                                      dialog setVisible false } } }

  dialog setVisible true
  val theChoice = choice
  new { def confirmationTicked=checkBox.isSelected
        def choice=theChoice } }

 private def getPreferredSize(buttons: List[JButton]) = { var maxWidth: Int = buttons(0).getPreferredSize.width
                                                          var maxHeight: Int = buttons(0).getPreferredSize.height

                                                          for (b <- buttons.tail) {
                                                           maxWidth += (padding+b.getPreferredSize.width)
                                                           maxHeight = Math.max(maxHeight,b.getPreferredSize.height) }

                                                          new Dimension(maxWidth, maxHeight) }
 def showNonModalMessageDialog(title: String, message: String)(implicit frame: JFrame): Unit =
  showNonModalMessageDialog(title, new JLabel("<html>"+message+"</html>", getIcon("OptionPane.questionIcon"), LEFT))

 def showNonModalMessageDialog(title: String, component: Component)(implicit frame: JFrame): Unit = {
  val dialog=createDialogWithEscapeKeyToClose(title)
  dialog setDefaultCloseOperation WindowConstants.DISPOSE_ON_CLOSE

  val container = dialog.getContentPane
  val okButton = new JButton("OK")

  AnyLayout.useAnyLayout(container, 0.5F, 0.5F, new SizeCalculator {
   def getHeight = padding*3+component.getPreferredSize.height+okButton.getPreferredSize.height
   def getWidth = component.getPreferredSize.width+padding*2 } , null)

  container.add(component,ConstraintUtility.topCentre(padding))
  container.add(okButton,ConstraintUtility.bottomCentre(paddingFunction))
  okButton addActionListener new ActionListener { def actionPerformed(event: ActionEvent) = dialog.dispose }
  dialog.pack
  dialog setLocationRelativeTo frame
  dialog setVisible true } }

object Dialogs {
 def createDialogWithEscapeKeyToClose(title: String)(implicit frame: JFrame) = {
  val result=new JDialog(frame, title)
  result.getRootPane.registerKeyboardAction(new ActionListener { def actionPerformed(e: ActionEvent) = result setVisible false },
                                            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW)
  result } }

case class Bar(frame: JFrame) { val bar = new JMenuBar
                                frame setJMenuBar bar
                                def apply(menu: Menu) = bar add menu.menu then this }
case class Menu(text: String) { val menu = new JMenu(text)
                                def apply(item: Item) = menu add item.item then this
                                def mnemonic(c: Char) = menu setMnemonic c then this }
case class Item(text: String) {
 val item = new JMenuItem(text)
 def mnemonic(c: Char) = item setMnemonic c then this
 def listener(e: () => Unit) = item addActionListener new ActionListener { def actionPerformed(ev: ActionEvent) = e() } then this
 def accelerator(stroke: KeyStroke) = item setAccelerator(stroke) then this
 def accelerator(vk: Int) = item setAccelerator KeyStroke.getKeyStroke(vk, InputEvent.CTRL_DOWN_MASK) then this }

import java.awt.{LayoutManager, Container}
case class Panel(parent: Container, layoutConstraint: Object, layout: LayoutManager) {
 val panel = new javax.swing.JPanel(layout)
 parent add (panel, layoutConstraint)
 def apply(component: Component) = panel add component then this }

case class Button(text: String) extends JButton {
 def listener(listener: () => Unit) = addActionListener (new ActionListener { def actionPerformed(e: ActionEvent) = listener() } ) then this
 def mnemonic(m: Int) = this setMnemonic m then this }

case class RadioButton(text: String) extends javax.swing.JRadioButton { def mnemonic(c: Char) = this setMnemonic c then this
                                                                        def opaque(o: Boolean) = this setOpaque o then this
                                                                        def mnemonic(m: Int) = this setMnemonic m then this
                                                                        def font(f: java.awt.Font) = this setFont f then this
                                                                        def selected(s: Boolean) = this setSelected s then this }

case class Label(text: String) extends JLabel { def horizontalAlignment(ha: Int) = this setHorizontalAlignment ha then this }

object Swing { def ButtonGroup(buttons: javax.swing.JRadioButton*) = { val bg = new javax.swing.ButtonGroup
                                                                       buttons foreach (bg add _)
                                                                       bg }
               def item(string: String, mnemonic: Char, accelerator: Int, action: => Unit): JMenuItem = {
                val theItem=item(string, mnemonic, action)
                theItem setAccelerator KeyStroke.getKeyStroke(accelerator, InputEvent.CTRL_DOWN_MASK)
                theItem }

               def item(string: String, mnemonic: Char, action: => Unit): JMenuItem = {
                val item = new JMenuItem(string)
                item setMnemonic mnemonic
                item addActionListener new ActionListener { def actionPerformed(e: ActionEvent) = action }
                item } }

class TestMenus extends Application {
 val frame=new JFrame
 Bar(frame) {
  Menu("File") {
   Item("New") listener (() => println("new")) } } {
  Menu("Edit") }
 frame setVisible true }

import Implicits._
object Buttons { def closeButton(caption: String, window: Window) = new JButton(caption) listener { window setVisible false
                                                                                                    window dispose } }
                                                                      

object Implicits {
 implicit def buttons(b: JButton): { def listener(ef: => Unit): JButton } = new { def listener(ef: => Unit) =
  b addActionListener new ActionListener { def actionPerformed(e: ActionEvent) = ef } then b }
 implicit def menuitems[T <: JMenuItem](t: T): { def listener(ef: => Unit): T } = new { def listener(ef: => Unit) =
  t addActionListener new ActionListener { def actionPerformed(e: ActionEvent) = ef } then t }
 implicit def windows[T <: Window](t: T) = new RichWindow[T](t)
 implicit def jcomponents[T <: JComponent](t: T) = new RichComponent[T](t) }

case class RichWindow[T <: Window](t: T) { def size(width: Int, height: Int) = t setSize (width, height) then t }
case class RichComponent[T <: JComponent](t: T) { def opaque(o: Boolean) = t setOpaque true then t }

case class SystrayMessage(msg: String) {
 import java.awt.{SystemTray, TrayIcon}
 import java.awt.event.{ActionListener, ActionEvent}
 import java.awt.image.BufferedImage
 import javax.swing.{JFrame, Timer}

 val tray = SystemTray.getSystemTray
 val icon = new TrayIcon(new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB))
 tray add icon
 icon displayMessage (msg, msg, TrayIcon.MessageType.INFO)
 val timer = new Timer(1000, new ActionListener { def actionPerformed(e: ActionEvent) = tray remove icon } )
 timer setRepeats false
 timer start }

import javax.swing.{JEditorPane, JScrollPane}
import java.awt.Toolkit
import java.awt.event.AWTEventListener
import java.awt.AWTEvent
import java.io.IOException

object ScrollableEditorPane { def apply(scrollPane: JScrollPane) = try {
 val editorPane = new JEditorPane(ScrollableEditorPane.getClass.getResource("/help/index.html"))
 Toolkit.getDefaultToolkit.addAWTEventListener(new AWTEventListener {
  def eventDispatched(event: AWTEvent) =
   if (event.isInstanceOf[KeyEvent] && event.getSource == editorPane) {
    val keyEvent = event.asInstanceOf[KeyEvent]
    if (keyEvent.getID == KeyEvent.KEY_PRESSED && (keyEvent.getKeyCode == KeyEvent.VK_DOWN || keyEvent.getKeyCode == KeyEvent.VK_UP)) {
     keyEvent.consume
     val viewport = scrollPane.getViewport
     val rectangle = viewport.getViewRect.getBounds
     val unitIncrement = editorPane.getScrollableUnitIncrement(rectangle, SwingConstants.VERTICAL, 1)
     rectangle.translate(0, unitIncrement * (if (keyEvent.getKeyCode == KeyEvent.VK_DOWN) 1 else -1))
     editorPane.scrollRectToVisible(rectangle) } } }, AWTEvent.KEY_EVENT_MASK)
 Left(editorPane) } catch { case e: IOException => Right(e) } }

import javax.swing.text.Document
trait DocumentValidator { def isValid(document: Document): Boolean }

import javax.swing.event.{DocumentListener, DocumentEvent}
import java.awt.Color
case class ValidatingDocumentListener(parentComponent: Component, valid: Color, invalid: Color, validator: DocumentValidator) extends DocumentListener {
 def insertUpdate(event: DocumentEvent) = changedUpdate(event)
 def removeUpdate(event: DocumentEvent) = changedUpdate(event)
 def changedUpdate(event: DocumentEvent) = parentComponent.setBackground(
  if (isValid(event.getDocument) || event.getDocument.getLength == 0) valid else invalid)
 def isValid(document: Document) = validator isValid document }

import javax.swing.{JPanel, JTextField}
import anylayout.AnyLayout.useAnyLayout
import anylayout.extras.ConstraintUtility
import fpeas.function.FunctionUtility
import ipsim.lang.Runnables

trait LabelledTextField[T <: JTextField] { def panel: JPanel
                                           def field: T }
object LabelledTextField {
 def createLabelledTextField[T <: JTextField](text: String, _field: T) = { val _panel = new JPanel opaque false
                                                                           val label = new JLabel(text) { setLabelFor(_field) }
                                                                           _panel add label
                                                                           _panel add _field
                                                                           new LabelledTextField[T] { def panel = _panel
                                                                                                      def field = _field } } 
 def createLabelledTextField2[T <: JTextField](text: String, field: T) = {
  val panel: JPanel = new JPanel opaque false
  val label = new JLabel(text) { setLabelFor(field) }
  useAnyLayout(panel, 0.5f, 0.5f, new SizeCalculator { def getHeight = Math.max(field.getPreferredSize.height, label.getPreferredSize.height)
                                                       def getWidth = field.getPreferredSize.width + label.getPreferredSize.width + 5 },
               ConstraintUtility.typicalDefaultConstraint(Runnables.throwRuntimeException))
  val constant: Function[LayoutContext, Integer] = FunctionUtility constant 0
  panel.add(label, ConstraintUtility topLeft (constant, constant))

  { val _panel = panel
    val _field = field
    new LabelledTextField[T]{ val panel = _panel
                              val field = _field } } } }
