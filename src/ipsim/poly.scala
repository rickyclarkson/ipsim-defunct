package ipsim.sc

case class Term(factor: Int,power: Int) { override def toString=factor+"x^"+power }

case class Poly(terms: Seq[Term]) {
 def +(other: Poly) = (other.terms map (
  t => Term((terms find (_.power==t.power)
                   getOrElse Term(0,t.power)).factor+t.factor,
            t.power)))
 override def toString = terms.foldLeft("")(_+"+"+_).substring(1) }

object Polynomial {
 def stringToOption(s: String)=if (s.length==0) None else Some(s)
 def onlyStartingDigits(s: String): String = if (s.length==0) s else if (s charAt 0 isDigit) s.charAt(0)+onlyStartingDigits(s substring 1) else ""
 def parsePoly(poly: String) = Poly(
  poly split ("\\+") map {s => Term(Integer.parseInt(stringToOption(onlyStartingDigits(s)) getOrElse "1"),
                                    if (s contains "^") Integer parseInt (s.split("\\^")(1)) else if (s contains "x") 1 else 0) } ) }