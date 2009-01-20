package ipsim.network.connectivity.cable.incoming;
/*
import fpeas.function.*;
import fpeas.predicate.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

public class CableIncoming implements IncomingPacketListener
{
	public void packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination)
	{
		packetIncomingImpl(asNotNull(asEthernetPacket(packet)), source, asNotNull(asCable(destination)));
	}

	public static Function<Network, Network> packetIncomingImpl(@NotNull final EthernetPacket packet, @NotNull final PacketSource source, @NotNull final Cable destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				final boolean canTransferPackets=destination.canTransferPackets();

				if (!canTransferPackets)
					return network;

				final Iterable<PacketSource> ends=Collections.only(destination.getEnds(), PredicateUtility.not(equalT(source)));

				network.packetQueue.enqueueIncomingPacket(network,packet, destination, ends.iterator().next());

				return network;
			}
		};
	}

        }*/