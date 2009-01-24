package ipsim.tests

import ipsim.lang.Implicits._
import com.rickyclarkson.testsuite.UnitTestUtility.runTests
import ipsim.network.{Positions, ProblemTest, Computer}
import ipsim.network.connectivity.cable.CableTest
import ipsim.persistence.{LogRetentionTest, XMLSerialisationTest}
import ipsim.network.{Hub, BugzillaBug18}
import ipsim.util.Collections
import ipsim.network.{PositionTest, InfiniteLoopBug}
import ipsim.network.connectivity.FullyConnectedFilesTest
import ipsim.network.connectivity.{ComputerArpIncomingTest, ComputerArpOutgoingTest, IncompleteArpTest}
import ipsim.network.route.RoutingTableTests.{testRetention,testGetBroadcastRoute}
import ipsim.network.route.{RoutingTableBugs, InvalidRouteTest}
import ipsim.network.connectivity.{BroadcastPingTest, RoutingLoopTest, PingerTest, UnconnectedFilesTest, ComputerArpIncomingTest, FullyConnectedFilesTest,
                                   RemoteBroadcastBug, ArpStoredFromForeignNetworkBug}
import ipsim.network.ComputerTest.testCardIndexRetention
import ipsim.network.connectivity.traceroute.TracerouteTest
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
 val results=List[() => Boolean](Computer.testLoadingRestoresBigPipeIcon,
                                 Computer.testLoadingGivesRightNumberOfComponents,
                                 XMLSerialisationTest.testComputerIPForwarding,                 
                                 PositionTest.testRetention, PositionTest.cableWithTwoEnds, PositionTest.setParentTwice,
                                 PositionTest.testRetention2,
                                 Collections.testAddCollection,
                                 testG,
                                 CableTest.testCableWithNoParents,
                                 CableTest.testCable,
                                 BugzillaBug18.test,
                                 FullyConnectedFilesTest.test _,
                                 ComputerArpIncomingTest.test,
                                 () => testRetention,
                                 () => testGetBroadcastRoute,
                                 () => RoutingTableBugs.apply,
                                 () => InvalidRouteTest.apply,
                                 () => BroadcastPingTest.apply,
                                 ProblemTest.testInvalidNetMaskRejection,
                                 ProblemTest.test1,
                                 () => RemoteBroadcastBug.apply,
                                 () => ArpStoredFromForeignNetworkBug.apply,
                                 () => InfiniteLoopBug.apply,
                                 ComputerArpOutgoingTest.test,
                                 () => testCardIndexRetention,
                                 () => RoutingLoopTest.apply,
                                 () => PingerTest.apply,
                                 TracerouteTest.apply _,
                                 UnconnectedFilesTest.apply _,
                                 IncompleteArpTest.apply _,
                                 testAddOrSet)
 println(results.map(x => { println(x); x() })) }
