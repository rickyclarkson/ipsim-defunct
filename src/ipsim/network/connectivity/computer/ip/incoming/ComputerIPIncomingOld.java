package ipsim.network.connectivity.computer.ip.incoming;
/*
import fpeas.function.*;
import static ipsim.Globals.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.route.RoutingTable$.*;
import ipsim.network.connectivity.icmp.ping.*;
import static ipsim.network.connectivity.icmp.ping.PingData.*;
import ipsim.network.connectivity.icmp.ttl.*;
import ipsim.network.connectivity.icmp.unreachable.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.Computer$.*;
import static ipsim.network.NetBlock$.*;
import static ipsim.network.IPAddress.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

public final class ComputerIPIncoming implements IncomingPacketListener
{
	public Function<Network, Network> packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				final IPPacket ipPacket=PacketUtility2.asIPPacket(packet);

				if (ipPacket==null)
					return network;

				@Nullable
				final Computer computer=asComputer(destination);

				@Nullable
				final Card card=asCard(source);

				if (handleBroadcastPing(network, computer, ipPacket))
					return network;

				if (card==null)
					return network;

				if (handlePing(network, computer, card.withDrivers, ipPacket))
					return network;

				handleForwarding(network, computer, card.withDrivers, ipPacket);

				return network;
			}
		};
	}

	private static boolean handleBroadcastPing(final Network network, final Computer computer, @NotNull final IPPacket ipPacket)
	{
		if (!ipPacket.data.equals(PingData.REQUEST))
			return false;

		final boolean[] result={false};

		for (final CardDrivers card : cardsWithDrivers(network, computer))
		{
			if (result[0])
				continue;

			final NetBlock netBlock=CardUtility.getNetBlock(card);

			final IPAddress broadcastAddress=netBlock.broadcastAddress();
			if (ipPacket.destinationIPAddress.ipAddress().equals(broadcastAddress))
			{
                            network.packetQueue.enqueueOutgoingPacket(network, new IPPacket(new SourceIPAddress(card.ipAddress.get()), ipPacket.sourceIPAddress.toDest(), DEFAULT_TIME_TO_LIVE, ipPacket.identifier, REPLY), computer);

				return true;
			}
		}

		return result[0];
	}

	private static void handleForwarding(final Network network, final Computer computer, final CardDrivers card, final IPPacket ipPacket)
	{
		if (!computer.ipForwardingEnabled)
			return;

		final DestIPAddress destIP=ipPacket.destinationIPAddress;

		if (card.ipAddress.get().equals(destIP.ipAddress()))
			return;

		for (final CardDrivers innerCard : cardsWithDrivers(network, computer))
		{
                    final IPAddress broadcastAddress=CardUtility.getNetBlock(innerCard).broadcastAddress();

			if (ipPacket.destinationIPAddress.ipAddress().equals(broadcastAddress))
			{
				handleBroadcastPing(network, computer, ipPacket);
				return;
			}
		}

		final boolean hasRoute=hasRouteFor(network, computer, destIP);

		final IPPacket newIPPacket;

		if (hasRoute)
		{
			if (ipPacket.timeToLive<2)
				newIPPacket=dropPacket(card, ipPacket, TimeExceededData.TIME_TO_LIVE_EXCEEDED);
			else
				newIPPacket=forwardPacket(ipPacket);

			network.packetQueue.enqueueOutgoingPacket(network, newIPPacket, computer);
		}
		else
		{
			newIPPacket=dropPacket(card, ipPacket, UnreachableData.NET_UNREACHABLE);

			network.packetQueue.enqueueOutgoingPacket(network, newIPPacket, computer);
		}
	}

	private static IPPacket dropPacket(final CardDrivers card, final IPPacket ipPacket, final Object reason)
	{
		return new IPPacket(new SourceIPAddress(card.ipAddress.get()), new DestIPAddress(ipPacket.sourceIPAddress.ipAddress()), DEFAULT_TIME_TO_LIVE, ipPacket.identifier, reason);
	}

	private static IPPacket forwardPacket(final IPPacket ipPacket)
	{
		return new IPPacket(ipPacket.sourceIPAddress, ipPacket.destinationIPAddress, ipPacket.timeToLive-1, ipPacket.identifier, ipPacket.data);
	}

	private static boolean handlePing(final Network network, final Computer computer, @NotNull final CardDrivers card, final IPPacket ipPacket)
	{
		if (!ipPacket.data.equals(PingData.REQUEST))
			return false;

		final Stream<CardDrivers> cards;

		if (computer.ipForwardingEnabled)
			cards=cardsWithDrivers(network, computer);
		else
			cards=Stream.one(card);

		for (final CardDrivers innerCard : cards)
		{
			if (innerCard.ipAddress.get().equals(ipPacket.destinationIPAddress.ipAddress()))
			{
                            final IPPacket reply=new IPPacket(new SourceIPAddress(innerCard.ipAddress.get()), ipPacket.sourceIPAddress.toDest(), DEFAULT_TIME_TO_LIVE, ipPacket.identifier, REPLY);

				network.packetQueue.enqueueOutgoingPacket(network, reply, computer);

				return true;
			}
		}

		return false;
	}
}*/