package ipsim.webinterface;
/*
import ipsim.gui.Global;

import java.io.*;
import java.net.*;

public final class Web
{
	private static final String server="fw4.cms.salford.ac.uk";
	private static final int PORT=80;
	private static final String cgi="/cgi-bin/ipsim2.cgi";

	/**
	 @throws IOException if there is a network access problem.
	 * /
	public static String webInteraction(final String command, final String testNumber, final String line1, final String line2) throws IOException
	{
		final StringBuilder output=new StringBuilder("");

		// Make a connection to the socket
		// Wait five seconds for a reply (exception thrown if
		// timeout)
		final Socket socket=new Socket(server,PORT);
		socket.setSoTimeout(5000);

		final PrintWriter writer=new PrintWriter(socket.getOutputStream());

		final BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Sent HTTP/1.0 "POST" command for the root document
		writer.println("POST "+cgi+" HTTP/1.0");
		writer.println("Host: "+server);
		writer.println("Content-Type: text/plain");

		writer.println("Content-Length: "+(5+Global.CUSTOMER_NUMBER.length()+command.length()+testNumber.length()+line1.length()+line2.length()));

		writer.println();
		writer.println(command+' '+Global.CUSTOMER_NUMBER+' '+testNumber+' '+line1);
		writer.println(line2);
		writer.flush();

		// Read the HTTP response (ignore the body)
		int state=0;
		while (true)
			try
			{
				final String line=reader.readLine();

				if (line==null)
					break;

				if (0==state && 0==line.length())
					state=1;
				else
					if (1==state)
					{
						output.append(line);
						output.append('\n');
					}
			}
			catch (final IOException e)
			{
				// Usually the connection has been
				// terminated by the other end
				break;
			}

		// Tidy up
		writer.close();
		reader.close();
		socket.close();

		return output.toString();
	}
}*/