package ipsim.network.connectivity.cable.outgoing;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import org.jetbrains.annotations.*;

public final class CableOutgoing implements OutgoingPacketListener
{
	public Function<Network, Network> packetOutgoing(final Packet packet, final PacketSource source)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				final Cable cable=asCable(source);

				if (cable==null)
					throw null;

				for (final PacketSource end : cable.getEnds())
					network.packetQueue.enqueueIncomingPacket(network, packet, source, end);

				return network;
			}
		};
	}

        }*/