package ipsim.gui

import ipsim.network.Card

object TwoCablesOnOneCardTest {
 def testTwoCablesOnOneCard = UserAction.newNetwork failsWith("Stupid") }

object UserAction {
 def newNetwork = new Action(throw null) }

class Action(val r: () => Option[String]) { def then(a: Action): Action=new Action(() => r() match { case Some(c) => Some(c)
                                                                                                     case None => a.r() } )
                                            def failsWith(s: String)=r() match { case Some(t) => s==t
                                                                                 case None => false } }
