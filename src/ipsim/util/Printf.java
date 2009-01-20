package ipsim.util;

import java.io.*;

public final class Printf
{
	private Printf()
	{
	}

	public static String sprintf
		(final String format,final Object... args)
	{
		final StringWriter stringWriter=new StringWriter();
		PrintWriter writer=null;

		try
		{
			writer=new PrintWriter(stringWriter);
			writer.printf(format,args);
			return stringWriter.toString();
		}
		finally
		{
			writer.close();
		}
	}
}