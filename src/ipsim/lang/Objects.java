package ipsim.lang;

import fj.F;
import fj.data.Option;

public class Objects
{
	public static <T> F<T, String> toStringRef()
	{
		return new F<T, String>()
		{
			public String f(final Object o)
			{
				return o.toString();
			}
		};
	}

  public static F<Object, Boolean> notEquals(final Object object)
	{
          return new F<Object, Boolean>()
		{
			public Boolean f(final Object o)
			{
				return !o.equals(object);
			}
		};
	}

  public static <T> F<Object, Boolean> equal(final T t)
	{
          return new F<Object, Boolean>()
		{
			public Boolean f(final Object t2)
			{
				return t2.equals(t);
			}
		};
	}

	public static <T,R> R lift(final T t, final R ifNull, final F<T, R> ifNotNull)
	{
		return t==null ? ifNull : ifNotNull.f(t);
	}

	public static <T,R> R lift(Option<T> t,final R ifNothing,final F<T,R> ifJust)
	{
          return t.option(ifNothing, ifJust);
	}
}