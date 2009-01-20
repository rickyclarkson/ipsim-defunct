package ipsim.traceroute;
/*
import anylayout.*;
import static anylayout.extras.ConstraintUtility.*;
import anylayout.extras.*;
import fpeas.sideeffect.*;
import ipsim.*;
import ipsim.gui.components.*;
import ipsim.io.*;
import static ipsim.lang.Runnables.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.connectivity.traceroute.*;
import ipsim.network.ip.*;
import static ipsim.swing.Buttons.*;
import static ipsim.swing.Dialogs.*;
import ipsim.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public final class TracerouteDialog
{
	public static JDialog newTracerouteDialog(final JFrame frame,final Network network,final Computer computer)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose("Traceroute",frame);

		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		dialog.setLocation(200,100);
		dialog.setSize(400,400);

		final Container pane=dialog.getContentPane();

		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(pane);
		AnyLayout.useAnyLayout(pane,0.5f,0.5f,SizeCalculatorUtility.absoluteSize(200,100),typicalDefaultConstraint(throwRuntimeException));

		constraints.add(new JLabel("IP Address"),10,5,25,5,false,false);

		final IPAddressTextField ipAddressTextField=IPAddressTextFieldUtility.newInstance();

		constraints.add(ipAddressTextField.textField,30,5,25,5,false,false);

		final JButton button=new JButton("Traceroute");

		constraints.add(button,60,5,30,5,false,false);

		constraints.add(new JLabel("Output:"),5,15,30,5,false,false);

		final JTextArea outputArea=new JTextArea(5,5);

		final JPanel outputPanel=new JPanel(new BorderLayout());
		outputPanel.add(outputArea);

		constraints.add(new JScrollPane(outputPanel),10,25,80,65,true,true);

		final JButton closeButton=closeButton("Close",dialog);

		constraints.add(closeButton,70,90,20,10,false,false);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				outputArea.setText("");

				if (0==ipAddressTextField.textField.getText().length())
				{
					NetworkContext.errors(frame,"Cannot traceroute without an IP address");

					return;
				}

				final OptionIPAddress address=IPAddressUtility.valueOf(ipAddressTextField.textField.getText());

                                if (address.isEmpty())
                                {
                                    NetworkContext.errors(frame,"Malformed IP Address");
                                    return;
				}

				final TracerouteResults results=Traceroute.trace(network,computer, new DestIPAddress(address.get()),30);

				IOUtility.withPrintWriter(DocumentWriter.documentWriter(outputArea.getDocument()),new SideEffect<PrintWriter>()
				{
					public void run(final PrintWriter printWriter)
					{
						printWriter.println(results.toString());
					}
				});

				network.log.add("Tracerouted from "+PacketSourceUtility.asString(network, computer)+" to "+address+", "+results.size()+" results received.");
			}
		});

		return dialog;
	}
        }*/