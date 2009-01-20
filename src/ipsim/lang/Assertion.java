package ipsim.lang;

public final class Assertion
{
	public static void assertTrue(final boolean... values)
	{
		for (final boolean value: values)
			if (!value)
				throw new RuntimeException();
	}

	public static void assertFalse(final boolean... values)
	{
		for (final boolean value: values)
			if (value)
				throw new RuntimeException();
	}
}
