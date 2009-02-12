package ipsim.tests

import ipsim.lang.Implicits._
import com.rickyclarkson.testsuite.UnitTestUtility.runTests
import ipsim.network.{Positions, ProblemTest, Computer}
import ipsim.network.connectivity.cable.CableTest
import ipsim.network.Hub
import ipsim.util.Collections
import ipsim.network.PositionTest
import ipsim.network.connectivity.{ComputerArpIncomingTest, ComputerArpOutgoingTest}
import ipsim.network.route.RoutingTableTests.{testRetention,testGetBroadcastRoute}
import ipsim.network.connectivity.ComputerArpIncomingTest
import ipsim.network.ComputerTest.testCardIndexRetention
import ipsim.util.Collections.testAddOrSet

import fj.P1

object RunTests extends Application {
 import System.{setProperty,currentTimeMillis}

 setProperty("java.awt.headless","true")
 println("Running tests")
 val start=currentTimeMillis
 implicit val random=new java.util.Random(0)

 def testG=() => network.ProblemDifficulty.testGeneration(random)

 implicit def p1ToClosure(la: P1[java.lang.Boolean]): () => Boolean = new (() => Boolean) { def apply() = la._1.asInstanceOf[Boolean]
                                                                                                             override def toString = la.toString }
 val results=List[() => Boolean](PositionTest.testRetention, PositionTest.cableWithTwoEnds, PositionTest.setParentTwice,
                                 PositionTest.testRetention2,
                                 Collections.testAddCollection,
                                 testG,
                                 CableTest.testCableWithNoParents,
                                 CableTest.testCable,
                                 () => testRetention,
                                 () => testGetBroadcastRoute,
                                 ProblemTest.testInvalidNetMaskRejection,
                                 ProblemTest.test1,
                                 () => testCardIndexRetention,
                                 testAddOrSet)
 println(results.map(_()))
}
