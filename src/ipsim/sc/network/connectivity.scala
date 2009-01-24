package ipsim.network.connectivity

import scala.collection.{jcl, mutable}
import ipsim.network.Implicits._
import ipsim.network.SourceIPAddress
import ipsim.network.connectivity.ping.{PingData, PingResults, Request, Reply}
import PingResults.{timedOut, hostUnreachable, ttlExpired, pingReplyReceived, netUnreachable}
import ipsim.gui.Global

class PacketQueue {
 var pendingRequests = new java.util.LinkedList[Runnable]
 val emptyQueueListeners = new jcl.ArrayList[Runnable]

 def enqueueOutgoingPacket(packet: Packet, source: PacketSource)(implicit network: Network) =
  for (li <- source.outgoingPacketListeners)
   pendingRequests add { new Runnable { def run = { li.packetOutgoing(packet, source)
                                                    processed(this)
                                                    handleIfEmpty } } }
 def enqueueIncomingPacket(packet: Packet, source: PacketSource, destination: PacketSource)(implicit network: Network) =
  for (li <- destination.incomingPacketListeners)
   pendingRequests add new Runnable { def run = { li.packetIncoming(packet, source, destination)
                                                  pendingRequests remove this
                                                  handleIfEmpty } }
 def handleIfEmpty = if (pendingRequests isEmpty) { val temp=new mutable.HashSet[Runnable]
                                                    temp ++= emptyQueueListeners
                                                    for (runnable <- temp) { emptyQueueListeners remove runnable
                                                                             runnable.run } }
 def addEmptyQueueListener(runnable: Runnable) = emptyQueueListeners add runnable
 def processed(runnable: Runnable) = pendingRequests remove runnable
 def processAll = { while (!pendingRequests.isEmpty) pendingRequests.remove.run
                    while (!emptyQueueListeners.isEmpty) emptyQueueListeners.remove(0).run } }

import ipsim.network.connectivity.ping.Pinger                                                   
import ipsim.lang.Implicits._
object ConnectivityTest { def testConnectivity(logger: String => Unit, progress: Int => Unit)(implicit network: Network) = {
 val computers = network.all flatMap (_.asComputer)
 var results: List[String] = Nil
 var total=0
 var currentComputer = 0
 for (computer <- computers) { computer.ipAddresses.toList.firstOption foreach { sourceIP =>
  for (computer2 <- computers; if computer2!=computer; card <- computer2.cardsWithDrivers) {
   val ipAddress = card.ipAddress
   total += 1
   for (another <- computers)
    another.arpTable.clear
   logger("Pinging "+ipAddress+" from "+computer.ipAddresses.toList.head)
   var pingResults: List[PingResults] = Pinger.ping(computer, DestIPAddress(ipAddress), Global.defaultTimeToLive)
   val firstResult = pingResults.head
   val isBroadcast = card.netBlock.broadcastAddress == ipAddress
   if (!firstResult.pingReplyReceived || isBroadcast) {
    if (isBroadcast) pingResults = List(PingResults.hostUnreachable(SourceIPAddress(sourceIP))) ++ pingResults.tail 
    results ++= List(sourceIP+" cannot ping "+ipAddress+": "+firstResult) } }
                                                                                 progress(currentComputer * 100 / computers.size)
  currentComputer += 1 } }
 new { val percentConnected = (100 - results.size * 100.0 / total).toInt
       val outputs = results
       override val toString = { val builder = new StringBuilder append percentConnected append "% connected\n"
                                 for (string <- results) builder append string append '\n'
                                 builder.toString } } } }

object PingTester { def testPing(sourceIP: IPAddress, destIP: IPAddress)(implicit network: Network) =
 network.computersByIP(sourceIP) flatMap (computer => Pinger.ping(computer, destIP, Global.defaultTimeToLive)) }

import java.io.File
import PingTester.testPing

object BroadcastPingTest { def apply = { implicit val network = new Network
                                         network loadFromFile new File("datafiles/fullyconnected/broadcast1.ipsim")
                                         testPing(IPAddress.valueOf("146.87.1.1").get, IPAddress.valueOf("146.87.1.255").get).size == 2 } }

object FullyConnectedFilesTest {
 def test: Boolean = {
  for (file <- new File("datafiles/fullyconnected").listFiles) {
   implicit val network = new Network
   network loadFromFile file
   val results = ConnectivityTest.testConnectivity(s => (), i => ())
   if (results.percentConnected != 100) {
    println(file+": "+results)
    return false } }
  true } }

