package ipsim.network
import java.util.Random

import ipsim.persistence.XMLDeserialiser

import ipsim.awt.Point
import ipsim.lang.StreamProperty

import scala.collection.{mutable, immutable}
import ipsim.io.IOUtility

import java.io.File
import javax.swing.{JFrame, JOptionPane}
import ipsim.network.route.{RoutingTable, Route}
import ipsim.persistence.{XMLSerialiser, Serial}
import org.w3c.dom.Node

import ipsim.gui.GetChildOffset
import ipsim.lang.RichStream
import ipsim.swing.CustomJOptionPane

import ipsim.lang.Implicits._
import ipsim.property.Property
import ipsim.io.PimpIO.{bracketEithers, fileWriter, bufferedWriter}
import java.awt.Rectangle

object Implicits {
 implicit def sourceIPAddress(s: SourceIPAddress) = s.ipAddress
 implicit def destIPAddress(d: DestIPAddress) = d.ipAddress
 implicit def positionOrParent2Point(positionOrParent: Either[Point, PacketSource]): PositionOrParent2Point = new PositionOrParent2Point(positionOrParent)
 class PositionOrParent2Point(positionOrParent: Either[Point, PacketSource]) {
  def toPoint(implicit network: Network) = positionOrParent.fold(point => point, parent => Positions.position(parent, 0)) } }

object ProblemDifficulty {
 def tryTimes[T](times: Int)(task: => Option[T]): T = if (times<=0) throw null else task match { case Some(t) => t
                                                                                                 case None => tryTimes(times-1)(task) }

 def easy(random: Random): Problem = tryTimes(1000) {
  val randomNumber = random.nextInt(65536) << 16
  val address=IPAddress(randomNumber)
  val block=NetBlock(address,NetMask.fromPrefixLength(16).get)
  if (Problem.isValidNetworkNumber(block.networkNumber) && !Problem.isReservedNetworkNumber(block)) Some(Problem(block,3)) else None }
 def medium(random: Random) = generate(random,17,4,4)
 def hard(random: Random) = generate(random,22,2,5)
 def testGeneration(random: Random) = { easy(random)
                                        medium(random)
                                        hard(random)
                                        true }
 def generate(random: Random,randomStart: Int,randomRange: Int,numSubnets: Int): Problem = tryTimes(100) {
  val random1=random nextInt 65536
  val random2=random nextInt 65536
  val random3=random1<<16+random2
  val netmaskLength=random.nextInt(randomRange)+randomStart
  val netmask=NetMask fromPrefixLength netmaskLength get
  val rawNetworkNumber=random3&netmask.rawValue
  if (!(0==(rawNetworkNumber & 0xFF00))) {
   val networkNumber=IPAddress(rawNetworkNumber)
   val netBlock=NetBlock(networkNumber,netmask)
   if (Problem.isValidNetworkNumber(netBlock.networkNumber) && !Problem.isReservedNetworkNumber(netBlock))
    Some(Problem(netBlock,numSubnets))
   else None } else None }

 def valueOf(s: String)(implicit random: Random)=if (s.equalsIgnoreCase("EASY")) easy(random) else
  if (s.equalsIgnoreCase("MEDIUM")) medium(random) else if (s.equalsIgnoreCase("HARD")) hard(random)
                                                   else throw null }

object ProblemConstants { val MIN_SUBNETS=2
                          val MAX_SUBNETS=32 }

case class Problem(val netBlock: NetBlock, val numberOfSubnets: Int) {
 import ProblemConstants._
 if (numberOfSubnets < MIN_SUBNETS) throw new RuntimeException(numberOfSubnets+"<"+MIN_SUBNETS) else
  if (numberOfSubnets > MAX_SUBNETS) throw new RuntimeException(numberOfSubnets+">"+MAX_SUBNETS) else
   if (!Problem.isValidNetworkNumber(netBlock.networkNumber)) throw new RuntimeException(netBlock.networkNumber+" is not a valid network number")

 override def toString = {
  val length=netBlock.netMask.prefixLength
  if (length<0) throw null
  else "Network number: "+netBlock.networkNumber+'/'+length+" Number of subnets: "+numberOfSubnets } }

import scala.xml._

object Problem {
 import ProblemConstants._
 def arbitraryProblem(implicit random: Random)=Problem(NetBlock.arbitraryNetBlock, random.nextInt(MAX_SUBNETS-MIN_SUBNETS+1)+MIN_SUBNETS)
 def isValidNetworkNumber(networkNumber: IPAddress) = networkNumber.rawValue>>>24 match { case 0 => false
                                                                                           case 127 => false
                                                                                           case x => x<224 }
 private val reservedNetBlocks: List[NetBlock] = List("10.0.0.0/8","192.168.0.0/16","172.16.0.0/12") map (NetBlock.valueOf(_).get)
 def isReservedNetworkNumber(n: NetBlock): Boolean = reservedNetBlocks exists (temp => n.networkNumber==(temp.networkNumber & n.netMask) ||
                                                                                        temp.networkNumber==(n.networkNumber & temp.netMask))
 import ProblemDifficulty.tryTimes
 def generateNetworkNumber(random: Random,prefixLength: Int) = tryTimes(1000) {
  new IPAddress(random.nextInt(Integer.MAX_VALUE) << (32-prefixLength)) match { case result => if (!isValidNetworkNumber(result)) None else
   if (isReservedNetworkNumber(NetBlock(result,NetMask.fromPrefixLength(prefixLength) get))) None else Some(result) } }
 object delegate extends Serial[Problem] {
  def toXML(problem: Problem) = serialiser => serialiser -> { <object> <attribute name="networkNumber" value={problem.netBlock.networkNumber.toString}/>
                                                                       <attribute name="subnetMask" value={problem.netBlock.netMask.toString}/>
                                                                       <attribute name="numberOfSubnets" value={problem.numberOfSubnets.toString}/> </object> }

  def fromXML(elem: Elem) = deserialiser => deserialiser -> Problem(NetBlock(IPAddress.valueOf(((elem \ "attribute") filter (_.asInstanceOf[Elem] \ "@name" == "networkNumber")) \ "@value" toString).get,
                                                                             NetMask.valueOf((elem \ "attribute" filter (_ \ "@name" == "netMask")).first.toString).get),
                                                                    Integer.parseInt((elem \ "attribute" filter (_ \ "@name" == "numberOfSubnets")).first.toString))
  def construct = None
  def identifier = "ipsim.persistence.delegates.ProblemDelegate" } }
                                                                                
