package ipsim;

import fpeas.function.*;
import fpeas.predicate.*;
import ipsim.lang.*;
import ipsim.util.*;
import fpeas.lazy.Lazy;
import org.jetbrains.annotations.*;
import com.rickyclarkson.testsuite.*;

public class Caster
{
	public static <T> Predicate<T> equalT(@NotNull final T first)
	{
		return new Predicate<T>()
		{
			public boolean invoke(final T second)
			{
				return first.equals(second);
			}
		};
	}

	@NotNull
	public static <T> T asNotNull(@Nullable final T t)
	{
		if (t==null)
			throw null;

		return t;
	}

	public static <T> Function<Object, T> asFunction(final Class<T> aClass)
	{
		return new Function<Object, T>()
		{
			@NotNull
			public T run(@NotNull final Object o)
			{
				return aClass.cast(o);
			}
		};
	}
    /*
	public static <T> Function<Object, Stream<T>> asStreamOfType(final Class<T> type)
	{
		return new Function<Object, Stream<T>>()
		{
			@NotNull
			public Stream<T> run(@NotNull final Object o)
			{
				return ((Stream<Object>)o).map(Classes.<Object,T>cast(type));
			}
		};
                }*/

    /*	public static final Lazy<Boolean> testAsStreamOfType=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			return asStreamOfType(String.class).run(Stream.one("hello").prepend("world")).size()==2;
		}
                }; */
}
