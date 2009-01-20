package ipsim.network.connectivity.hub.outgoing;
/*
import fpeas.function.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.hub.*;
import org.jetbrains.annotations.*;

public class HubOutgoing implements OutgoingPacketListener
{
	public Function<Network,Network> packetOutgoing(final Packet packet,final PacketSource source)
	{
		return packetOutgoingImpl(asNotNull(asEthernetPacket(packet)), asNotNull(asHub(source)));
	}

	private static Function<Network,Network> packetOutgoingImpl(@NotNull final EthernetPacket packet, @NotNull final Hub source)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				for (final Cable cable: source.getCables(network))
					network.packetQueue.enqueueIncomingPacket(network,packet,source,cable);

				return network;
			}
		};
	}

        }*/