case class IPAddress(val rawValue: Int) {
 override def toString=(rawValue >>> 24) + "." + (rawValue >> 16 & 0xff) + '.' + (rawValue >> 8 & 0xff) + '.' + (rawValue & 0xff)
 def &(netMask: NetMask)=rawValue & netMask.rawValue
 def &(netMask: Int)=rawValue & netMask
 def ==(i: Int)=rawValue==i
 def isZero = rawValue==0 }

object IPAddress {
 def zero=IPAddress(0)
 def valueOf(ip: String) = { val p = ip split "\\." map { x => try { Integer.parseInt(x) } catch { case e => 260 } }
                             if (p.filter(_>255).length!=0 || p.length!=4) None
                             else Some(IPAddress(p(0)<<24 | p(1)<<16 | p(2)<<8 | p(3))) }
 def randomIP(random: Random)=IPAddress(random nextInt)
 def randomNetNum(prefixLength: Int)(implicit random: Random) = IPAddress(random.nextInt << (32-prefixLength))
 implicit def ipAddress2Int(ip: IPAddress)=ip.rawValue }

sealed trait NetMask {
 def rawValue: Int
 def prefixLength=(0 until 32 map (a => (a,NetMask fromPrefixLength a get)) find (this equals _._2)).get._1
 def asCustomString=if (prefixLength == -1) IPAddress(rawValue).toString else String.valueOf(prefixLength)
 override def toString=(rawValue >>> 24) + "." + (rawValue >> 16 & 0xff) + '.' + (rawValue >> 8 & 0xff) + '.' + (rawValue & 0xff)
 def isZero = rawValue == 0 }

object NetMask {
 private case class NetMaskImpl(override val rawValue: Int) extends NetMask
 def netMask(rv: Int): Option[NetMask] = if (isValid(rv)) Some(new NetMaskImpl(rv)) else None
 private def isValid(rawValue: Int) = !(Integer.toBinaryString(rawValue) match { case x => x.toList.zip(x.toList.tail) exists (_==('0','1')) } )
 def zero=netMask(0).get
 def fromPrefixLength(length: Int): Option[NetMask]=if (length==0) netMask(0) else if (length==32) netMask(0xFFFFFFFF) else
  if (length<0 || length>32) None
  else netMask(~(~0>>>length))
 def valueOf(text: String): Option[NetMask] = IPAddress.valueOf(text).flatMap(i => netMask(i.rawValue))
 def randomNetMask(implicit random: Random)=fromPrefixLength(random.nextInt(24) + 7).get
 implicit def netMask2Int(netMask: NetMask)=netMask.rawValue }

import NetMask.netMask2Int

case class NetBlock(val networkNumber: IPAddress, val netMask: NetMask) {
 if (networkNumber.rawValue != (networkNumber.rawValue & netMask.rawValue))
  throw new RuntimeException(networkNumber+"/"+netMask+" is a malformed netblock")

 override def toString=networkNumber+" netmask "+netMask
 def contains(ipAddress: IPAddress)=(ipAddress.rawValue & netMask.rawValue)==networkNumber.rawValue
 def asStringContainingSlash=networkNumber+"/"+netMask.prefixLength
 def broadcastAddress=IPAddress(~netMask|networkNumber)
 lazy val isZero = networkNumber.isZero
 def asCustomString = netMask.prefixLength match { case length => networkNumber+"/"+(if (length == -1) netMask else length) } }
 
import scala.xml._

import ipsim.persistence.Implicits._

object NetBlock {
 def zero=NetBlock(IPAddress(0),NetMask.netMask(0).get)
 def arbitraryNetBlock(implicit random: Random) = {
  val prefixLength=random.nextInt(8)+16
  NetBlock(Problem.generateNetworkNumber(random,prefixLength),NetMask.fromPrefixLength(prefixLength) get) }
 def valueOf(net: String): Option[NetBlock] = if (!net.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}/[0-9]{1,2}")) None else {
  val parts=net split "/"
  val netNum=IPAddress valueOf parts(0) get
  val tempMask=Integer parseInt parts(1)
  if (tempMask>32) None else {
   val netMask=NetMask.fromPrefixLength(tempMask).get
   if ((netNum & ~netMask)!=0) None else Some(NetBlock(netNum,netMask)) } }
 implicit val delegate: Serial[NetBlock] = new Serial[NetBlock] {
  def toXML(netBlock: NetBlock) = serialiser => serialiser -> <object> <attribute name="netmask" value={netBlock.netMask.toString}/> <attribute name="networkNumber" value={netBlock.networkNumber.toString}/> </object>
  def fromXML(elem: Elem) = _ -> { val netMask = NetMask.valueOf(elem /@ "netmask").get
                                   NetBlock(IPAddress(IPAddress.valueOf(elem /@ "networkNumber").get & netMask), netMask) }

  val construct = null
  val identifier = "ipsim.persistence.delegates.NetBlockDelegate" } }

case class SourceIPAddress(ipAddress: IPAddress) { def toDest=DestIPAddress(ipAddress)
                                                   override def toString=ipAddress.toString }

case class DestIPAddress(ipAddress: IPAddress) { def toSource=SourceIPAddress(ipAddress)
                                                 override def toString=ipAddress.toString }

case class MacAddress(rawValue: Int)

trait OutgoingPacketListener { def packetOutgoing(packet: Packet, source: PacketSource)(implicit network: Network): Unit }
trait IncomingPacketListener { def packetIncoming(packet: Packet, source: PacketSource, dest: PacketSource)(implicit network: Network): Unit }

sealed abstract class PacketSource(
 var positions: List[Either[Point,PacketSource]],
 var outgoingPacketListeners: List[OutgoingPacketListener],
 var incomingPacketListeners: List[IncomingPacketListener]) {
  def asComputer: Option[Computer] = None
  def asHub: Option[Hub] = None
  def asCard: Option[Card] = None
  def asCable: Option[Cable] = None
  def asCardDrivers = asCard flatMap (_.cardDrivers)
                                    
  def isSpecialCard = asCard exists (_.specialVar)
  def isCard = asCard.isDefined
  def isComputer = asComputer.isDefined
  def isHub = asHub.isDefined
  def isCable = asCable.isDefined

  def fold[T](card: Card => T, computer: Computer => T, cable: Cable => T, hub: Hub => T): T
  def children(all: Seq[PacketSource]) = all filter (_.positions exists (_.fold(x => false, this == _)))
  def children(implicit network: Network): Iterable[PacketSource] = children(network.all)
  def parents = positions flatMap (_.right.toOption)
  def serialise(serialiser: XMLSerialiser, name: String)(implicit network: Network): Unit }

