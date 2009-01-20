package ipsim.network.connectivity.computer.ethernet.incoming;
/*
import fpeas.function.*;
import ipsim.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import org.jetbrains.annotations.*;

public final class ComputerEthernetIncoming implements IncomingPacketListener
{
	public Function<Network, Network> packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				@Nullable final Card maybeCard=PacketSourceUtility.asCard(source);

				if (maybeCard==null)
					return network;

				@Nullable final CardDrivers card=maybeCard.withDrivers;

				@Nullable final EthernetPacket maybePacket=PacketUtility2.asEthernetPacket(packet);

				return card==null || maybePacket==null ? network : packetIncomingImpl(network, card, maybePacket, destination);
			}
		};

	}

	public static Network packetIncomingImpl(@NotNull final Network network,@NotNull final CardDrivers card, @NotNull final EthernetPacket ethPacket, @NotNull final PacketSource destination)
	{
		final MacAddress zero=new MacAddress(0);

		final MacAddress destinationAddress=ethPacket.destinationAddress;

		if ((destinationAddress.equals(card.card.getMacAddress(network)) || destinationAddress.equals(zero)) && PacketUtility2.isArpPacket(ethPacket.data) && !ethPacket.sourceAddress.equals(card.card.getMacAddress(network)))
		{
			final ArpPacket arpPacket=Caster.asNotNull(PacketUtility2.asArpPacket(ethPacket.data));

			network.packetQueue.enqueueIncomingPacket(network,arpPacket, card.card, destination);
		}

		if (destinationAddress.equals(card.card.getMacAddress(network)) && isIPPacket(ethPacket.data))
		{
			final IPPacket ipPacket=asNotNull(asIPPacket(ethPacket.data));
			network.packetQueue.enqueueIncomingPacket(network, ipPacket, card.card, destination);
		}

		if (destinationAddress.equals(zero) && isIPPacket(ethPacket.data))
		{
			final IPPacket ipPacket=asNotNull(asIPPacket(ethPacket.data));

			if (CardUtility.getBroadcastAddress(card).equals(ipPacket.destinationIPAddress.ipAddress()))
				network.packetQueue.enqueueIncomingPacket(network, ipPacket, card.card, destination);
		}

		return network;
	}

        }*/