package ipsim.lang;

import java.util.*;

public class Randoms
{
	public static <T> T randomOneOf(Random random,final T... options)
	{
		return options[random.nextInt(options.length)];
	}
}