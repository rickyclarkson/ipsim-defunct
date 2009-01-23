package ipsim.lang;

import fj.F;
import fj.P1;

public class Integers
{
	public static final F<String, Integer> parseIntRef=new F<String, Integer>()
	{
		public Integer f(final String s)
		{
			return Integer.parseInt(s);
		}
	};
	public static F<String, P1<Integer>> parseIntLazy=new F<String, P1<Integer>>()
	{
		public P1<Integer> f(final String s)
		{
			return new P1<Integer>()
			{
				public Integer _1()
				{
					return Integer.parseInt(s);
				}
			};
		}
	};
}