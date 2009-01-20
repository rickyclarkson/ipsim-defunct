package ipsim.network.connectivity.card.incoming;
/*
import fpeas.function.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ethernet.*;
import org.jetbrains.annotations.*;

public final class CardIncoming implements IncomingPacketListener
{
	public Function<Network,Network> packetIncoming(final Packet packet,final PacketSource source,final PacketSource destination)
	{
		return packetIncomingImpl(asNotNull(asEthernetPacket(packet)), asNotNull(asCard(destination)));
	}

	public static Function<Network,Network> packetIncomingImpl(@NotNull final EthernetPacket packet, @NotNull final Card destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				if (destination.withDrivers!=null)
					network.packetQueue.enqueueIncomingPacket(network,packet,destination, asNotNull(destination.positionData(0).parent));

				return network;
			}
		};
	}
        }*/