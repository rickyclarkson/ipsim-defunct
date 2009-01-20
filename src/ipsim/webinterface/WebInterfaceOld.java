package ipsim.webinterface;
/*
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import static ipsim.NetworkContext.*;
import ipsim.gui.*;
import static ipsim.webinterface.Web.*;
import static ipsim.webinterface.WebInterfaceUtility.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.io.*;

/**
 * SU is normal problems, TS is troubleshoot.
 * /
public class WebInterface
{
	public static void putException(final JFrame frame,final String exceptiontext, final String currentconfig)
	{
		try
		{
			final int sequence=1;
			final String output=webInteraction("PUT", "","exception/log"+sequence,exceptiontext)+webInteraction("PUT", "","exception/save"+sequence,currentconfig);

			if (!output.matches("102: exception/log\\d+\\.\\d+\n"+"102: exception/save\\d+\\.\\d+\n"))
			{
				handleError(frame);
				return;
			}

			final String[] values=getLogAndSaveValues(output);

			UserMessages.message("Tell your tutor to look at exception "+values[0]+" and log "+values[1]+'.');
		}
		catch (final IOException exception)
		{
			handleError(frame);
		}
	}

	private static void handleError(JFrame frame)
	{
		JOptionPane.showMessageDialog(frame, "Problem accessing network", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static Maybe<String> getProblem(final JFrame frame,final String testNumber)
	{
		try
		{
			final String returnCode=webInteraction("TGET", "",testNumber,"");

			if (returnCode.startsWith("3"))
			{
				JOptionPane.showMessageDialog(frame, "The test cannot be done at this time", "Error", JOptionPane.ERROR_MESSAGE);

				return nothing();
			}

			if (!returnCode.startsWith("101"))
			{
				JOptionPane.showMessageDialog(frame, "Problem Accessing Network", "Error", JOptionPane.ERROR_MESSAGE);

				return nothing();
			}

			return just(returnCode);
		}
		catch (final IOException exception)
		{
			JOptionPane.showMessageDialog(frame, "Problem Accessing Network", "Error", JOptionPane.ERROR_MESSAGE);

			return nothing();
		}
	}

	public static final class NamedConfigurationOrFailure
	{
		@Nullable
		public final NamedConfiguration config;

		@Nullable
		public final String reason;

		NamedConfigurationOrFailure(final NamedConfiguration config)
		{
			this.config=config;
			reason=null;
		}

		NamedConfigurationOrFailure(final String reason)
		{
			this.reason=reason;
			config=null;
		}
	}

	@NotNull
	public static NamedConfigurationOrFailure getNamedConfiguration(final String configurationName)
	{

		try
		{
			final String input=webInteraction("CGET", "",configurationName,"");

			if (input.startsWith("101: OK\n"))
				return new NamedConfigurationOrFailure(new NamedConfiguration(configurationName,input.substring("101: OK\n".length())));

			if (input.startsWith("101: OK \""))
				return new NamedConfigurationOrFailure(new NamedConfiguration(input.substring("101: OK \"".length(),input.indexOf((int)'"',"101: OK \"".length()+1)),input.substring(input.indexOf((int)'\n')+1)));

			if (input.startsWith("407"))
				return new NamedConfigurationOrFailure("Cannot download configuration "+configurationName);

			throw null;
		}
		catch (final IOException exception)
		{
			return new NamedConfigurationOrFailure("Problem Accessing Network");
		}
	}

	/**
	 TODO remove 456, replace with test number from user.
	 * /
	public static void putSUProblem(final String user, final String suProblem) throws IOException
	{
		webInteraction("TPUT", "456","su/problems/"+user,suProblem);
	}

	public static String putSUSolution(final JFrame frame,final String testNumber,final String email,final String suSolution) throws IOException
	{
		final String returnCode=webInteraction("TPUT", testNumber,"su/solutions/"+email,suSolution);

		if (returnCode.startsWith("3"))
		{
			errors(frame,"The test cannot be done at this time");

			throw new IOException(returnCode);
		}

		return returnCode;
	}

	public static String putNamedConfiguration(final String name, final String string) throws IOException
	{
		return webInteraction("PUT", "","saved/"+name,string);
	}
}*/