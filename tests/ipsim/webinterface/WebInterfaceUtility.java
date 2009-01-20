package ipsim.webinterface;

import java.util.regex.*;

public final class WebInterfaceUtility
{
	public static String[] getLogAndSaveValues(final String output)
	{
		final Matcher matcher=Pattern.compile("102: exception/log(\\d+\\.\\d+)\n"+"102: exception/save(\\d+\\.\\d+)\n").matcher(output);
		return new String[]{matcher.group(1),matcher.group(2)};
	}
}