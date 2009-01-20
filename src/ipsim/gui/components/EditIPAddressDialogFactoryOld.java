package ipsim.gui.components;
/*
import static anylayout.AnyLayout.*;
import static anylayout.extras.ConstraintUtility.*;
import anylayout.extras.*;
import fpeas.sideeffect.*;
import ipsim.awt.*;
import static ipsim.lang.Runnables.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.Computer$.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import static ipsim.swing.Buttons.*;
import static ipsim.swing.Dialogs.*;
import ipsim.swing.*;
import static ipsim.lang.RunnablesInteropFailure.throwRuntimeException;
import javax.swing.*;
import java.awt.event.*;

public class EditIPAddressDialogFactory
{
	public static JDialog newInstance(final JFrame frame,final Network network,final Computer computer, final int ethNo)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose("Edit IP Address", frame);

		dialog.setSize(400, 220);
		ComponentUtility.centreOnParent(dialog, frame);
		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(dialog.getContentPane());
		useAnyLayout(dialog.getContentPane(), 0.5f, 0.5f, constraints.getSizeCalculator(), typicalDefaultConstraint(throwRuntimeException));

		constraints.add(new JLabel("IP Address"), 10, 5, 25, 15, false, false);

		final IPAddressTextField ipAddressTextField=IPAddressTextFieldUtility.newInstance();

		final CardDrivers card=cardWithEth(network, ethNo);

		ipAddressTextField.setIPAddress(card.ipAddress.get());

		constraints.add(ipAddressTextField.textField, 40, 5, 25, 15, false, false);

		constraints.add(new JLabel("Subnet Mask"), 10, 45, 25, 15, false, false);

		final SubnetMaskTextField subnetMaskTextField=new SubnetMaskTextField();

		final CardDrivers card2=getEth(network,computer, ethNo);

		subnetMaskTextField.setNetMask(card2.netMask.get());

		constraints.add(subnetMaskTextField, 40, 45, 25, 15, false, false);

		final JButton okButton=new JButton("Ok");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				final CardDrivers cardWithDrivers=getEth(network,computer, ethNo);

				final IPAddress before=cardWithDrivers.ipAddress.get();
				final NetMask beforeNetMask=cardWithDrivers.netMask.get();

				final String cardBefore=PacketSourceUtility.asString(network, cardWithDrivers.card);
				cardWithDrivers.ipAddress.set(ipAddressTextField.getIPAddress());
				cardWithDrivers.netMask.set(NetMaskUtility.valueOf(subnetMaskTextField.getText()).get());

				final String after=cardWithDrivers.ipAddress.get().toString();
				final String afterNetmask=cardWithDrivers.netMask.get().toString();

				network.log.add(0==before.rawValue() ? "Assigned IP address "+after+" and subnet mask "+afterNetmask+" to "+cardBefore+'.' : "Changed the IP address of "+PacketSourceUtility.asString(network, cardWithDrivers.card)+" from "+before+" to "+after+" and the netmask from "+beforeNetMask+" to "+afterNetmask+'.');

				computer.routingTable.routes().foreach(new SideEffect<Route>()
				{
					public void run(final Route route)
					{
						if (!ComputerUtility.isLocallyReachable(network,computer,route.gateway))
							computer.routingTable.remove(route);
					}
				});

				network.modified=true;
				dialog.setVisible(false);
				dialog.dispose();
			}
		});

		constraints.add(okButton, 10, 80, 25, 15, false, false);
		constraints.add(closeButton("Cancel", dialog), 60, 80, 25, 15, false, false);
		return dialog;
	}
        }*/