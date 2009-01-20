package ipsim.network.connectivity.hub.incoming;
/*
import fpeas.function.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.hub.*;
import ipsim.util.Collections;
import org.jetbrains.annotations.*;

import java.util.*;

public class HubIncoming implements IncomingPacketListener
{
	public Function<Network,Network> packetIncoming
	(
		final Packet packet,
		final PacketSource source,
		final PacketSource destination
	)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				return packetIncomingImpl(network,asNotNull(asEthernetPacket(packet)),asNotNull(asCable(source)),asNotNull(asHub(destination)));
			}
		};
	}

	private static Network packetIncomingImpl
		(@NotNull final Network network,@NotNull final EthernetPacket packet, @NotNull final Cable source, @NotNull final Hub hub)
	{
		if (!hub.isPowerOn())
			return network;

		final List<Cable> cables=Collections.arrayList();

		cables.addAll(hub.getCables(network));

		cables.remove(source);

		for (final Cable cable: cables)
			network.packetQueue.enqueueIncomingPacket(network,packet,hub,cable);

		return network;
	}

        }*/