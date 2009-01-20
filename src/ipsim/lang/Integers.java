package ipsim.lang;

import fpeas.function.*;
import fpeas.lazy.*;
import org.jetbrains.annotations.*;

public class Integers
{
	public static final Function<String, Integer> parseIntRef=new Function<String, Integer>()
	{
		@NotNull
		public Integer run(@NotNull final String s)
		{
			return Integer.parseInt(s);
		}
	};
	public static Function<String, Lazy<Integer>> parseIntLazy=new Function<String, Lazy<Integer>>()
	{
		@NotNull
		public Lazy<Integer> run(@NotNull final String s)
		{
			return new Lazy<Integer>()
			{
				public Integer invoke()
				{
					return Integer.parseInt(s);
				}
			};
		}
	};
}