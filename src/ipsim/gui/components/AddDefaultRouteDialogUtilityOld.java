package ipsim.gui.components;
/*
import static anylayout.AnyLayout.*;
import anylayout.extras.*;
import static fpeas.maybe.MaybeUtility.*;
import static ipsim.network.NetworkContext.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.Computer$.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import static ipsim.swing.Buttons.*;
import static ipsim.swing.Dialogs.*;
import ipsim.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddDefaultRouteDialogUtility
{
	public static JDialog newInstance(final JFrame frame,final Network network,final Computer computer)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose( "Add a Default Route",frame);

		dialog.setSize(400, 200);

		ComponentUtility.centreOnParent(dialog, frame);

		final Container pane=dialog.getContentPane();

		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(pane);
		useAnyLayout(pane, 0.5f, 0.5f, constraints.getSizeCalculator(), ConstraintUtility.typicalDefaultConstraint(RunnablesInteropFailure.throwRuntimeException));

		constraints.add(new JLabel("IP Address"), 10, 10, 25, 15, false, false);

		final IPAddressTextField ipAddressTextField=IPAddressTextFieldUtility.newInstance();

		constraints.add(ipAddressTextField.textField, 40, 10, 25, 15, false, false);
		final JButton okButton=new JButton("OK");
		constraints.add(okButton, 10, 70, 15, 15, false, false);

		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				final NetBlock zero=NetBlock$.MODULE$.zero();

				final IPAddress ipAddress=ipAddressTextField.getIPAddress();

				if (computer.isLocallyReachable(ipAddress, network))
				{
					final Route route=new Route(zero, ipAddress);

					computer.routingTable().add(just(computer), route);

					network.log.add("Added a default route to "+PacketSourceUtility.asString(network, computer)+" of "+ipAddress+'.');

					network.modified=true;

					dialog.setVisible(false);
					dialog.dispose();
				}
				else
				{
					errors(frame,"Gateway unreachable");
					dialog.requestFocus();
				}
			}
		});

		final JButton cancelButton=closeButton("Cancel", dialog);
		constraints.add(cancelButton, 70, 70, 25, 15, false, false);

		return dialog;
	}
        }*/