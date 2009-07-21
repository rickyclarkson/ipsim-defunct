package ipsim.tests

import ipsim.lang.Implicits._
import com.rickyclarkson.testsuite.UnitTestUtility.runTests
import ipsim.network.{Positions, ProblemTest, Computer}
import ipsim.network.connectivity.cable.CableTest
import ipsim.network.{Hub, PositionTest}
import ipsim.network.route.RoutingTableTests.{testRetention,testGetBroadcastRoute}
import ipsim.network.ComputerTest.testCardIndexRetention

object RunTests extends Application {
 import System.{setProperty,currentTimeMillis}

 setProperty("java.awt.headless","true")
 println("Running tests")
 val start=currentTimeMillis
 implicit val random=new java.util.Random(0)

 def testG=() => network.ProblemDifficulty.testGeneration(random)

 val results=List[() => Boolean](PositionTest.testRetention, PositionTest.cableWithTwoEnds, PositionTest.setParentTwice,
                                 PositionTest.testRetention2,
                                 testG,
                                 CableTest.testCableWithNoParents,
                                 CableTest.testCable,
                                 () => testRetention,
                                 () => testGetBroadcastRoute,
                                 ProblemTest.testInvalidNetMaskRejection,
                                 ProblemTest.test1,
                                 () => testCardIndexRetention)
 println(results.map(_()))
}
