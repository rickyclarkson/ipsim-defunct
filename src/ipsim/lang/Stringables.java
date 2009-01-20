package ipsim.lang;

import fpeas.function.*;
import org.jetbrains.annotations.*;

public class Stringables
{
	public static <T extends Stringable> Function<T,String> asString()
	{
		return new Function<T,String>()
		{
			@NotNull
			public String run(@NotNull final T stringable)
			{
				return stringable.toString();
			}
		};
	}
}
