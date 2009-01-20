package ipsim.util;

public final class Arrays
{
	public static <E> String toString(final String separator, final E... array)
	{
		final StringBuilder answer=new StringBuilder();

		for (int a=0;a<array.length;a++)
		{
			answer.append(array[a]);

			if (a<array.length-1)
				answer.append(separator);
		}

		return answer.toString();
	}
}