object PacketSource { 
 def arbitraryPacketSource(implicit random: Random) = random.nextInt(4) match { case 0 => Cable arbitraryCable random
                                                                                case 1 => Card arbitraryCard random
                                                                                case 2 => Computer arbitraryComputer random
                                                                                case 3 => Hub arbitraryHub random } }

import ipsim.awt.Point.arbitraryPoint

case class Computer(position: Point) extends PacketSource(List(Left(position)),List(),List()) {
 var ipForwardingEnabled = false
 val arpTable = new ArpTable()
 val routingTable = new RoutingTable
 var computerID = 0
 var isISP = false

 override def asComputer = Some(this)
 def fold[T](card: Card => T, computer: Computer => T, cable: Cable => T, hub: Hub => T) = computer(this)
 def cardsFor(route: Route)(implicit network: Network) = cardsWithDrivers filter {
  _.netBlock match { case netBlock => !netBlock.networkNumber.isZero && netBlock.contains(route.gateway) } }

 def cards(implicit network: Network) = children(network.all) map (_.asInstanceOf[Card])
 def cardsWithDrivers(implicit network: Network) = cards flatMap (_.cardDrivers)

 def withID(i: Int) = { computerID = i
                        this }

 def isLocallyReachable(ip: IPAddress)(implicit network: Network) = cardsWithDrivers exists (_.netBlock contains ip)
 def routeFor(ip: IPAddress)(implicit network: Network): Option[Route] =
  cardsWithDrivers.find(card =>
   !card.ipAddress.isZero && card.netBlock.contains(ip)).map(_.toRoute) orElse routingTable.routes.find(_.netBlock contains ip)
 def firstAvailableEthNumber(implicit network: Network) = Stream from 0 find (a => !sortedCards.exists(a==_.ethNumber)) get
 def sortedCards(implicit network: Network) = children(network.all).flatMap(_.asCard).flatMap(_.withDrivers).toList.sort(_.ethNumber > _.ethNumber)
 def ipAddresses(implicit network: Network) = cardsWithDrivers map (_.ipAddress)
 def isRouter(implicit network: Network) = ipAddresses.size>1 }

import scala.xml._

object Computer {
 object delegate extends Serial[Computer] {
  def toXML(computer: Computer) = originalSerialiser => {
   var serialiser = originalSerialiser
   <object>
    <attribute name="ipForwardingEnabled" value={computer.ipForwardingEnabled.toString}/>
    <attribute name="computerId" value={computer.computerID.toString}/>
    <attribute name="isISP" value={computer.isISP.toString}/>
    { DelegateHelper.positionsToXML(computer)(originalSerialiser) match { case (s, nodes) => {
     serialiser = s
     nodes } } }
    { serialiser.toXML(computer.routingTable, "routingTable") match { case (s, nodes) => {
     serialiser = s
     nodes } } }
   </object> match { case elem => serialiser -> elem }
  }

  def fromXML(elem: Elem) = Computer(
  def readXML(deserialiser: XMLDeserialiser, elem: Elem, andOthers: Option[Stream[PacketSource]]) = {
   val computer = andOthers.get.head.asComputer.get
   computer.ipForwardingEnabled = deserialiser.readAttribute(elem, "ipForwardingEnabled").get.toBoolean
   computer.computerID = deserialiser.readAttribute(elem, "computerID") match { case Some(s) => s.toInt
                                                                                case None => network.generateComputerID }
   computer.isISP = deserialiser.readAttribute(elem, "isISP") map (_.toBoolean) getOrElse false
   val ret = ipsim.lang.RichStream(andOthers.get).merge(DelegateHelper.readPositions(deserialiser, elem, computer))
   for (route <- deserialiser.readObject(elem, "routingTable", RoutingTable.delegate).get.routes)
    computer.routingTable.add(None, route)
   ret.toStream }
  def construct = Some(Stream(Computer(Point.origin)))
  val identifier = "ipsim.persistence.delegates.ComputerDelegate" }

 def testLoadingGivesRightNumberOfComponents = {
  val network = new Network().loadFromFile(new File("datafiles/unconnected/hubdisabled.ipsim"))
  if (true) throw new RuntimeException("" + network.all.size) 
  network.all.size==7 && network.contexts.stream.head.visibleComponentsVar.size==7 }

