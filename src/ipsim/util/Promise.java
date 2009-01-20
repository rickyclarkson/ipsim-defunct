package ipsim.util;

import org.jetbrains.annotations.*;

import java.lang.ref.*;

public abstract class Promise<T>
{
	private SoftReference<T> forced=null;

	@NotNull
	public T force()
	{
		if (forced==null || forced.get()==null)
			forced=new SoftReference<T>(override());

		return forced.get();
	}

	@NotNull
	public abstract T override();

	public static <T> Promise<T> forced(@NotNull final T t)
	{
		return new Promise<T>()
		{
			@NotNull
			@Override
			public T override()
			{
				return t;
			}
		};
	}
}