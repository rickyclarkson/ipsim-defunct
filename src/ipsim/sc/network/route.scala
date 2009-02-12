package ipsim.network.route

import ipsim.network.{Computer, Network}

import ipsim.awt.Point
import ipsim.lang.Implicits._

object RoutingTableTests {
 def testRetention = {
  implicit val network=new Network
  val context=network.contexts.append(new NetworkContext)
  val computer= Computer(Point(100, 200)).useAndReturn(c => context.visibleComponentsVar = context.visibleComponentsVar prependIfNotPresent c).withID(
   network.generateComputerID)
  val table=new RoutingTable
  val withDrivers = Card(false, Right(computer)).useAndReturn(c => context.visibleComponentsVar = context.visibleComponentsVar.prependIfNotPresent(c)).
   installDeviceDrivers
  withDrivers.ipAddress=IPAddress.valueOf("146.87.1.1").get
  withDrivers.netMask=NetMask.fromPrefixLength(24).get
  val route=new Route(NetBlock.zero,IPAddress.valueOf("146.87.1.2").get)
  table.add(route)
  table.defaultRoutes.first == route }

 def testGetBroadcastRoute = {
  val (boilerplate, card, cardDrivers, computer) = oneInstalledCardOnOneComputer
  implicit val network=boilerplate
  cardDrivers.ipAddress=IPAddress.valueOf("146.87.1.1").get
  cardDrivers.netMask=NetMask.fromPrefixLength(24).get
  val route=computer.routeFor(IPAddress.valueOf("146.87.1.255").get).get
  val card2Drivers=computer.cardsFor(route).apply(0)
  card.cardDrivers.get==card2Drivers && route.netBlock.broadcastAddress==IPAddress.valueOf("146.87.1.255").get }

 def oneInstalledCardOnOneComputer: (Network, Card, CardDrivers, Computer) = {
  implicit val network=new Network
  val context=network.contexts.append(new NetworkContext)
  val computer=Computer(Point(100, 200)) useAndReturn (c => context.visibleComponentsVar = context.visibleComponentsVar.prependIfNotPresent(c))
  val card=Card(false, Right(computer)) useAndReturn (c => context.visibleComponentsVar = context.visibleComponentsVar.prependIfNotPresent(c))
  (network, card, card.installDeviceDrivers, computer) } }

import ipsim.gui.RouteInfo

case class Route(val netBlock: NetBlock, val gateway: IPAddress) {
 override def toString = "Destination: "+(if (netBlock.isZero || netBlock.netMask.isZero) "default" else netBlock.toString)+" Gateway: "+
                                         (if (gateway.isZero) "default" else gateway.toString)
 def isDefault = netBlock==NetBlock.zero
 def isRouteToSelf(computer: Computer)(implicit network: Network) = computer.cards flatMap (_.cardDrivers) exists (_.ipAddress == gateway)
 def asCustomString = RouteInfo(netBlock, gateway).toString }
                        
import scala.xml._

import scala.collection.mutable.ListBuffer

class RoutingTable {
 val routes = new ListBuffer[Route]
 def add(route: Route) = routes += route
 def remove(route: Route) = routes -= route
 def replace(route: Route, newRoute: Route) = routes.update(routes.indexOf(route), newRoute)
 def defaultRoutes = routes filter (_.isDefault)
 def explicitRoutes = routes filter (!_.isDefault)
}
