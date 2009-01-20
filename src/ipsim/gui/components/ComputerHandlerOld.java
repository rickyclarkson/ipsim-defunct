package ipsim.gui.components;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import ipsim.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.awt.Point;
import ipsim.gui.*;
import static ipsim.gui.AddDefaultRouteDialogUtility.*;
import static ipsim.gui.components.ContextMenuUtility.*;
import static ipsim.gui.components.RoutingTableEntryEditDialog.*;
import static ipsim.lang.Comparators.fromFunction;
import ipsim.network.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.Computer$.*;
import ipsim.network.ip.*;
import static ipsim.swing.CustomJOptionPane.*;
import ipsim.traceroute.*;
import ipsim.util.Collections;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public final class ComputerHandler
{
	public static final ImageIcon icon=new ImageIcon(ComputerHandler.class.getResource("/images/computer.png"));
	public static final ImageIcon ispIcon=new ImageIcon(ComputerHandler.class.getResource("/images/isp.png"));

	public static SideEffect<Computer> renderRef(final Network network, final NetworkView view, final Graphics2D graphics)
	{
		return new SideEffect<Computer>()
		{
			public void run(final Computer computer)
			{
				render(network, view, computer, graphics);
			}
		};
	}

	public static void render(final Network network, final NetworkView view, final Computer computer, final Graphics2D graphics)
	{
		final Point position=getPosition(network, computer, 0);

		final Stream<PacketSource> cards=PositionUtility.children(network.getAll(), computer);

		final BasicStroke basicStroke=new BasicStroke(8);

		cards.foreach(new SideEffect<PacketSource>()
		{
			public void run(final PacketSource component2)
			{
				final Point position2=getPosition(network, component2, 0);

				graphics.setStroke(basicStroke);

				graphics.drawLine((int)position.x(), (int)position.y(), (int)position2.x(), (int)position2.y());
			}
		});

		final Image computerImage=computer.isISP ? ispIcon.getImage() : icon.getImage();

		final int imageWidth=computerImage.getWidth(null)/2;

		final int imageHeight=computerImage.getHeight(null)/2;

		graphics.drawImage(computerImage, (int)position.x()-imageWidth, (int)position.y()-imageHeight, view);
	}

	public static JPopupMenu createContextMenu(final JFrame frame, final Network network, final NetworkView view, final NetworkContext context, final Computer computer)
	{
		final JPopupMenu menu=new JPopupMenu();
		menu.add(item("Ping...", 'P', new Runnable()
		{
			public void run()
			{
				final PingDialog dialog=new PingDialog(frame, network, computer);
				dialog.jDialog.setVisible(true);
			}
		}));
		menu.add(item("Traceroute...", 'T', new Runnable()
		{
			public void run()
			{
				TracerouteDialog.newTracerouteDialog(frame, network, computer).setVisible(true);
			}
		}));

		final Predicate<Card> hasDeviceDrivers=new Predicate<Card>()
		{
			public boolean invoke(final Card card)
			{
				return card.withDrivers!=null;
			}
		};

		if (Collections.any(computer.getCards(network), hasDeviceDrivers))
			menu.addSeparator();

		final Function<CardDrivers, Integer> getEthNumber=new Function<CardDrivers, Integer>()
		{
			@NotNull
			public Integer run(@NotNull final CardDrivers card)
			{
				return card.ethNumber;
			}
		};

		final Stream<CardDrivers> cards=cardsWithDrivers(network, computer).only(new Predicate<CardDrivers>()
		{
			public boolean invoke(final CardDrivers cardDrivers)
			{
				return context.visibleComponents.get().contains(cardDrivers.card);
			}
		});

		final List<CardDrivers> cards2=sort2(cards, fromFunction(getEthNumber));

		for (final CardDrivers card : cards2)
		{
			final JMenu cardMenu=new JMenu("Card "+card.ethNumber);
			if (card.ethNumber<10)
				cardMenu.setMnemonic((char)('0'+card.ethNumber));

			cardMenu.add(item("Edit IP Address", 'E', editIPAddress(frame, network, computer, card)));
			cardMenu.add(item("Assign Automatic IP Address", 'A', assignAutomaticIPAddress(frame)));
			cardMenu.add(item("Remove IP Address", 'R', removeIPAddress(network, computer, card)));

			menu.add(cardMenu);
		}

		if (Collections.any(computer.getCards(network), hasDeviceDrivers))
			menu.addSeparator();

		menu.add(item("List IP Addresses", 'S', new Runnable()
		{
			public void run()
			{
				final String display=getIPAddresses(network, computer);

				network.log.add("Listed the IP addresses of "+PacketSourceUtility.asString(network, computer));

				if (display.length()==0)
					errors(frame, "No IP addresses to display");
				else
					JOptionPane.showMessageDialog(frame, display, "IP Addresses", JOptionPane.INFORMATION_MESSAGE);
			}
		}));

		menu.add(item("View/Edit Routing Table", 'R', new Runnable()
		{
			public void run()
			{
				RoutingTableDialogUtility.createRoutingTableDialog(frame, network, computer).getJDialog().setVisible(true);

				network.log.add("Listed the routing table of "+PacketSourceUtility.asString(network, computer));
			}
		}));

		menu.add(item("Add Explicit Route", 'X', new Runnable()
		{
			public void run()
			{
				final NetBlock zero=NetBlockUtility.zero();

				final RouteInfo entry=new RouteInfo(zero, IPAddressUtility.zero());

				final Maybe<Route> route=nothing();
				final Maybe<RoutingTableDialog> dialog=nothing();

				final JDialog dialog2=createRoutingTableEntryEditDialog(frame, network, computer, entry, route, dialog).getDialog();
				dialog2.setLocationRelativeTo(frame);

				dialog2.setVisible(true);
			}
		}));
		menu.add(item("Add Default Route", 'D', new Runnable()
		{
			public void run()
			{
				newInstance(frame, network, computer).setVisible(true);
			}
		}));

		final JRadioButtonMenuItem toggleIPForwarding=new JRadioButtonMenuItem("Toggle IP Forwarding");
		toggleIPForwarding.setMnemonic('G');

		toggleIPForwarding.setSelected(computer.ipForwardingEnabled);

		toggleIPForwarding.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				if (toggleIPForwarding.isSelected())
				{
					computer.ipForwardingEnabled=true;
					network.modified=true;

					network.log.add("Enabled IP forwarding on "+PacketSourceUtility.asString(network, computer)+'.');
				}
				else
				{
					computer.ipForwardingEnabled=false;
					network.modified=true;
					network.log.add("Disabled IP forwarding on "+PacketSourceUtility.asString(network, computer)+'.');
				}
			}
		});

		menu.add(toggleIPForwarding);

		menu.add(item("Packet Sniffer...", 'N', new Runnable()
		{
			public void run()
			{
				network.log.add("Started a packet sniffer on "+PacketSourceUtility.asString(network, computer)+'.');

				final PacketSniffer instance=PacketSnifferUtility.instance(frame, network, computer);

				computer.getIncomingPacketListeners().add(instance);
				computer.getOutgoingPacketListeners().add(instance);
			}
		}));

		menu.add(item("Show ARP Table...", 'A', new Runnable()
		{
			public void run()
			{
				final JTextArea area=new JTextArea(5, 20);
				String string=computer.arpTable.asString();
				if (string.trim().length()==0)
					string="No ARP records";

				area.setText(string);
				area.setEditable(false);
				area.setOpaque(false);

				JOptionPane.showMessageDialog(frame, area, "ARP Table for "+PacketSourceUtility.asString(network, computer), JOptionPane.INFORMATION_MESSAGE);
			}
		}));
		menu.add(item("Delete", 'L', new Runnable()
		{
			public void run()
			{
				if (JOptionPane.YES_OPTION==showYesNoCancelDialog(frame, "Really delete this computer?", "Delete?"))
				{
					network.log.add("Deleted "+PacketSourceUtility.asString(network, computer));
					computer.getCards(network).foreach(network.deleteCard);
					network.delete(computer);
					network.modified=true;
					view.repaint();
				}
			}
		}));

		return menu;
	}

	public static Runnable editIPAddress(final JFrame frame, final Network network, final Computer computer, final CardDrivers card)
	{
		return new Runnable()
		{
			public void run()
			{
				final String cardNumber=String.valueOf(card.ethNumber);

				EditIPAddressDialogFactory.newInstance(frame, network, computer, Integer.parseInt(cardNumber)).setVisible(true);
			}
		};
	}

	public static Runnable assignAutomaticIPAddress(final JFrame frame)
	{
		return new Runnable()
		{
			public void run()
			{
				errors(frame, "No DHCP server found");
			}
		};
	}

	public static Runnable removeIPAddress(final Network network, final Computer computer, final CardDrivers card)
	{
		return new Runnable()
		{
			public void run()
			{
				network.log.add("Removed the "+NetBlockUtility.asCustomString(CardUtility.getNetBlock(card))+" IP address from "+PacketSourceUtility.asString(network, computer));

				card.ipAddress.set(IPAddressUtility.zero());

				card.netMask.set(NetMaskUtility.zero());
				network.modified=true;
			}
		};
	}

	public static String getIPAddresses(Network network, final Computer computer)
	{
		final Iterable<CardDrivers> collection=getSortedCards(network, computer);

		final StringBuilder display=new StringBuilder(22);

		for (final CardDrivers cardWithDrivers : collection)
		{
			final IPAddress ipAddress=cardWithDrivers.ipAddress.get();
			final NetMask netMask=cardWithDrivers.netMask.get();

			if (!(0==ipAddress.rawValue()))
                            display.append("Card ").append(cardWithDrivers.ethNumber).append(", IP=").append(ipAddress.toString()).append(", netmask=").append(netMask.toString()).append('\n');
		}

		return display.toString();
	}
}
*/