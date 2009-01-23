package ipsim;

import ipsim.lang.*;
import ipsim.util.*;

import fj.F;

public class Caster
{
  public static <T> F<T, Boolean> equalT(final T first)
	{
          return new F<T, Boolean>()
		{
			public Boolean f(final T second)
			{
				return first.equals(second);
			}
		};
	}

	public static <T> F<Object, T> asFunction(final Class<T> aClass)
	{
		return new F<Object, T>()
		{
			public T f(final Object o)
			{
				return aClass.cast(o);
			}
		};
	}
}