 import ipsim.qc.QuickCheck.check
 def testLoadingRestoresBigPipeIcon()(implicit random: Random) = check(Network.arbitraryNetwork(_), { network: Network => 
  def numISPs(network: Network) = (network.all flatMap(_.asComputer) filter (_.isISP)).toList.size
  numISPs(network)==numISPs(network.loadFromString(network.saveToString))
} )
 def arbitraryComputer(implicit random: Random) = Computer(arbitraryPoint) }

case class Hub(position: Point) extends PacketSource(List(Left(position)), Nil, Nil) {
 var power = false
 def otherEnds(all: Seq[PacketSource]) = children(all) map (_.asCable.get) flatMap (_ otherEnd this)
 def serialise(serialiser: XMLSerialiser, name: String)(implicit network: Network) = serialiser.writeObject(this, name, Hub.delegate)
 override def asHub = Some(this)
 def fold[T](card: Card => T, computer: Computer => T, cable: Cable => T, hub: Hub => T) = hub(this) }

case class Card(_special: Boolean, _positionOrParent: Either[Point,PacketSource]) extends PacketSource(List(_positionOrParent), Nil, Nil) {
 var cardDrivers: Option[CardDrivers] = None
 override def asCard = Some(this)
 def installDeviceDrivers(implicit network: Network): CardDrivers = {
  val computer = positions.head.right.get.asInstanceOf[Computer]
  val ethNumber = computer.firstAvailableEthNumber
  val ret=CardDrivers(IPAddress.zero, NetMask.zero, ethNumber)
  cardDrivers=Some(ret)
  ret }
 def uninstallDeviceDrivers = cardDrivers filter (!_.ipAddress.isZero) foreach (drivers => cardDrivers = None) 
 def withDrivers = cardDrivers
 def serialise(serialiser: XMLSerialiser, name: String)(implicit network: Network) = serialiser.writeObject(this, name, Card.delegate)
 def cable(implicit network: Network) = Positions.children(network.all, this).toOption
 def fold[T](card: Card => T, computer: Computer => T, cable: Cable => T, hub: Hub => T) = card(this)
 var specialVar = _special }

object Card {
 def arbitraryCard(implicit random: Random) = Card(random.nextInt(2)==0, Left(arbitraryPoint))
 def delegate(implicit network: Network) = new ReadDelegate[Stream[PacketSource]] with WriteDelegate[Card] {
  def writeXML(serialiser: XMLSerialiser, card: Card) = {
   card.withDrivers match {
    case Some(drivers) => {
     serialiser.writeAttribute("ethNumber", drivers.ethNumber.toString)
     serialiser.writeAttribute("ipAddress", drivers.ipAddress.toString)
     serialiser.writeAttribute("netMask", drivers.netMask.toString) } case None => () }
   DelegateHelper.writePositions(serialiser, card) then serialiser }
  def readXML(deserialiser: XMLDeserialiser, elem: Elem, andOthers: Option[Stream[PacketSource]]): Stream[PacketSource] = {
   val card=andOthers.get.head.asCard.get
   val ret = RichStream(andOthers.get).merge(DelegateHelper.readPositions(deserialiser, elem, card))
   deserialiser.readAttribute(elem, "ethNumber").map(_.toInt) match {
    case Some(ethNumber) => if (ethNumber != -1) card.installDeviceDrivers.ethNumber = ethNumber
    case None => () }
   card.withDrivers match {
    case Some(withDrivers) => {
     withDrivers.ipAddress = IPAddress.valueOf(deserialiser.readAttribute(elem, "ipAddress").get).get
     var binaryMask = Integer.toBinaryString(IPAddress.valueOf(deserialiser.readAttribute(elem, "netMask").get).get.rawValue)
     while (binaryMask contains "01") binaryMask=binaryMask.replaceAll("01", "00")
     withDrivers.netMask = NetMask.netMask(java.lang.Long.parseLong(binaryMask, 2).toInt).get }
    case None => () }
    ret.toStream }
  def construct = Some(Stream(Card(false, Left(Point.origin))))
  val identifier = "ipsim.persistence.delegates.EthernetCardDelegate" } }
                                                          
case class CardDrivers(var ipAddress: IPAddress, var netMask: NetMask, var ethNumber: Int) { def netBlock = NetBlock(IPAddress(ipAddress & netMask), netMask)
                                                                                             def toRoute = Route(netBlock, ipAddress) }

case class Cable(from: Either[Point, PacketSource], to: Either[Point, PacketSource]) extends PacketSource(List(from, to), Nil, Nil) {
 var cableType: CableType = straightThrough
 def otherEnd(p: PacketSource) = (parents - p) firstOption
 override def asCable = Some(this)
 def canTransferPackets = if (parents.size == 2) cableType.canTransferPackets(parents(0), parents(1)) else false
 def fold[T](card: Card => T, computer: Computer => T, cable: Cable => T, hub: Hub => T) = cable(this) }

object Cable {
 def arbitraryCable(implicit random: Random) = Cable(Left(arbitraryPoint), Left(arbitraryPoint))
 def delegate(implicit network: Network) = new ReadDelegate[Stream[PacketSource]] with WriteDelegate[Cable] {
  def writeXML(serialiser: XMLSerialiser, cable: Cable) = { DelegateHelper.writePositions(serialiser, cable)
                                                            serialiser.writeAttribute("cableType", cable.cableType.toString) }
  def readXML(deserialiser: XMLDeserialiser, elem: Elem, andOthers: Option[Stream[PacketSource]]) = {
   val cable = andOthers.get.head.asCable.get
   val ret = RichStream(andOthers.get).merge(DelegateHelper.readPositions(deserialiser, elem, cable))
   cable.cableType = deserialiser.readAttribute(elem, "cableType") match { case None => straightThrough
                                                                           case Some(t) => if (t == crossover.toString) crossover else
                                                                            if (t == straightThrough.toString) straightThrough else
                                                                             if (t == broken.toString) broken else throw null }
   ret.toStream }
  def construct = Some(Stream(Cable(Left(Point origin), Left(Point origin))))
  val identifier = "ipsim.persistence.delegates.EthernetCableDelegate" } }

sealed abstract class CableType { def canTransferPackets(oneEnd: PacketSource, otherEnd: PacketSource): Boolean }

case object straightThrough extends CableType { def canTransferPackets(oneEnd: PacketSource, otherEnd: PacketSource) =
 oneEnd.isSpecialCard || otherEnd.isSpecialCard || oneEnd.isCard && otherEnd.isHub || oneEnd.isHub && otherEnd.isCard }

case object crossover extends CableType { def canTransferPackets(oneEnd: PacketSource, otherEnd: PacketSource) =
 oneEnd.isSpecialCard || otherEnd.isSpecialCard || oneEnd.isHub && otherEnd.isHub || oneEnd.isCard && otherEnd.isCard }

case object broken extends CableType { def canTransferPackets(oneEnd: PacketSource, otherEnd: PacketSource) = false }

object Hub {
 def arbitraryHub(implicit random: Random) = Hub(arbitraryPoint)
 def delegate(implicit network: Network) = new ReadDelegate[Stream[PacketSource]] with WriteDelegate[Hub] {
  def writeXML(serialiser: XMLSerialiser, hub: Hub) = { serialiser.writeAttribute("isPowerOn", String.valueOf(hub.power))
                                                        DelegateHelper.writePositions(serialiser, hub) then serialiser }
  def readXML(deserialiser: XMLDeserialiser, elem: Elem, hubAndOthers: Option[Stream[PacketSource]]) = {
   val hub = hubAndOthers.get.head.asHub.get
   deserialiser.readAttribute(elem, "isPowerOn") foreach (s => hub.power = s.toBoolean)
   val otherStream=DelegateHelper.readPositions(deserialiser, elem, hub).toStream
   RichStream(hubAndOthers.get).merge(otherStream).toStream }
  def construct = Some(Stream(Hub(Point(200, 200))))
  val identifier = "ipsim.persistence.delegates.HubDelegate" } }

sealed abstract class Packet { def asIPPacket: Option[IPPacket] = None
                               def asArpPacket: Option[ArpPacket] = None
                               def asEthernetPacket: Option[EthernetPacket] = None }

case class IPPacket(val sourceIP: SourceIPAddress, val destIP: DestIPAddress, val timeToLive: Int, val identifier: Object, val data: Object)
 extends Packet { override def asIPPacket = Some(this) }

case class ArpPacket(val sourceIP: IPAddress, val sourceMac: MacAddress, val destIP: IPAddress, val destMac: MacAddress, val id: Object) extends Packet {
 override def asArpPacket = Some(this) }

case class EthernetPacket(val source: MacAddress, val dest: MacAddress, val data: Packet) extends Packet {
 override def asEthernetPacket = Some(this) }

case class ArpEntry(macAddress: Option[MacAddress], ttl: Int) { var dead = false }

class ArpTable {
 val map=new mutable.HashMap[IPAddress, ArpEntry]
 def put(ipAddress: IPAddress, macAddress: MacAddress)(implicit network: Network) = map.put(ipAddress, ArpEntry(Some(macAddress), network.arpCacheTimeout))
 def macAddress(gatewayIP: IPAddress) = map.get(gatewayIP) match { case Some(entry) => entry.macAddress
                                                                   case None => None }
 override def toString = map.toString
 def clear = map.clear
 def putIncomplete(from: IPAddress, network: Network) = map.put(from, ArpEntry(None, network.arpCacheTimeout))
 def hasEntryFor(ip: IPAddress) = map.contains(ip) & !map.get(ip).get.dead }

import ipsim.lang.Unique

object Debug {
 def toXML(node: Node) = {
  import java.io._
  import org.w3c.dom._
  import javax.xml._
  import javax.xml.transform._
  import javax.xml.transform.dom._
  import javax.xml.transform.stream._
  val writer = new StringWriter
  TransformerFactory.newInstance().newTransformer().transform(new DOMSource(node), new StreamResult(writer))
  writer.toString
 }
}

class Network {
 implicit val _ = this
 var modified = false
 import ipsim.gui.{UserPermissions, Freeform}
 var userPermissions: UserPermissions = Freeform
 var zoomLevel: Double = 0
 def zoomLevel_(newValue: Double) = if (newValue<0.01) throw null else zoomLevel=newValue
 var emailAddress: Option[String] = None
 var log: List[String] = Nil
 def logAsString = log.foldLeft(new StringBuilder)(_ append _ append '\n') toString

