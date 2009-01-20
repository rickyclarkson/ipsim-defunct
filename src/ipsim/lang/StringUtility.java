package ipsim.lang;

import java.util.*;

public final class StringUtility
{
	public static String join(final Iterable<String> strings, final String joiner)
	{
		final StringBuilder builder=new StringBuilder();

		final Iterator<String> iterator=strings.iterator();

		if (iterator.hasNext())
			builder.append(iterator.next());

		while (iterator.hasNext())
		{
			final String next=iterator.next();
			builder.append(joiner);
			builder.append(next);
		}

		return builder.toString();
	}
}