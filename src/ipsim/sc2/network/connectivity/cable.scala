package ipsim.network.connectivity.cable

import ipsim.network.{Card, Network, Hub, Positions}

import ipsim.awt.Point
import ipsim.Caster.asNotNull
import ipsim.lang.Assertion.assertTrue
import ipsim.lang.Maths.approxEqual

import ipsim.lang.Implicits._

object CableTest {
 def testCable = () => {
  val network=new Network()
  val context=network.contexts.append(new NetworkContext)

  val card = Card(false, Left(Point(0, 0)))
  context.visibleComponentsVar ::= card

  val hub = Hub(Point(100, 100))
  context.visibleComponentsVar ::= hub

  val cable=Cable(Right(hub), Right(card))
  context.visibleComponentsVar ::= cable

  val card2 = Card(false, Left(Point(200, 200)))
  context.visibleComponentsVar ::= card2

  val either1=cable.canTransferPackets
  cable.positions = cable.positions.set(0, Right(card2))

  either1 && !cable.canTransferPackets }

 def testCableWithNoParents = () => {
  implicit val network = new Network
  val cable=Cable(Left(Point(5, 5)), Left(Point(10, 10)))

  var a=0
  while (a<cable.positions.size) { assertTrue(Array(approxEqual(Positions.position(cable,a).x,5+a*5)))
                                   a=a+1 }
  true } }
