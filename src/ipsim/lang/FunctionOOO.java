package ipsim.lang;

import fpeas.function.*;
import org.jetbrains.annotations.*;

public abstract class FunctionOOO<T,U,R>
{
	public abstract R invoke(T t,U u);

	public static <T,U,R> FunctionOOO<T,U,R> ignoreFirst(final Function<U,R> function)
	{
		return new FunctionOOO<T, U, R>()
		{
			@Override
			public R invoke(T t, U u)
			{
				return function.run(u);
			}
		};
	}

	public static <T,U,R> Function<U,R> partApply(final FunctionOOO<T,U,R> function, final T t)
	{
		return new Function<U, R>()
		{
			@NotNull
			public R run(@NotNull final U u)
			{
				return function.invoke(t,u);
			}
		};
	}
}