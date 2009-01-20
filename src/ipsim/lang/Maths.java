package ipsim.lang;

public class Maths
{
	public static int max(final int... values)
	{
		int max=values[0];

		for (final int value: values)
			if (value>max)
				max=value;

		return max;
	}

	public static boolean approxEqual(final double one, final double two)
	{
		return Math.abs(one-two)<0.005;
	}
}