 import connectivity.PacketQueue
 val packetQueue = new PacketQueue
 val macAddresses: List[PacketSource] = Nil
 var arpCacheTimeout = 20
 val contexts: StreamProperty[NetworkContext] = StreamProperty(Stream.empty)
 def deleteCard(card: Card) = for (context <- contexts) context.visibleComponentsVar = context.visibleComponentsVar filter (_ != card)
 //does deleteCard delete from the ISP too?

 def deleteFrom(v: StreamProperty[PacketSource], item: PacketSource) = v filter (_!=item)
 def delete(source: PacketSource): Unit = { Positions.children(all,source) foreach delete
                                            contexts foreach (x => x.visibleComponentsVar = x.visibleComponentsVar filter (_!=source)) }
 var nextComputerID = 200
 def generateComputerID = { nextComputerID += 1
                            nextComputerID - 1 }
 val ispContext = new NetworkContext
 var testName: Option[String] = None
 def all = { val result = mutable.HashSet[PacketSource]()
             for (c <- Stream.cons(ispContext, contexts.stream)) { for (d <- c.visibleComponentsVar) result+=d }
             result.toList }
 def cards = all flatMap (_.asCard)
 def cardDrivers(computer: Computer) = Positions.children(all, computer) flatMap (_.asCard) flatMap (_.cardDrivers)
 def cardsWithDrivers = all flatMap (_.asCard) flatMap (_.cardDrivers)
 def computersByIP(ipAddress: IPAddress) = all flatMap (_.asComputer) filter (_.cards flatMap (_.cardDrivers) exists (_.ipAddress == ipAddress))
 def hubs = all flatMap (_ asHub)
 def problems = List(ispContext) ++ contexts.stream flatMap (_.problem)
 def numberOfSubnets = all flatMap (_.asComputer) flatMap (_.cards) flatMap (_.cardDrivers) map (it => it.netMask & it.ipAddress) filter Unique.unique size

 import ipsim.gui.UserMessages
 def loadFromFile(file: File): Network = IOUtility.readWholeResource(file.toURI.toURL.toString) match {
  case Some(s) => loadFromString(s)
  case None => { UserMessages.message("Could not load the requested file")
                 this } }

 def loadFromString(xmlString: String) = { clearAll
                                           nextComputerID = 200
                                           XMLDeserialiser.fromString(xmlString)
                                           this }

 import java.io.{StringWriter, Writer}
 def saveToString = saveToWriter(new StringWriter).toString
 def saveToWriter(writer: Writer) = Network.saveToWriter(writer, this)
 def saveToFile(filename: File) =
  if (filename.getName startsWith "@") {
   val stringWriter = new StringWriter
   val tempModified = modified
   saveToWriter(stringWriter)
   modified = tempModified
   val output = ipsim.web.Web.putNamedConfiguration(filename.getName, stringWriter.toString)
   if (!output.startsWith("102")) throw error(output) } else
    bracketEithers(fileWriter(filename))(_.close)(fileWriter => bracketEithers(bufferedWriter(fileWriter))(_.close)(writer => Right(saveToWriter(writer))))
                                         
 def clearAll = { for (context <- Stream.cons(ispContext,contexts.stream)) context.visibleComponentsVar = Nil
                  nextComputerID = 200 }

 def depthFirstIterable: Iterable[PacketSource] = Trees.depthFirstIterable(Trees.nodify(topLevelComponents))

 def depthFirstIterable(context: NetworkContext): Iterable[PacketSource] = { var set = new scala.collection.mutable.HashSet[PacketSource]
                                                                             set ++= Trees.depthFirstIterable(Trees.nodify(context.topLevelComponents))
                                                                             set }

