package ipsim.web

import javax.swing.{JOptionPane, JFrame}
import java.io.{IOException, PrintWriter, BufferedReader, InputStreamReader}
import ipsim.lang.Implicits._
import java.net.Socket
import ipsim.gui.{UserMessages, Global}

object Web {
 val server = "fw4.cms.salford.ac.uk"
 val port = 80
 val cgi = "/cgi-bin/ipsim2.cgi"
 def webInteraction(command: String, testNumber: String, line1: String, line2: String): String = {
  val output = new StringBuilder()
  val socket = new Socket(server, port)
  socket setSoTimeout 5000
  val writer = new PrintWriter(socket.getOutputStream)
  val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
  writer.println("POST "+cgi+" HTTP/1.0")
  writer.println("Host: "+server)
  writer.println("Content-Type: text/plain")
  writer.println("Content-Length: "+(5+Global.customerNumber.length+command.length+testNumber.length+line1.length+line2.length))
  writer.println
  writer.println(command + ' ' + Global.customerNumber + ' ' + testNumber + ' ' + line1)
  writer.println(line2)
  writer.flush

  var state = 0
  while (true) {
   try { val line = reader.readLine
         if (line == null) return output.toString
         if (state == 0 && line.length == 0)
          state = 1
         else if (state == 1) { output append line
                                output append '\n' } } finally { writer.close
                                                                 reader.close
                                                                 socket.close } }
  throw null }
 def putException(frame: JFrame, exceptionText: String, currentConfig: String) = try {
  val sequence = 1
  val output = webInteraction("PUT", "", "exception/log" + sequence, exceptionText) + webInteraction("PUT", "", "exception/save" + sequence, currentConfig)
  if (!output.matches("102: exception/log\\d+\\.\\d+\n102: exception/save\\d+\\.\\d+\n"))
   handleError(frame)
  else { val values = getLogAndSaveValues(output)
         UserMessages.message("Tell your tutor to look at exception " + values._1 + " and log " + values._2 + '.') } } catch {
          case e: IOException => handleError(frame) }
 private def handleError(implicit frame: JFrame): Unit = handleError("Problem accessing network")

 private def handleError(title: String)(implicit frame: JFrame): Unit =
  JOptionPane.showMessageDialog(frame, title, "Error", JOptionPane.ERROR_MESSAGE)

 def getProblem(testNumber: String)(implicit frame: JFrame) = try {
  val returnCode = webInteraction("TGET", "", testNumber, "")
  if (returnCode startsWith "3") handleError("The test cannot be done at this time") then None
  else if (!returnCode.startsWith("101")) handleError("Problem accessing network") then None
  else Some(returnCode) } catch { case exception: IOException => handleError("Problem accessing network") then None }

 def getNamedConfiguration(configurationName: String): Either[(String, String), String] = try {
  val input = webInteraction("CGET", "", configurationName, "")
  if (input startsWith "101: OK\n") Left(configurationName, input substring "101: OK\n".length) else
   if (input startsWith "101: OK \"") Left(input.substring("101: OK \"".length, input.indexOf('"', "101: OK \"".length + 1)),
                                           input.substring(input.indexOf('\n') + 1)) else
                                            if (input startsWith "407") Right("Cannot download configuration "+configurationName) else
                                             throw null } catch { case exception: IOException => Right("Problem accessing network") }
 def putSUProblem(user: String, suProblem: String) = webInteraction("TPUT", "456", "su/problems/" + user, suProblem)

 import ipsim.gui.UserMessages.message
 def putSUSolution(frame: JFrame, testNumber: String, email: String, suSolution: String) = {
  val returnCode = webInteraction("TPUT", testNumber, "su/solutions/" + email, suSolution)
  if (returnCode startsWith "3") message("The test cannot be done at this time") then Right(new IOException(returnCode)) else Left(returnCode) }
 def putNamedConfiguration(name: String, string: String) = webInteraction("PUT", "", "saved/" + name, string)
 def getLogAndSaveValues(output: String) = {
  val matcher=java.util.regex.Pattern compile """102: exception/log(\d+\.\d+)\n102: exception/save(\d+\.+\d+)\n""" matcher output
  (matcher group 1,matcher group 2) } }
