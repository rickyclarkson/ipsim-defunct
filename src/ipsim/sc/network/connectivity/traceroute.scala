package ipsim.network.connectivity.traceroute

import ipsim.network.{Computer, IPAddress, Network}
import ipsim.network.connectivity.ping.Pinger

object Traceroute { def trace(computer: Computer, destIP: IPAddress, maxTTL: Int)(implicit network: Network) = {
 val results = TracerouteResultsUtility.newTracerouteResults
 var stop = false
 var ttl = 1
 while (ttl <= maxTTL && !stop) {
  val pingResults = Pinger.ping(computer, destIP, ttl)
  for (result <- pingResults) { results.add(ttl+": "+(if (result.ttlExpired || result.pingReplyReceived) result.replyingHost else result))
                                if (!result.timedOut && !result.ttlExpired) stop = true }
  ttl += 1 }
 results } }

object TracerouteResultsUtility { def newTracerouteResults = new TracerouteResults { val builder = new StringBuilder
                                                                                     override def toString = builder.toString
                                                                                     def add(obj: String) = builder append obj append '\n'
                                                                                     def size = builder.toString split "\n" length } }
trait TracerouteResults { def add(obj: String): Unit
                          def size: Int }

import java.io.File
object TracerouteTest { def apply = {
 implicit val network = new Network
 network loadFromFile new File("datafiles/fullyconnected/1.6.ipsim")
 val ipAddress = IPAddress.valueOf("146.87.1.1").get
 network.computersByIP(ipAddress).forall(computer => Traceroute.trace(computer, IPAddress.valueOf("146.87.4.3").get, 30).toString.startsWith(
  "1: 146.87.1.3\n2: 146.87.2.3\n3: 146.4.3")) } }