 def topLevelComponents = all filter (_.positions exists (_.isRight)) }

object logDelegate extends ReadDelegate[List[String]] with WriteDelegate[List[String]] {
 def writeXML(serialiser: XMLSerialiser, log: List[String]) =
  (for ((entry, a) <- log.zipWithIndex) serialiser.writeObject(entry, "entry "+a, defaultCommandDelegate)) then serialiser
 def readXML(deserialiser: XMLDeserialiser, elem: Elem, ignored: Option[List[String]]) =
  deserialiser.getObjectNames(elem) filter (_ startsWith "entry ") map (name => deserialiser.readObject(elem, name, defaultCommandDelegate).get) toList
 val construct = None
 val identifier = "ipsim.persistence.delegates.LogDelegate" }

object defaultCommandDelegate extends ReadDelegate[String] with WriteDelegate[String] {
 def writeXML(serialiser: XMLSerialiser, s: String) = serialiser.writeAttribute("value", s)
 def readXML(deserialiser: XMLDeserialiser, elem: Elem, ignored: Option[String]) =
  deserialiser.readAttribute(elem, "description").getOrElse(deserialiser.readAttribute(elem, "value").get)
 val construct = None
 val identifier = "ipsim.persistence.delegates.DefaultCommandDelegate" }

object Network {
 object serial extends Serial[Network] {
  def writeXML(serialiser: XMLSerialiser, ignored: Network) = {
   for ((context, a) <- contexts.stream.zip(Stream from 0)) serialiser.writeObject(context, "context "+a, NetworkContext.delegate)
   serialiser.writeObject(ispContext, "ispContext", NetworkContext.delegate)
   serialiser.writeObject(log, "log", logDelegate) }
  def readXML(deserialiser: XMLDeserialiser, elem: Elem, ignored: Option[Network]) = {
   contexts.clear
   //single-network files generated by old versions will have a 'problem' here.  Newer versions have it in the NetworkContext.
   //so if we find an old one, we need to create a NetworkContext for it and set the problem accordingly.  If a file has no problem and no
   //NetworkContexts, create a new NetworkContext.
   val problem = deserialiser.readObject(elem, "problem", Problem.delegate)
   problem match { case None => { println("No top-level problem found")
                                  var newFile = false
                                  for (name <- new immutable.TreeSet[String]++deserialiser.getObjectNames(elem); if name startsWith "context ") {
                                   newFile = true
                                   contexts.append(deserialiser.readObject(elem, name, NetworkContext.delegate).get) }
                                  if (contexts.isEmpty) { if (newFile) throw null
                                                          contexts.append(new NetworkContext) } }
                   case Some(problem) => { println("A top-level problem found")
                                           val networkContext = new NetworkContext
                                           networkContext.problem = Some(problem)
                                           contexts.append(networkContext) } }
   deserialiser.readObject(elem, "ispContext", NetworkContext.delegate).foreach(x => ispContext.visibleComponentsVar = x.visibleComponentsVar)

   println("objectnames: "+deserialiser.getObjectNames(elem).toList)

   for (name <- deserialiser.getObjectNames(elem); if name.startsWith("child ")) {
    println("Found "+name)
    val component = DelegateHelper.readFromDeserialiser(deserialiser, elem, name)
    if (contexts.size==1) {
     var components=contexts.stream(0).visibleComponentsVar
     components = components.prependIfNotPresent(component.head)
     contexts.stream(0).visibleComponentsVar = contexts.stream(0).visibleComponentsVar merge component.toList } else throw null }
   deserialiser.readObject(elem, "log", logDelegate).foreach(log = _)
   modified = false
   Network.this }
  def construct = Some(Network.this)
  def identifier = "ipsim.persistence.delegates.NetworkDelegate" }

 def idFactory[T] = new Function1[T, Int] { val ids: mutable.Map[Int, T] = new mutable.HashMap
                                            def apply(t: T): Int = ids.find(_._2==t) match { case Some(e) => e._1
                                                                                             case None => {
                                                                                              def identifier: Int = { for (i <- Stream from 0
                                                                                                                           if !ids.contains(i)) {
                                                                                                                            ids.put(i, t)
                                                                                                                            return i }
                                                                                                                      throw null }
                                                                                              identifier } } }                                            
 def arbitraryNetwork(implicit random: Random): Stream[Network] = {
  val network = new Network
  0 until random.nextInt(10) foreach (ignored => network.contexts.prepend(NetworkContext.arbitraryNetworkContext))
  Stream.cons(network,arbitraryNetwork) }

