package ipsim.lang;

import fpeas.sideeffect.*;

public class SideEffects
{
	public static <T> SideEffect<T> fromRunnable(final Runnable runnable)
	{
		return new SideEffect<T>()
		{
			public void run(final T t)
			{
				runnable.run();
			}
		};
	}
}
