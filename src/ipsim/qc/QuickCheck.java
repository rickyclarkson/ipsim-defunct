package ipsim.qc;

import fpeas.predicate.*;

import java.util.*;

public class QuickCheck
{
	public static <T> boolean check(Random random,Predicate<T> property,Arbitrary<T> generator)
	{
		return generator.data(random).take(500).all(property);
	}
}