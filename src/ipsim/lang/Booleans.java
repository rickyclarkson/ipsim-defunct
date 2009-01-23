package ipsim.lang;

import fj.F;

public class Booleans
{
	public static final F<String, Boolean> toString=new F<String, Boolean>()
	{
		public Boolean f(final String s)
		{
			return Boolean.valueOf(s);
		}
	};
}