package ipsim.network.connectivity.computer.arp.outgoing;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ethernet.*;
import org.jetbrains.annotations.*;

public final class ComputerArpOutgoing implements OutgoingPacketListener
{
	public Function<Network, Network> packetOutgoing(final Packet packet, final PacketSource source)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				final ArpPacket arpPacket=PacketUtility2.asArpPacket(packet);
				final Computer computer=PacketSourceUtility.asComputer(source);

				if (computer==null || arpPacket==null)
					return network;

				if (ArpPacket.isRequest(arpPacket) && !computer.arpTable.hasEntryFor(arpPacket.destinationIPAddress))
						computer.arpTable.putIncomplete(arpPacket.destinationIPAddress, network);

				final EthernetPacket ethPacket=new EthernetPacket(arpPacket.sourceMacAddress, arpPacket.destinationMacAddress, arpPacket);

				network.packetQueue.enqueueOutgoingPacket(network, ethPacket, source);
				return network;
			}
		};
	}

        }*/