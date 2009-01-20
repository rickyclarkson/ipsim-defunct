package ipsim.gui.components;
/*
import anylayout.*;
import anylayout.extras.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.connectivity.ping.*;
import ipsim.network.ip.*;
import ipsim.swing.*;
import ipsim.util.Collections;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PingDialog
{
	public final JDialog jDialog;

	public PingDialog(final JFrame frame,final Network network,final Computer computer)
	{
            jDialog=Dialogs.createDialogWithEscapeKeyToClose("Ping", frame);

		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(jDialog.getContentPane());

		jDialog.setSize(600,400);
		ComponentUtility.centreOnParent(jDialog, frame);

		AnyLayout.useAnyLayout(jDialog.getContentPane(),0.5f,0.5f, SizeCalculatorUtility.absoluteSize(600,400), ConstraintUtility.typicalDefaultConstraint(RunnablesInteropFailure.throwRuntimeException));

		constraints.add(new JLabel("IP Address"),5,5,25,10,true,true);

		final IPAddressTextField ipAddressTextField=IPAddressTextFieldUtility.newInstance();

		ipAddressTextField.setIPAddress(new IPAddress(0));

		constraints.add(ipAddressTextField.textField,5,15,25,10,false,false);

		final JButton pingButton=new JButton("Ping");
		pingButton.setMnemonic('P');

		constraints.add(pingButton,35,15,25,10,false,false);

		final JTextArea textArea=new JTextArea(10, 10);
		textArea.setEditable(false);

		pingButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				final IPAddress ipAddress=ipAddressTextField.getIPAddress();

				final Writer documentWriter=DocumentWriter.documentWriter(textArea.getDocument());

				final PrintWriter printWriter=new PrintWriter(documentWriter);

				final List<PingResults> pingResults=Pinger.ping(computer, ipAddress, Globals.DEFAULT_TIME_TO_LIVE, network);
				network.log.add(pinged(computer, ipAddress, pingResults, network));

				try
				{
					printWriter.println(Collections.<PingResults>asString(pingResults,"\n"));
				}
				finally
				{
					printWriter.close();
				}
			}

		});

		constraints.add(new JScrollPane(textArea),10,30,80,60,true,true);

		final JButton closeButton=Buttons.closeButton("Close", jDialog);
		closeButton.setMnemonic('C');

		constraints.add(closeButton,75,15,25,10,false,false);
	}

	public static String pinged(final Computer source, final IPAddress destination, final List<PingResults> pingResults, final Network network)
	{
		String string=PacketSourceUtility.asString(network,source);

		string=Character.toUpperCase(string.charAt(0))+string.substring(1);

		final StringBuilder tempDescription=new StringBuilder(string);
		tempDescription.append(" pinged ");
		tempDescription.append(destination);
		tempDescription.append(", ");

		if (1==pingResults.size())
		{
			final PingResults results=pingResults.get(0);

			tempDescription.append(results==null ? "" : "with a result of \""+results.toString()+"\".");
		}
		else
		{
			tempDescription.append(pingResults.size());
			tempDescription.append(" results returned");
		}

		return tempDescription.toString();
	}
        }*/