 import java.io.Writer
 import Network.serial
 def saveToWriter(writer: Writer, network: Network) =
  XML.write(writer, XMLSerialiser().toXML(network, "network")(serial)._2, "UTF-8", true, null)
}

class NetworkContext {
 var visibleComponentsVar: List[PacketSource] = Nil
 var problem: Option[Problem] = None
 def askUserForNumberOfFaults(frame: JFrame) = Stream from 1 flatMap (ignored => {
  val result=JOptionPane.showInputDialog(frame, "How many faults? (1 to 5)").toInt
  if (result>=1 && result<=5) Some(result) else None } ) head
 def topLevelComponents: Iterable[PacketSource] = visibleComponentsVar.filter(_.positions.forall(_.isLeft)) }

object NetworkContext {
 def arbitraryNetworkContext(implicit random: Random): NetworkContext = {
  val context=new NetworkContext
  if (random.nextInt(2) == 0)
   context.problem = Some(Problem.arbitraryProblem)
  val numComponents = random nextInt 3
  context.visibleComponentsVar = (0 until numComponents map (ignored => PacketSource.arbitraryPacketSource)).toList
  context }
 def delegate(implicit network: Network) = new ReadDelegate[NetworkContext] with WriteDelegate[NetworkContext] {
  def writeXML(serialiser: XMLSerialiser, context: NetworkContext) = {
   context.problem match { case Some(problem) => serialiser.writeObject(problem, "problem", Problem.delegate)
                           case _ => () }
   (for ((component, a) <- context.visibleComponentsVar.zipWithIndex)
    DelegateHelper.writePacketSource(serialiser, component, "visibleComponent "+a)) then serialiser }
  def readXML(deserialiser: XMLDeserialiser, elem: Elem, andOthers: Option[NetworkContext]) = {
   andOthers.get.problem = deserialiser.readObject(elem, "problem", Problem.delegate)
   for (name <- deserialiser.getObjectNames(elem); if name startsWith "visibleComponent ")
    andOthers.get.visibleComponentsVar = andOthers.get.visibleComponentsVar prependIfNotPresent DelegateHelper.readFromDeserialiser(deserialiser, elem, name).head
   andOthers.get }
  def construct = Some(new NetworkContext)
  def identifier = "NetworkContext" }
 def errors(s: String)(implicit frame: JFrame) = JOptionPane.showMessageDialog(frame, s, "Error", JOptionPane.ERROR_MESSAGE) }

object Positions {
 def testSetPosition = { implicit val network=new Network
                         val cable = Cable(Left(Point(50, 50)), Left(Point(100, 100)))
                         Math.abs(position(cable, 0).x-50)<0.005 }
 def centreOf(component: PacketSource)(implicit network: Network) = {
  val results=new mutable.HashMap[Int, Point]
  for ((pos,a) <- component.positions.zipWithIndex)
   results += (a -> position(component, a))
  var totalX = 0
  var totalY = 0
  for (point <- results.values) { totalX += point.x.toInt
                                  totalY += point.y.toInt }
  Point(totalX*1.0/results.size, totalY*1.0/results.size) }
 def translateAllWhenNecessary(visibleRect: Rectangle)(implicit network: Network) =
  if (visibleRect.x<0 || visibleRect.y<0) translateAll(-Math.min(visibleRect.x, 0), -Math.min(visibleRect.y, 0))
 def translateAll(x: Int, y: Int)(implicit network: Network) =
  for (p <- network.all; a <- 0 until p.positions.length; if p.positions(a).isLeft)
   p.positions = p.positions.map(x => if (x==p.positions(a)) Left(position(p, a)) else x) ++ List(Left(Point(x, y)))
 def children(all: Iterable[PacketSource], component: PacketSource) = all filter (_.positions exists (_.fold(x => false, component == _)))
 def position(component: PacketSource, index: Int)(implicit network: Network): Point = position(component.positions(index), component)
 def position(positionOrParent: Either[Point, PacketSource], component: PacketSource)(implicit network: Network) =
  positionOrParent.fold(pos => pos, (parent => GetChildOffset.getChildOffset(parent, component) + parent.positions(0).left.get))
 def positions(component: PacketSource)(implicit network: Network) = component.positions map (p => position(p, component)) }

import scala.collection.mutable.ListBuffer
                                                                  
object DelegateHelper {
 def positionsToXML(component: PacketSource): XMLSerialiser => (XMLSerialiser, Seq[Node]) = originalSerialiser => {
  var serialiser = originalSerialiser
  var nodes = new ListBuffer[Node]
  for ((packetSource, a) <- component.children.zipWithIndex)
   serialiser.toXML(packetSource, "child "+a) match { case (s, node) => {
    serialiser = s
    nodes += node
   } }
  serialiser -> nodes.toList
 }

 def writePositions(serialiser: XMLSerialiser, component: PacketSource)(implicit network: Network) = {
  for ((packetSource, a) <- Positions.children(network.all, component).toStream.zip(Stream from 0))
   writePacketSource(serialiser, packetSource, "child "+a)
  for ((pos, b) <- component.positions.toStream.zip(Stream from 0))
   pos match { case Left(position) => serialiser.writeObject(position, "point "+b, Point.delegate)
               case Right(parent) => writePacketSource(serialiser, parent, "parent "+b) } }
 def readPositions(deserialiser: XMLDeserialiser, elem: Elem, packetSource: PacketSource)(implicit network: Network): Stream[PacketSource] = {
  var andOthers: Stream[PacketSource] = Stream.empty
  for (nodeName <- deserialiser.getObjectNames(elem); if nodeName startsWith "parent ") {
   val a=Integer.parseInt(nodeName.substring("parent ".length))
   val parent = readFromDeserialiser(deserialiser, elem, nodeName)
   andOthers=RichStream(andOthers).merge(parent).toStream
   packetSource.positions=packetSource.positions.set(a, Right(parent.head))
   if (nodeName startsWith "point ") {
    val string = nodeName.substring("point ".length)
    val a = Integer.parseInt(string)
    packetSource.positions = packetSource.positions.set(a, Left(deserialiser.readObject(elem, nodeName, Point.delegate).get)) }
   if (nodeName startsWith "child ")
    andOthers = andOthers merge readFromDeserialiser(deserialiser, elem, nodeName) toStream }
  andOthers }
 def readFromDeserialiser(deserialiser: XMLDeserialiser, elem: Elem, name: String)(implicit network: Network): Stream[PacketSource] = {
  val ident=deserialiser.typeOfChild(elem, name)
  (if (Hub.delegate.identifier == ident) deserialiser.readObject(elem, name, Hub.delegate) else
   if (Computer.delegate.identifier == ident) deserialiser.readObject(elem, name, Computer.delegate) else
    if (Cable.delegate.identifier == ident) deserialiser.readObject(elem, name, Cable.delegate) else
     if (Card.delegate.identifier == ident) deserialiser.readObject(elem, name, Card.delegate) else throw null).get }

