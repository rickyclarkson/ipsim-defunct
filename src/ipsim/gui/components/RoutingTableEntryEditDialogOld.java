package ipsim.gui.components;
/*
import static anylayout.AnyLayout.*;
import static anylayout.extras.ConstraintUtility.*;
import anylayout.extras.*;
import fpeas.maybe.*;
import fpeas.sideeffect.*;
import static ipsim.lang.Runnables.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetBlock$.*;
import ipsim.network.ip.*;
import static ipsim.swing.Buttons.*;
import static ipsim.swing.Dialogs.*;
import ipsim.swing.*;
import ipsim.network.route.*;
import javax.swing.*;
import java.awt.event.*;
import ipsim.lang.RunnablesInteropFailure;

public final class RoutingTableEntryEditDialog
{
	public static RouteEditDialog createRoutingTableEntryEditDialog(final JFrame frame,final Network network,final Computer computer,final RouteInfo entry,final Maybe<Route> maybeRealRoute,final Maybe<RoutingTableDialog> maybeParent)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose("Edit Route",frame);

		dialog.setSize(400,200);
		dialog.setTitle("Edit Route");

		return createRouteEditor(dialog, network,computer,entry,maybeRealRoute,maybeParent);
	}

	public static RouteEditDialog createRouteEditor(final JDialog dialog,final Network network,final Computer computer,final RouteInfo entry,final Maybe<Route> maybeRealRoute,final Maybe<RoutingTableDialog> maybeParent)
	{
		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(dialog.getContentPane());
		useAnyLayout(dialog.getContentPane(),0.5f,0.5f,SizeCalculatorUtility.absoluteSize(400,200),typicalDefaultConstraint(RunnablesInteropFailure.throwRuntimeException));

		constraints.add(new JLabel("Destination Network"),5,5,45,10,false,false);

		final IPAddressTextField networkNumberTextField=IPAddressTextFieldUtility.newInstance();

		networkNumberTextField.setIPAddress(entry.destination.networkNumber());

		constraints.add(networkNumberTextField.textField,50,5,45,10,false,false);

		constraints.add(new JLabel("Destination Subnet Mask"),5,20,45,10,false,false);

		final SubnetMaskTextField subnetMaskTextField=new SubnetMaskTextField();

		subnetMaskTextField.setNetMask(entry.destination.netMask());

		constraints.add(subnetMaskTextField,50,20,30,10,false,false);
		constraints.add(new JLabel("Gateway"),5,40,45,10,false,false);

		final IPAddressTextField ipAddress=IPAddressTextFieldUtility.newInstance();

		ipAddress.setIPAddress(entry.gateway);
		constraints.add(ipAddress.textField,50,40,30,10,false,false);

		final JButton okButton=new JButton("OK");

		final RouteInfo entry1=new RouteInfo(entry.destination, entry.gateway);
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				final IPAddress networkNumber=networkNumberTextField.getIPAddress();

				final NetMask netMask=subnetMaskTextField.getNetMask();

				final RouteInfo newEntry=new RouteInfo(new NetBlock(networkNumber, netMask), ipAddress.getIPAddress());

				final Route realEntry=new Route(newEntry.destination, newEntry.gateway);

				MaybeUtility.run(maybeRealRoute,new SideEffect<Route>()
				{
					public void run(final Route route)
					{
                                            final RouteInfo previous=new RouteInfo(route.netBlock(), route.gateway());

						computer.routingTable().replace(route,new Route(newEntry.destination,newEntry.gateway));
						network.log_$eq(network.log().$plus("Changed a route ("+previous.asString()+" to "+newEntry.asString()+" on "+PacketSourceUtility.asString(network, computer)));
					}
				},new Runnable()
				{
					public void run()
					{
                                            computer.routingTable().add(network,MaybeUtility.just(computer),realEntry);
						network.log.add("Added an explicit route to "+PacketSourceUtility.asString(network, computer)+" to get to the "+asCustomString(entry1.destination)+" network, via the "+entry1.gateway+" gateway.");
					}
				});

				MaybeUtility.run(maybeParent, RoutingTableDialogUtility.populateElements);

				dialog.setVisible(false);
				dialog.dispose();
			}
		});

		constraints.add(okButton,20,80,20,15,false,false);
		constraints.add(closeButton("Cancel",dialog),60,80,20,15,false,false);

		return new RouteEditDialog()
		{
			public JDialog getDialog()
			{
				return dialog;
			}

		};
	}
}*/