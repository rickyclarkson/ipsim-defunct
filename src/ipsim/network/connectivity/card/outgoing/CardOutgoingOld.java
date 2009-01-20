package ipsim.network.connectivity.card.outgoing;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import org.jetbrains.annotations.*;

public final class CardOutgoing implements OutgoingPacketListener
{
	public Function<Network, Network> packetOutgoing(final Packet packet, final PacketSource source)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				if (!isEthernetPacket(packet))
					return network;

				final Card card;
				final Cable cable;

				if (!isEthernetPacket(packet) || (card=asCard(source))==null || (cable=card.getCable(network))==null)
					return network;

				network.packetQueue.enqueueIncomingPacket(network, packet, card, cable);
				return network;
			}
		};
	}

	@Override
	public String toString()
	{
		return "CardOutgoing";
	}
        }*/