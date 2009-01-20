package ipsim.io;

import fpeas.sideeffect.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.net.*;

public final class IOUtility
{
    @Nullable
    public static String readWholeResource(final URL resource)
	{
		try
		{
			InputStream inputStream=null;
			InputStreamReader inputStreamReader=null;
			BufferedReader reader=null;

			final StringBuilder builder=new StringBuilder();

			try
			{
				final URLConnection connection=resource.openConnection();
				connection.connect();

				inputStream=connection.getInputStream();
				inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
				reader=new BufferedReader(inputStreamReader);

				boolean first=true;
				String string;

				do
				{
					string=reader.readLine();

					if (string!=null)
					{
						if (!first)
							builder.append('\n');

						builder.append(string);
						first=false;
					}
				}
				while (string!=null);

				reader.close();
			}
			finally
			{
				if (reader!=null)
					reader.close();

				if (inputStreamReader!=null)
					inputStreamReader.close();

				if (inputStream!=null)
					inputStream.close();
			}

			return builder.toString();
		}
		catch (final IOException exception)
		{
		    return null;
		}
	}

	public static void withPrintWriter(final Writer writer, final SideEffect<PrintWriter> sideEffect)
	{
		final PrintWriter pw=new PrintWriter(writer);
		try
		{
			sideEffect.run(pw);
		}
		finally
		{
			pw.close();
		}
	}
}