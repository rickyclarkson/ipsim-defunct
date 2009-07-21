package ipsim.lang

object TupleAdd { implicit def tupleAdd(a: (Int, Int)) = new { def +(b: (Int, Int))=(a._1 + b._1, a._2 + b._2) } }
