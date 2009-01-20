package ipsim.network.connectivity.computer.ethernet.outgoing;
/*
import fpeas.function.*;
import ipsim.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ethernet.*;
import org.jetbrains.annotations.*;

public final class ComputerEthernetOutgoing implements OutgoingPacketListener
{
	public Function<Network, Network> packetOutgoing(final Packet packet, final PacketSource source)
	{
		final EthernetPacket e=asEthernetPacket(packet);
		final Computer c=asComputer(source);

		return e==null || c==null ? FunctionUtility.<Network>identity() : packetOutgoingImpl(e, c);
	}

	private static Function<Network, Network> packetOutgoingImpl(@NotNull final EthernetPacket packet, @NotNull final Computer computer)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{

				final boolean[] sane={true};

				for (final Card card : computer.getCards(network))
				{
					if (Caster.equalT(card.getMacAddress(network)).invoke(packet.sourceAddress))
						if (sane[0])
						{
							network.packetQueue.enqueueOutgoingPacket(network,packet, card);

							sane[0]=false;
						}
						else
							throw new RuntimeException();
				}

				return network;
			}
		};
	}

	@Override
	public String toString()
	{
		return "ComputerEthernetOutgoing";
	}

        }*/