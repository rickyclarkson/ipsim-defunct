package ipsim.lang;

import fpeas.predicate.*;
import fpeas.function.*;
import org.jetbrains.annotations.*;

public class Classes
{
	public static Predicate<Object> isAssignableFrom(final Class<?> aClass)
	{
		return new Predicate<Object>()
		{
			public boolean invoke(final Object o)
			{
				return aClass.isAssignableFrom(o.getClass());
			}
		};
	}

	public static <T, R extends T> Function<T, R> cast(final Class<R> type)
	{
		return new Function<T, R>()
		{
			@NotNull
			public R run(@NotNull final T t)
			{
				return type.cast(t);
			}
		};
	}
}