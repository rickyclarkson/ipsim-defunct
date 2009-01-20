package ipsim.network.connectivity.computer.ip.outgoing;
/*
import fpeas.function.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.ethernet.*;
import org.jetbrains.annotations.*;

public final class ContinueArpPacketListener implements IncomingPacketListener
{
	private final Packet originalPacket;

	private boolean dead=false;

	private final Object requestId;

	public ContinueArpPacketListener(final Packet originalPacket, final Object requestId)
	{
		this.originalPacket=originalPacket;
		this.requestId=requestId;
	}

	public Function<Network, Network> packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				if (dead)
					return network;

				if (!PacketUtility2.isArpPacket(packet))
					return network;

				@Nullable
				final ArpPacket arpPacket=asNotNull(asArpPacket(packet));

				if (!arpPacket.id.equals(requestId))
					return network;

				if (arpPacket.destinationMacAddress.equals(new MacAddress(0)))
					return network;

				final PacketQueue queue=network.packetQueue;
				destination.getIncomingPacketListeners().remove(ContinueArpPacketListener.this);

				queue.enqueueOutgoingPacket(network,originalPacket, destination);
				dead=true;

				return network;
			}
		};

	}

        }*/