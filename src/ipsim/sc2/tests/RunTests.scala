package ipsim.tests

import ipsim.lang.Implicits._
import com.rickyclarkson.testsuite.UnitTestUtility.runTests
import ipsim.network.{Positions, ProblemTest, Computer}
import ipsim.network.connectivity.cable.CableTest
import ipsim.persistence.{LogRetentionTest, XMLSerialisationTest}
import ipsim.network.{Hub, BugzillaBug18}
import ipsim.util.{Collections,Stream}
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

object RunTests extends Application {
 import System.{setProperty,currentTimeMillis}

 setProperty("java.awt.headless","true")
 println("Running tests")
 val start=currentTimeMillis
 implicit val random=new scala.Random(0)

 def testG=() => network.ProblemDifficulty.testGeneration(random)

 implicit def lazyToClosure(la: fpeas.`lazy`.Lazy[java.lang.Boolean]): () => Boolean = new (() => Boolean) { def apply() = la.invoke.asInstanceOf[Boolean]
                                                                                                             override def toString = la.toString }
 val results=List[() => Boolean](Computer.testLoadingRestoresBigPipeIcon,
                                 Computer.testLoadingGivesRightNumberOfComponents,
                                 Stream.testOnly, Stream.testMap, Stream.testIndexOf, Stream.testIterator,
                                 Stream.testContains, Stream.testEmpty,
                                 XMLSerialisationTest.testComputerIPForwarding,                 
                                 PositionTest.testRetention, PositionTest.cableWithTwoEnds, PositionTest.setParentTwice,
                                 PositionTest.testRetention2,
                                 Collections.testAddCollection,
                                 new ConformanceTestsTest(),
                                 XMLSerialisationTest.testComputerIPForwarding,
                                 testG,
                                 CableTest.testCableWithNoParents,
                                 CableTest.testCable,
                                 BugzillaBug18,
                                 FullyConnectedFilesTest.test,
                                 ComputerArpIncomingTest.test,
                                 testRetention,
                                 testGetBroadcastRoute,
                                 RoutingTableBugs,
                                 InvalidRouteTest,
                                 BroadcastPingTest,
                                 ProblemTest.testInvalidNetMaskRejection,
                                 ProblemTest.test1,
                                 RemoteBroadcastBug,
                                 ArpStoredFromForeignNetworkBug,
                                 InfiniteLoopBug,
                                 ComputerArpOutgoingTest,
                                 testCardIndexRetention,
                                 RoutingLoopTest,
                                 PingerTest,
                                 TracerouteTest,
                                 UnconnectedFilesTest,
                                 IncompleteArpTest,
                                 testAddOrSet)
 println(results.map(x => x())) }
