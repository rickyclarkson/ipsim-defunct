package ipsim.gui

import ipsim.network.Network

object Main extends Application { swing.SwingUtilities.invokeLater {
 import javax.swing.UIManager._
 setLookAndFeel(getSystemLookAndFeelClassName)
 java.awt.Toolkit.getDefaultToolkit setDynamicLayout true
 import Global.frame
 implicit val hack = frame
 implicit val network = new Network

 Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler { def uncaughtException(thread: Thread, exception1: Throwable) = {
  exception1.printStackTrace()
  ExceptionReportDialog.handle(exception1) } }) 

 frame setVisible true
 MenuHandler.networkNew(new scala.Random) } }
