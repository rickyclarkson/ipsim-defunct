package ipsim.lang;

import fpeas.function.*;
import static fpeas.lazy.LazyUtility.*;
import fpeas.maybe.*;
import fpeas.predicate.*;
import org.jetbrains.annotations.*;

public class Objects
{
	public static <T> Function<T, String> toStringRef()
	{
		return new Function<T, String>()
		{
			//TODO find out why Object works there.

			@NotNull
			public String run(@NotNull final Object o)
			{
				return o.toString();
			}
		};
	}

	public static Predicate<Object> notEquals(final Object object)
	{
		return new Predicate<Object>()
		{
			public boolean invoke(final Object o)
			{
				return !o.equals(object);
			}
		};
	}

	public static <T> Predicate<Object> equal(final T t)
	{
		return new Predicate<Object>()
		{
			public boolean invoke(final Object t2)
			{
				return t2.equals(t);
			}
		};
	}

	@NotNull
	public static <T,R> R lift(@Nullable final T t, final R ifNull, final Function<T, R> ifNotNull)
	{
		return t==null ? ifNull : ifNotNull.run(t);
	}

	public static <T,R> R lift(Maybe<T> t,final R ifNothing,final Function<T,R> ifJust)
	{
		return t.apply(constant(ifNothing),ifJust);
	}
}