object PingerTest {
 def apply = {
  implicit val network = new Network
  network loadFromFile new File("datafiles/unconnected/pingertest1.ipsim")
  val ip4_3 = IPAddress.valueOf("146.87.4.3").get
  val ip4_1 = IPAddress.valueOf("146.87.4.1").get
  network.computersByIP(IPAddress.valueOf("146.87.1.1").get) forall (computer => {
   val results = Pinger.ping(computer, ip4_3, Global.defaultTimeToLive)
   results.size==1 && results.forall(result => result.hostUnreachable && result.replyingHost.ipAddress == ip4_1) } ) } }

object RoutingLoopTest {
 def apply = { implicit val network = new Network
               network loadFromFile new File("datafiles/fullyconnected/routingloop1.ipsim")
               val ipAddress = IPAddress.valueOf("146.87.1.1").get
               val ipAddress2 = IPAddress.valueOf("146.87.2.1").get
               network.computersByIP(ipAddress) forall (computer => { val results = Pinger.ping(computer, ipAddress2, Global.defaultTimeToLive)
                                                                      results.size == 1 && results.head.ttlExpired } ) } }

object UnconnectedFilesTest {
 def apply: Boolean = { for (file <- new File("datafiles/unconnected").listFiles) { implicit val network = new Network
                                                                           network loadFromFile file
                                                                           val results = ConnectivityTest.testConnectivity(s => (), i => ())
                                                                           if (results.percentConnected != 100)
                                                                            println(file.toString)
                                                                            return false}
               true } }

import ipsim.awt.Point

object ComputerArpIncomingTest { def test = () => {
 implicit val network = new Network
 implicit val context = new NetworkContext
 network.contexts append context

 val card = Card(false, Left(Point origin))
 context.visibleComponentsVar ::= card

 val computer = Computer(Point.origin) withID network.generateComputerID
 context.visibleComponentsVar ::= computer

 card.positions = card.positions.set(0, Right(computer))
 card.installDeviceDrivers
 val cardDrivers = card.cardDrivers.get
 cardDrivers.ipAddress = IPAddress(10)
 import network.{packetQueue => queue}
 val answer = new StringBuilder
 computer.outgoingPacketListeners = computer.outgoingPacketListeners ++ List[OutgoingPacketListener](new OutgoingPacketListener { def packetOutgoing(packet: Packet, source: PacketSource)(implicit network: Network) = {
  if (packet.asArpPacket.isDefined && source.asComputer.isDefined) answer append "Pass" } } )
 val packet = ArpPacket(IPAddress(10), MacAddress(0), IPAddress(5), MacAddress(6), new Object)
 queue enqueueIncomingPacket (packet, card, computer)
 queue.processAll
 answer.toString == "Pass" } }

object ComputerArpOutgoingTest { def test = () => {
 implicit val network = new Network
 val computer = new Computer(Point origin) withID network.generateComputerID
 import network.{packetQueue => queue}
 val answer = new StringBuilder
 computer.outgoingPacketListeners = computer.outgoingPacketListeners ++ List(new OutgoingPacketListener {
  def packetOutgoing(packet: Packet, source: PacketSource)(implicit network: Network) =
   packet.asEthernetPacket foreach (ethPacket => source.asComputer foreach (c => ethPacket.data.asArpPacket foreach (a => answer.append("Pass")))) })
 val arpPacket = ArpPacket(IPAddress(0), MacAddress(5), IPAddress(0), MacAddress(10), new Object)
 queue.enqueueOutgoingPacket(arpPacket, computer)
 queue.processAll
 answer.toString == "Pass" } }

object ArpStoredFromForeignNetworkBug { def apply = {
 implicit val network = new Network
 network loadFromFile new File("datafiles/fullyconnected/arpforeign.ipsim")
 PingTester.testPing(IPAddress.valueOf("146.87.1.1").get, IPAddress.valueOf("146.87.2.1").get)
 network.computersByIP(IPAddress.valueOf("146.87.1.1").get).forall(_.arpTable.macAddress(IPAddress.valueOf("146.87.2.1").get) == None) } }

object IncompleteArpTest { def apply = {
 implicit val network = new Network
 network loadFromFile new File("datafiles/unconnected/hubdisabled.ipsim")

 def p = { val results = testPing(IPAddress.valueOf("146.87.1.1").get, IPAddress.valueOf("146.87.1.2").get)
           results.size == 1 && results.forall(_ hostUnreachable) }

 if (p) { network.hubs(0).power = true
          p } else false } }

object RemoteBroadcastBug { def apply = { implicit val network = new Network
                                          network loadFromFile new File("datafiles/fullyconnected/101.ipsim")
                                          val ip41 = IPAddress.valueOf("146.87.4.1").get
                                          val results = testPing(IPAddress.valueOf("146.87.1.1").get, IPAddress.valueOf("146.87.4.255").get)
                                          results.size == 1 && results(0).pingReplyReceived && results(0).replyingHost.ipAddress == ip41 } }
