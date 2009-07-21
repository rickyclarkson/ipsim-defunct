package ipsim.network.connectivity

import scala.collection.mutable
import ipsim.network.Implicits._
import ipsim.network.SourceIPAddress
import ipsim.network.connectivity.ping.{PingData, PingResults, Request, Reply}
import PingResults.{timedOut, hostUnreachable, ttlExpired, pingReplyReceived, netUnreachable}
import ipsim.gui.Global

class PacketQueue {
 var pendingRequests = new mutable.ListBuffer[Runnable]
 val emptyQueueListeners = new mutable.ArrayBuffer[Runnable]

 def enqueueOutgoingPacket(packet: Packet, source: PacketSource)(implicit network: Network) =
  for (li <- source.outgoingPacketListeners)
   pendingRequests += new Runnable { def run = { li.packetOutgoing(packet, source)
                                                 processed(this)
                                                 handleIfEmpty } } 
 def enqueueIncomingPacket(packet: Packet, source: PacketSource, destination: PacketSource)(implicit network: Network) =
  for (li <- destination.incomingPacketListeners)
   pendingRequests += new Runnable { def run = { li.packetIncoming(packet, source, destination)
                                                  pendingRequests -= this
                                                  handleIfEmpty } }
 def handleIfEmpty = if (pendingRequests isEmpty) { val temp=new mutable.HashSet[Runnable]
                                                    temp ++= emptyQueueListeners
                                                    for (runnable <- temp) { emptyQueueListeners -= runnable
                                                                             runnable.run } }
 def addEmptyQueueListener(runnable: Runnable) = emptyQueueListeners += runnable
 def processed(runnable: Runnable) = pendingRequests -= runnable
 def processAll = { while (!pendingRequests.isEmpty) pendingRequests.remove(0).run
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

