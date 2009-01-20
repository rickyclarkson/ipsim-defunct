package ipsim.network.connectivity.computer.arp.incoming;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.ethernet.*;
import org.jetbrains.annotations.*;

public final class ComputerArpIncoming implements IncomingPacketListener
{
	public Function<Network, Network> packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				@Nullable
				final ArpPacket arpPacket=asArpPacket(packet);

				@Nullable
				final Computer computer=PacketSourceUtility.asComputer(destination);

				@Nullable
				final Card card=PacketSourceUtility.asCard(source);

				if (card==null || computer==null || arpPacket==null || card.withDrivers==null)
					return network;

				if (CardUtility.isOnSameSubnet(card.withDrivers, arpPacket.sourceIPAddress))
					computer.arpTable.put(arpPacket.sourceIPAddress, arpPacket.sourceMacAddress, network);

				if (0==arpPacket.destinationMacAddress.rawValue)
					for (final CardDrivers card2 : ComputerUtility.cardsWithDrivers(network,computer))
                                            if (card2.ipAddress.get().rawValue()==arpPacket.destinationIPAddress.rawValue())
							network.packetQueue.enqueueOutgoingPacket(network,new ArpPacket(arpPacket.sourceIPAddress, arpPacket.sourceMacAddress, card2.ipAddress.get(), card2.card.getMacAddress(network), arpPacket.id), computer);

				return network;
			}
		};
	}

        }*/