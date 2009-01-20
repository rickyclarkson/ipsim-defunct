package ipsim.util;

import ipsim.lang.*;
import fpeas.sideeffect.*;

public final class StackUtility
{
	public static <T extends Stringable> void pushAll
		(final Stack<T> stack,final Stream<T> items)
	{
		items.foreach(new SideEffect<T>()
		{
			public void run(final T t)
			{
				stack.push(t);
			}
		});
	}
}