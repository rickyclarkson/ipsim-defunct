package ipsim.lang

object Maths {
 def approxEqual(one: Double, two: Double) = Math.abs(one - two) < 0.005
}
