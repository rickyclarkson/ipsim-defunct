package ipsim.lang;

import fpeas.function.*;

import java.util.*;

public class Comparators
{
	public static <T> Comparator<T> fromFunction(final Function<T, Integer> function)
	{
		return new Comparator<T>()
		{
			public int compare(final T o1, final T o2)
			{
				return function.run(o1)-function.run(o2);
			}
		};
	}
}