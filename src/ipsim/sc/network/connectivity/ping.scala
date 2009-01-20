package ipsim.network.connectivity.ping

import ipsim.network.IncomingPacketListener

import scala.collection.mutable.ListBuffer
import PingResults.{ttlExpired, pingReplyReceived, netUnreachable, hostUnreachable}
case class PingListener(private val identifier: Object) extends IncomingPacketListener {
 val pingResults = new ListBuffer[PingResults]()
 def packetIncoming(packet: Packet, source: PacketSource, destination: PacketSource)(implicit network: Network) =
  packet.asIPPacket.foreach(ipPacket => pingResults += (
   if (ipPacket.data == TimeToLiveExceeded) ttlExpired(ipPacket.sourceIP) else
    if (ipPacket.data == Reply) pingReplyReceived(ipPacket.sourceIP) else
     if (ipPacket.data == NetUnreachable) netUnreachable(ipPacket.sourceIP) else
      if (ipPacket.data == HostUnreachable) hostUnreachable(ipPacket.sourceIP) else throw null)) }
   
object TimeToLiveExceeded { override def toString = "Time to Live Exceeded" }
object NetUnreachable { override def toString = "Net Unreachable" }
object HostUnreachable { override def toString = "Host Unreachable" }

sealed abstract class PingResults(val replyingHost: SourceIPAddress) { val pingReplyReceived = false
                                                                       val ttlExpired = false
                                                                       val hostUnreachable = false
                                                                       val timedOut = false
                                                                       def asString: String
                                                                       override def toString = asString }

object PingResults {
 def timedOut(reporter: SourceIPAddress) = new PingResults(reporter) { override def asString = reporter + " reports Request timed out"
                                                                       override val timedOut = true }
 def hostUnreachable(reporter: SourceIPAddress) = new PingResults(reporter) { override def asString = reporter + " reports Host unreachable"
                                                                              override val hostUnreachable = true }
 def ttlExpired(reporter: SourceIPAddress) = new PingResults(reporter) { override def asString = reporter + " reports TTL expired"
                                                                         override val ttlExpired = true }
 def pingReplyReceived(reporter: SourceIPAddress) = new PingResults(reporter) { override def asString = "Reply received from " + reporter
                                                                                override val pingReplyReceived = true }
 def netUnreachable(gatewayIP: SourceIPAddress) = new PingResults(gatewayIP) { override def asString = gatewayIP + " reports Net Unreachable" } }
 
sealed abstract class PingData
case object Request extends PingData
case object Reply extends PingData

import PingResults.{timedOut, hostUnreachable, ttlExpired, pingReplyReceived, netUnreachable}
import ipsim.lang.Implicits._
object Pinger { def ping(computer: Computer, destIP: IPAddress, ttl: Int)(implicit network: Network): List[PingResults] = {
 val hasRoute = computer.routeFor(destIP).isDefined
 val identifier = new Object
 val pingListener = PingListener(identifier)
 computer.incomingPacketListeners += pingListener
 try { computer.ipAddresses.toList.firstOption match {
  case None => List(PingResults.netUnreachable(SourceIPAddress(IPAddress.valueOf("127.0.0.1").get)))
  case Some(ipAddress1) => {
   val aRandomSourceIP = SourceIPAddress(ipAddress1)
   computer.routeFor(destIP) match {
    case None => List(netUnreachable(aRandomSourceIP))
    case Some(route) =>
     if (!hasRoute) List(netUnreachable(aRandomSourceIP)) else {
      val packet = IPPacket(SourceIPAddress(computer.cardsFor(route).head.ipAddress), DestIPAddress(destIP), ttl, identifier, Request)
      network.packetQueue.enqueueOutgoingPacket(packet, computer)
      network.packetQueue.processAll
      // val pingResults2 = pingListener.getPingResults
      // there was some code here checking for pingResults2==null, but it looked like it never was null.
      pingListener.pingResults.toList.map(result =>
       if (result.timedOut && route.gateway == computer.cardsFor(route).head.ipAddress) hostUnreachable(result.replyingHost) else result) } } } } }
 finally { computer.incomingPacketListeners -= pingListener } } }
