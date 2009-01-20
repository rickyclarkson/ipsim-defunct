package ipsim.network.connectivity.computer.ip.outgoing;
/*
import fpeas.function.*;
import static ipsim.Caster.*;
import ipsim.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.route.RoutingTable$.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.icmp.unreachable.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.Computer$.*;
import ipsim.network.ip.*;
import org.jetbrains.annotations.*;

public final class ComputerIPOutgoing implements OutgoingPacketListener
{
	public Function<Network, Network> packetOutgoing(final Packet packet, final PacketSource source)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{

				@NotNull
				final Computer computer=asNotNull(asComputer(source));

				final IPPacket ipPacket=asIPPacket(packet);

				if (ipPacket==null)
					return network;

				final DestIPAddress destinationIPAddress=ipPacket.destinationIPAddress;

				final SourceIPAddress sourceIPAddress=ipPacket.sourceIPAddress;

				final boolean hasRouteFor=hasRouteFor(network,computer, destinationIPAddress);

				if (!hasRouteFor)
				{
					dropPacket(network,computer, destinationIPAddress, sourceIPAddress);
					return network;
				}

				final Route route=asNotNull(getRouteFor(network, computer, destinationIPAddress));


				@Nullable final CardDrivers card=getCardFor(network,computer, route);

				if (card==null)
				{
					dropPacket(network,computer, destinationIPAddress, sourceIPAddress);

					return network;
				}

				final IPAddress gatewayIP;

				if (route.gateway.equals(card.ipAddress.get()))
					gatewayIP=destinationIPAddress.ipAddress();
				else
					gatewayIP=route.gateway;

				final PacketQueue queue=network.packetQueue;

				final int rawDestIP=destinationIPAddress.ipAddress().rawValue();
				if (card.ipAddress.get().equals(destinationIPAddress.ipAddress()))
				{
					queue.enqueueIncomingPacket(network, packet, card.card, computer);
					return network;
				}

				if (CardUtility.getBroadcastAddress(card).rawValue()==rawDestIP)
				{
					queue.enqueueIncomingPacket(network, packet, card.card, computer);
					queue.enqueueOutgoingPacket(network,new EthernetPacket(card.card.getMacAddress(network), new MacAddress(0), ipPacket), card.card);

					return network;
				}

				@Nullable
				final MacAddress arpMac=computer.arpTable.getMacAddress(gatewayIP);

				if (arpMac==null)
				{
					final Object object=new Object();

					final IncomingPacketListener continueListener=new ContinueArpPacketListener(ipPacket, object);

					computer.getIncomingPacketListeners().add(continueListener);

					queue.addEmptyQueueListener(new ContinueRemover(continueListener, computer));

					queue.addEmptyQueueListener(new Runnable()
					{
						public void run()
						{
							if (computer.arpTable.getMacAddress(gatewayIP)==null)
								queue.enqueueOutgoingPacket(network,new IPPacket(new SourceIPAddress(asNotNull(card).ipAddress.get()), new DestIPAddress(ipPacket.sourceIPAddress.ipAddress()), Globals.DEFAULT_TIME_TO_LIVE, ipPacket.identifier, UnreachableData.HOST_UNREACHABLE), computer);
						}
					});

					if (!computer.arpTable.hasEntryFor(gatewayIP))
						queue.enqueueOutgoingPacket(network,new ArpPacket(gatewayIP, new MacAddress(0), card.ipAddress.get(), card.card.getMacAddress(network), object), computer);
				}
				else
				{
					queue.enqueueOutgoingPacket(network,new EthernetPacket(card.card.getMacAddress(network), arpMac, ipPacket), computer);
				}

				return network;
			}
		};
	}

	private static void dropPacket(final Network network,final Computer computer, final DestIPAddress destinationIPAddress, final SourceIPAddress sourceIPAddress)
	{
            final IPPacket dropPacket=new IPPacket(destinationIPAddress.toSource(), sourceIPAddress.toDest(), Globals.DEFAULT_TIME_TO_LIVE, new Object(), UnreachableData.NET_UNREACHABLE);

		network.packetQueue.enqueueIncomingPacket(network, dropPacket, computer, computer);
	}

}*/