 def writePacketSource(serialiser: XMLSerialiser, packetSource: PacketSource, name: String)(implicit network: Network) = 
  packetSource.serialise(serialiser, name) }

private object Trees {
 def depthFirstIterable[T](roots: Iterable[TreeNode[T]]) = DepthFirstIterable(roots)
 case class NetworkNode(component: PacketSource, network: Network) extends TreeNode[PacketSource] {
  implicit val boilerplate = network
  override def value = component
  override def toString = "NetworkNode["+component+']'
  override def childNodes = nodify(component.children(network.all)) }
 def nodify[T <: PacketSource](components: Iterable[T])(implicit network: Network) = components.map(t => NetworkNode(t, network)) }

private case class DepthFirstIterable[T](roots: Iterable[TreeNode[T]]) extends Iterable[T] {
 def elements = new DepthFirstIterator(roots) }

private class DepthFirstIterator[T](root: Iterable[TreeNode[T]]) extends Iterator[T] {
 val stack = new mutable.Stack[TreeNode[T]]
 stack ++= root
 def hasNext = !stack.isEmpty
 def next = { if (stack.isEmpty) throw new NoSuchElementException
              val node = stack.pop
              stack ++= node.childNodes
              node.value }
 def remove = throw null }

private trait TreeNode[T] { def childNodes: Iterable[TreeNode[T]]
                            def value: T }

import ipsim.swing.Dialogs.createDialogWithEscapeKeyToClose
import javax.swing.JTextArea
import javax.swing.JScrollPane
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

trait PacketSniffer extends IncomingPacketListener with OutgoingPacketListener

object PacketSniffer { def packetSniffer(toBeSniffed: PacketSource)(implicit frame: JFrame, network: Network) = {
 val dialog = createDialogWithEscapeKeyToClose("Packet Sniffer on "+toBeSniffed)
 val output = new JTextArea(5, 80)
 dialog add new JScrollPane(output)
 dialog.pack
 dialog setVisible true
 val sniffer = new PacketSniffer {
  def packetIncoming(packet: Packet, source: PacketSource, dest: PacketSource)(implicit network: Network) = append("Incoming "+packet+" to "+dest)
  private def append(string: String) = output.setText(output.getText + '\n' + string)
  def packetOutgoing(packet: Packet, source: PacketSource)(implicit network: Network) = append("Outgoing "+packet+" from "+source) }
 dialog addWindowListener new WindowAdapter {
  override def windowClosing(e: WindowEvent) = { network.log ++= List("Removed a packet sniffer from " + toBeSniffed+'.')
                                                 toBeSniffed.incomingPacketListeners -= sniffer
                                                 toBeSniffed.outgoingPacketListeners -= sniffer } }
 sniffer } }

object ComputerTest { def testCardIndexRetention = {
 implicit val network = new Network
 val context = network.contexts append new NetworkContext

 val computer = Computer(Point.origin) withID network.generateComputerID
 context.visibleComponentsVar ::= computer

 val card = Card(false, Right(computer))
 context.visibleComponentsVar ::= card

 val cardDrivers = card.installDeviceDrivers

 val dummyCard = Card(false, Right(computer))
 context.visibleComponentsVar ::= dummyCard
 dummyCard.installDeviceDrivers

 cardDrivers.ethNumber == 0 } }

object PositionTest {
 def testRetention = () => { val network = new Network
                             val context = network.contexts append new NetworkContext
                             val computer = Computer(Point origin) withID network.generateComputerID
                             context.visibleComponentsVar ::= computer
                             
                             val card = Card(false, Right(computer))
                             context.visibleComponentsVar ::= card
                             card.positions(0) == Right(computer) }
 def testRetention2 = () => { val network = new Network
                              val context = network.contexts append new NetworkContext
                              
                              val cable = Cable(Left(Point origin), Left(Point(50, 0)))
                              context.visibleComponentsVar ::= cable

                              val card1 = Card(false, Left(Point origin))
                              context.visibleComponentsVar ::= card1

                              val card2 = Card(false, Left(Point origin))
                              context.visibleComponentsVar ::= card2

                              card1.positions = card1.positions set (0, Left(Point(200, 200)))
                              val point = card1.positions(0)

                              card2.positions = card2.positions set (0, Left(Point(300, 300)))
                              cable.positions = cable.positions set (0, Right(card1))
                              cable.positions = cable.positions set (1, Right(card2))
                      
                              cable.positions(0) == Right(card1) && cable.positions(1) == Right(card2) }
 def setParentTwice = () => { implicit val network = new Network
                              val context = network.contexts prepend new NetworkContext
                              val point = Point(5, 5)
                             
                              val card = Card(false, Left(point))
                              context.visibleComponentsVar ::= card
                             
                              val hub = Hub(Point(10, 10))
                              context.visibleComponentsVar ::= hub

                              val cable = Cable(Left(Point(20, 20)), Left(Point(40, 40)))
                              context.visibleComponentsVar ::= cable

                              cable.positions = cable.positions set (0, Right(card))
                              cable.positions = cable.positions set (0, Right(hub))
                              cable.positions(0) == Right(hub) }

 def cableWithTwoEnds = () => { implicit val network = new Network
                                val context = network.contexts prepend new NetworkContext
                                
                                val computer1 = Computer(Point(50, 50))
                                context.visibleComponentsVar ::= computer1

                                val computer2 = Computer(Point(100, 100))
                                context.visibleComponentsVar ::= computer2

                                val card1 = Card(false, Right(computer1))
                                context.visibleComponentsVar ::= card1

                                val card2 = Card(false, Right(computer2))
                                context.visibleComponentsVar ::= card2
                               
                                val cable = Cable(Right(card1), Right(card2))
                                context.visibleComponentsVar ::= cable
                         
                                cable.positions(0) == Right(card1) && cable.positions(1) == Right(card2) } }
 
import NetMask.netMask

object ProblemTest {
 def testInvalidNetMaskRejection = () => List("255.255.22.0", "255.0.0.0") map (NetMask valueOf _) match { case List(None, Some(t)) => true
                                                                                                           case _ => false }
 def test1 = () => { val address = IPAddress(221 << 24)
                     val mask = netMask(255 << 24).get
                     val netBlock = NetBlock(address, mask)
                     netBlock.networkNumber == IPAddress.valueOf("221.0.0.0").get } }

object BugzillaBug18 {
 def test =
  () => (List("192.168.1.0/24", "192.168.0.0/17", "192.168.0.0/17", "192.168.0.0/15", "192.128.0.0/10", "192.0.0.0/8") flatMap (NetBlock valueOf _)
         forall (block => !Problem.isReservedNetworkNumber(block))) && !Problem.isReservedNetworkNumber(NetBlock.valueOf("192.168.0.0/11").get) }

object CardDriversTest { def apply = { implicit val network = new Network
                                       val context = network.contexts prepend new NetworkContext
                                       
                                       val computer = Computer(Point(300, 300))
                                       context.visibleComponentsVar ::= computer
                                       
                                       val card = Card(false, Right(computer))
                                       context.visibleComponentsVar ::= card

                                       card.installDeviceDrivers

                                       val saved = network.saveToString
                                       network loadFromString saved

                                       !network.cardsWithDrivers.isEmpty } }
                                       
import ipsim.network.connectivity.PingTester

object InfiniteLoopBug { def apply = { implicit val network = new Network
                                       network loadFromFile new File("datafiles/fullyconnected/101.ipsim")
                                       PingTester.testPing(IPAddress.valueOf("146.87.1.1").get, IPAddress.valueOf("146.87.1.255").get)
                                       true } }
                                       
