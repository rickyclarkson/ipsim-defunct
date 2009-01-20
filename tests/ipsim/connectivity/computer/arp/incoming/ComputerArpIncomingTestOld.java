package ipsim.connectivity.computer.arp.incoming;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import fpeas.lazy.Lazy;
import static ipsim.Caster.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.ip.*;
import org.jetbrains.annotations.*;

public class ComputerArpIncomingTest implements Lazy<Boolean>
{
	/**
	 * Tests that when the Computer receives an ARP request (destination MAC address is zero), it sends an ARP reply.
	 * /
	public Boolean invoke()
	{
		final Network network=new Network(null);

		NetworkContext context=new NetworkContext();
		network.contexts.append(context);

		Point point=new Point(0, 0);
		final Card card=context.visibleComponents.prependIfNotPresent(new Card(false, position(point)));
		final Computer computer=context.visibleComponents.prependIfNotPresent(newComputerWithID(network.generateComputerID(),0,0));

		card.setPositionData(0, asNotNull(parent(computer)));

		card.installDeviceDrivers(network);

		final CardDrivers cardDrivers=card.withDrivers;

		cardDrivers.ipAddress.set(new IPAddress(10));

		final PacketQueue queue=network.packetQueue;

		final StringBuilder answer=new StringBuilder();

		computer.getOutgoingPacketListeners().add(new OutgoingPacketListener()
		{
			public Function<Network,Network> packetOutgoing(final Packet packet, final PacketSource source)
			{
				return new Function<Network, Network>()
				{
					@NotNull
					public Network run(@NotNull final Network network)
					{
						if (PacketUtility2.isArpPacket(packet) && PacketSourceUtility.isComputer(source))
							answer.append("Pass");

						return network;
					}
				};
			}

		});

		final ArpPacket packet=new ArpPacket(new IPAddress(10), new MacAddress(0), new IPAddress(5), new MacAddress(6), new Object());
		queue.enqueueIncomingPacket(network,packet, card, computer);

		queue.processAll();

		return "Pass".equals(answer.toString());
	}

	@Override
	public String toString()
	{
		return "ComputerArpIncomingTest";
	}
}
*/