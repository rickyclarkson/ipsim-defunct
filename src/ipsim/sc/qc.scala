package ipsim.qc

import scala.Random

object QuickCheck {
 def check[T](generator: Random => Stream[T], property: T => Boolean)(implicit random: Random) = generator(random) take 500 forall property }
