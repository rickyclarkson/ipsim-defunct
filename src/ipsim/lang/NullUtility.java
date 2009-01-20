package ipsim.lang;

import fpeas.predicate.*;

public class NullUtility
{

	public static <T> Predicate<T> ifNull()
	{
		return new Predicate<T>()
		{
			public boolean invoke(final T t)
			{
				return t==null;
			}
		};
	}
}