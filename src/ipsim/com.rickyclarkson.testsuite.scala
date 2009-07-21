package com.rickyclarkson.testsuite

import ipsim.lang.TupleAdd.tupleAdd

object UnitTestUtility { def runTests(tests: List[() => Boolean]): Boolean = {
                          def loop(tests: List[() => Boolean]): (Int, Int) =
                           tests match { case x::xs => if (x()) { println(x+" passed")
                                                                  (1,0)+loop(xs) }
                                                       else { println(x+" failed")
                                                              (0,1)+loop(xs) }
                                         case _ => (0,0) }
 
                          val res=loop(tests)

                          println(res._1+" passed")
                          println(res._2+" failed")

                          res._2==0 } }
