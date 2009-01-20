package ipsim.network.route

import ipsim.network.{Computer, Network}

import ipsim.awt.Point
import ipsim.persistence.{ReadDelegate, WriteDelegate, XMLSerialiser, XMLDeserialiser}
import org.w3c.dom.Node
import ipsim.lang.Implicits._

object RoutingTableTests {
 def testRetention = {
  implicit val network=new Network
  val context=network.contexts.append(new NetworkContext)
  val computer= Computer(Point(100, 200)).useAndReturn(c => context.visibleComponentsVar = context.visibleComponentsVar prependIfNotPresent c).withID(
   network.generateComputerID)
  val table=RoutingTable()
  val withDrivers = Card(false, Right(computer)).useAndReturn(c => context.visibleComponentsVar = context.visibleComponentsVar.prependIfNotPresent(c)).
   installDeviceDrivers
  withDrivers.ipAddress=IPAddress.valueOf("146.87.1.1").get
  withDrivers.netMask=NetMask.fromPrefixLength(24).get
  val route=new Route(NetBlock.zero,IPAddress.valueOf("146.87.1.2").get)
  table.add _
  table.add(Some(computer),route)(network)
  table.defaultRoutes.head==route }

 def testGetBroadcastRoute = {
  val (boilerplate, card, cardDrivers, computer) = oneInstalledCardOnOneComputer
  implicit val network=boilerplate
  cardDrivers.ipAddress=IPAddress.valueOf("146.87.1.1").get
  cardDrivers.netMask=NetMask.fromPrefixLength(24).get
  val route=computer.routeFor(IPAddress.valueOf("146.87.1.255").get).get
  val card2Drivers=computer.cardsFor(route).apply(0)
  card.cardDrivers.get==card2Drivers && route.netBlock.broadcastAddress==IPAddress.valueOf("146.87.1.255").get }

 def oneInstalledCardOnOneComputer: (Network, Card, CardDrivers, Computer) = {
  implicit val network=Network()
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
                        

object Route {
 object delegate extends ReadDelegate[Route] with WriteDelegate[Route] {
  def writeXML(serialiser: XMLSerialiser, route: Route) = { serialiser.writeObject(route.netBlock, "destination", NetBlock.delegate)
                                                            serialiser.writeAttribute("gateway", route.gateway.toString) }
  def readXML(deserialiser: XMLDeserialiser, node: Node, ignored: Option[Route]) =
   Route(deserialiser.readObject(node, "destination", NetBlock.delegate).get, IPAddress.valueOf(deserialiser.readAttribute(node, "gateway").get).get)
  val construct = null
  val identifier = "ipsim.persistence.delegates.RoutingTableEntryDelegate" } }

case class RoutingTable {
 private var _routes: Stream[Route] = Stream.empty
 def add(computer: Option[Computer], route: Route)(implicit network: Network) = computer match {
  case Some(computer) => if (computer.isLocallyReachable(route.gateway)) { _routes = _routes append List(route)
                                                                           true } else false
  case None => { _routes = _routes append List(route)
                 false } }
 def remove(route: Route) = _routes = _routes filter (_ != route)
 def replace(route: Route, newRoute: Route) = _routes = _routes.map(x => if (x==route) newRoute else x)
 def defaultRoutes = routes filter (_.isDefault)
 def explicitRoutes = routes filter (!_.isDefault)
 def routes = _routes }

object RoutingTable { 
 def delegate(implicit network: Network) = new ReadDelegate[RoutingTable] with WriteDelegate[RoutingTable] {
  def writeXML(serialiser: XMLSerialiser, table: RoutingTable) =
   (for ((entry, a) <- table.routes.zipWithIndex) serialiser.writeObject(entry, "entry "+a, Route.delegate)) then serialiser
  def readXML(deserialiser: XMLDeserialiser, node: Node, table: Option[RoutingTable]) = {
   deserialiser.getObjectNames(node).filter(_!="computer").toList.sort(_.compareTo(_) < 0).foreach(
    name => table.get.add(None, deserialiser.readObject(node, name, Route.delegate).get))
   table.get }
  def construct=Some(RoutingTable())
  val identifier="ipsim.persistence.delegates.RoutingTableDelegate" } }
                                                                                                                                                    
object InvalidRouteTest { def apply = { implicit val network = new Network
                                        val context = network.contexts append new NetworkContext
                                        val computer = new Computer(Point(200, 200)) withID network.generateComputerID
                                        context.visibleComponentsVar ::= computer
                                        val route = Route(NetBlock zero, IPAddress.valueOf("146.87.1.1").get)
                                        !computer.routingTable.add(Some(computer), route) } }

import java.io.File
object RoutingTableBugs { def apply: Boolean = { implicit val network = new Network
                                                 network loadFromFile new File("datafiles/unconnected/1.14.ipsim")
                                                 val netMask = NetMask.valueOf("192.0.0.0").get
                                                 val computers = network computersByIP IPAddress.valueOf("10.0.0.1").get
                                                 for { computer <- computers
                                                       possibleCards = computer.cardsWithDrivers filter (_.netMask == Some(netMask))
                                                       if !possibleCards.isEmpty } return !computer.routingTable.defaultRoutes.isEmpty
                                                 false } }
