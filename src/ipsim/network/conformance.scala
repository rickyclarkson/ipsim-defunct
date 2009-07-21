package ipsim.network.conformance

import ipsim.network.Network
import ipsim.lang.Implicits._
import scala.collection.mutable

case class ConformanceTests(implicit network: Network) {
 def allChecks = { val checks = networkChecks
                   val answer = new StringBuilder
                   var totalPercent = 100
                   val checkResults = new mutable.ListBuffer[CheckResult]
                   for (result <- checks) {
                    import result.{percent, summary}
                    totalPercent *= percent
                    totalPercent /= 100
                    if (percent != 100) { answer.append(summary.isEmpty ? "" ! summary.head)
                                          checkResults += result
                                          answer append "\n" } }
                   ResultsAndSummaryAndPercent(checkResults, answer.toString, totalPercent) }

 case class CheckResult(percent: Int, summary: Seq[String])
 object CheckResult { def fine = CheckResult(100,Nil)
                      def pessimisticMerge(seq: Seq[CheckResult]) = {
                       var minPercent: CheckResult = null
                       for (result <- seq)
                        if (minPercent == null || result.percent<minPercent.percent)
                         minPercent = result
                       (minPercent == null) ? fine ! minPercent } }

 import CheckResult.pessimisticMerge
 case class ResultsAndSummaryAndPercent(results: Seq[CheckResult], summary: String, percent: Int)

 def networkChecks: List[CheckResult] = List[CheckResult](pessimisticMerge(network.problems map (percentOfIPsMatchingProblem _)),
                                                          pessimisticMerge(network.problems map (zeroSubnetMaskUsed _))) ++ (network.problems flatMap (oneSubnetMaskUsed _)) ++ List[CheckResult](pessimisticMerge(network.problems flatMap (percentOfRequiredSubnets _))) ++
                                                           someChecks ++ cycleInDefaultRoutes
                                                  
 def percentOfIPsMatchingProblem(problem: Problem) = {
  val cards = network.all flatMap (_.asCard) flatMap (_.cardDrivers) filter (_.ipAddress != IPAddress.zero)
  val totalCorrect = cards filter (problem.netBlock contains _.ipAddress) size
  val percent = (0==cards.size) ? 0 ! (totalCorrect * 100 / cards.size)
  CheckResult(percent, List("IP address that doesn't match the problem given")) }

 def cycleInDefaultRoutes: Option[CheckResult] = {
  for {
   computer <- network.all flatMap (_.asComputer)
   route <- computer.routingTable.routes
   if route.isDefault
   if !(network.all flatMap (_.asComputer) filter (_.cards flatMap (_.cardDrivers) exists (_.ipAddress == route.gateway)) contains computer)
   if detectCycle((c: Computer) => false, computer, route) } return Some(CheckResult(TypicalScores.usual, List("Cycle in default routes")))
  None }
 import ipsim.network.route.Route
 private def detectCycle(containsComputer: Computer => Boolean, computer: Computer, route: Route): Boolean = 
  if (route.isRouteToSelf(computer)) false else network.computersByIP(route.gateway) exists (computer1 => if (containsComputer(computer1)) true else
   computer1.routingTable.defaultRoutes exists (route2 => detectCycle((aComputer => aComputer == computer || containsComputer(aComputer)),
                                                                      computer, route2)))
 def zeroSubnetMaskUsed(problem: Problem) = {
  val rawProblemNumber = problem.netBlock.networkNumber.rawValue
  val problemMaskPrefix = problem.netBlock.netMask.prefixLength
  customCheck(network.all flatMap (_.asCard), (card: Card) => { card.cardDrivers match {
   case None => None
   case Some(cardDrivers) => { val rawNetworkNumber = cardDrivers.ipAddress.rawValue & cardDrivers.netMask.rawValue
                               val cardNetMaskPrefix = cardDrivers.netMask.prefixLength
                               val equalNumbers = rawNetworkNumber == rawProblemNumber
                               if (equalNumbers && problemMaskPrefix < cardNetMaskPrefix)
                                Some("A subnet that uses an all-0s subnet number")
                               else if (problemMaskPrefix >= cardNetMaskPrefix)
                                Some("A subnet mask that is equal to or shorter than the problem's")
                               else if (equalNumbers)
                                Some("No subnetting attempted")
                               else None } } }, TypicalScores.usual) }

 def oneSubnetMaskUsed(problem: Problem): Option[CheckResult] = {
  val rawProblemNumber = problem.netBlock.networkNumber.rawValue
  val problemPrefix = problem.netBlock.netMask.prefixLength
  for (card <- network.all flatMap (_.asCard) flatMap (_.cardDrivers)) {
   val rawNetworkNumber = card.ipAddress.rawValue & card.netMask.rawValue
   val cardPrefix = card.netMask.prefixLength
   if (cardPrefix != problemPrefix && rawNetworkNumber - rawProblemNumber == (1 << cardPrefix - problemPrefix) - 1)
    return Some(CheckResult(TypicalScores.usual, List("A subnet that uses an all-1s network number"))) }
  return None }

 def percentOfRequiredSubnets(problem: Problem) = {
  import problem.{numberOfSubnets => ideal}
  import network.{numberOfSubnets => actual}
  if (actual > ideal) Some(CheckResult(TypicalScores.usual, List("More subnets in the solution than are in the problem")))
  else {
   val percent = (actual==ideal) ? 100 ! (100 * (ideal - (ideal - actual)) / ideal)
   if (percent != 100) Some(CheckResult(percent, List("Less subnets in the solution than the problem requires")))
   else None }}

 def computers = network.all flatMap (_.asComputer)
 def cables = network.all flatMap (_.asCable)
 def cards = network.all flatMap (_.asCard)
 def cardsWithDrivers = cards flatMap (_.cardDrivers)
 def hubs = network.all flatMap (_.asHub)

 def someChecks: List[CheckResult] = List[(Boolean, String, Int)](
  (computers exists (_.cards.isEmpty), "A computer without a network card", TypicalScores.usual),
  (cards exists (_.children(network.all).isEmpty), "A card without a cable", TypicalScores.usual),
  (cables exists (_.children(network.all).size != 2), "A cable that has not got both ends connected to components", TypicalScores.usual),
  (cards exists (_.cardDrivers.isEmpty), "A card with no device drivers", TypicalScores.usual),
  (cardsWithDrivers exists (_.ipAddress == IPAddress.zero), "A card with a 0.0.0.0 IP address", TypicalScores.usual),
  (hubs exists (_.children(network.all).isEmpty), "A hub with no cables", TypicalScores.usual),
  (hubs exists (hub => ((hub.otherEnds(network.all) flatMap (_.asCardDrivers) filter (_.ipAddress.isZero) toList) zipWithTail) exists (_.differs(_.netBlock))),
   "A hub with more than one subnet", TypicalScores.severe),
  (computers.exists(_.cards(network).size >= 3), "Some computers have 3 or more cards", TypicalScores.usual),
  (cardsWithDrivers exists (card => if (card.ipAddress.isZero) false else (card.ipAddress & card.netMask) == 0),
   "A card with an all-0s host part of its IP address", TypicalScores.usual),
  (cardsWithDrivers exists (card => (card.ipAddress & ~card.netMask) == ~card.netMask), "A card with an all-1s host number", TypicalScores.usual),
  (hubs exists (hub => !hub.otherEnds(network.all).exists(_.parents(0).children(network.all).size==1)),
   "Hub with no standalone (non-gateway) computer", TypicalScores.usual),
  (computers exists (_.cardsWithDrivers map (_.netBlock.networkNumber) hasDuplicates),
   "A computer that has multiple cards with the same subnet number", TypicalScores.usual),
  (computers exists (computer => computer.routingTable.routes exists (computer.ipAddresses contains _.gateway)),
   "A computer with a route that points to itself", TypicalScores.usual),
  (computers filter (!_.isRouter) exists (!_.routingTable.explicitRoutes.isEmpty), "An explicit route on a non-gateway computer", TypicalScores.usual),
  (computers flatMap (_.routingTable.routes) exists (route => !network.computersByIP(route.gateway).exists(_.isRouter)),
   "A computer with a route that doesn't point to a gateway", TypicalScores.usual),
  (computers exists (computer => computer.cards(network) flatMap (_.cardDrivers) exists (card => computer.routingTable.explicitRoutes exists (_.netBlock == card.netBlock))),
   "A computer with an explicit route that points to one of its local networks", TypicalScores.usual),
  (computers exists (computer => !computer.isRouter && computer.routingTable.defaultRoutes.isEmpty),
   "A non-gateway computer without a default route", TypicalScores.usual),
  (computers exists (computer => !computer.isRouter && computer.ipForwardingEnabled),
   "A computer that is not a gateway but has packet forwarding enabled", TypicalScores.usual),
  (computers exists (_.routingTable.explicitRoutes map (_.netBlock) hasDuplicates),
   "A computer with more than one route to the same network", TypicalScores.usual),
  (computers exists (_.routingTable.routes filter (!_.netBlock.networkNumber.isZero) map (_.netBlock.networkNumber) hasDuplicates),
   "A computer with more than one route to the same subnet", TypicalScores.usual),
  (computers exists (computer => computer.routingTable.routes exists (route => computer.isLocallyReachable(route.gateway))),
   "A route with a non-local gateway (this is an IPSim bug if it isn't from an old save file)", TypicalScores.usual)
) flatMap (_ match { case (p, msg, score) =>
   if (p) Some(CheckResult(score, List(msg))) else None } )

 def customCheck[T](collection: Seq[T], warning: T => Option[String], deductionsIfFound: Int) = {
  val warnings = collection flatMap (x => warning(x)) 
  CheckResult(warnings.isEmpty ? TypicalScores.none ! deductionsIfFound, warnings) } }

object TypicalScores { val none = 100
                       val usual = 90
                       val severe = 50 }
