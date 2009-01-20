package ipsim.lang;

import fpeas.function.*;
import org.jetbrains.annotations.*;

public class Booleans
{
	public static final Function<String, Boolean> toString=new Function<String, Boolean>()
	{
		@NotNull
		public Boolean run(@NotNull final String s)
		{
			return Boolean.valueOf(s);
		}
	};
}