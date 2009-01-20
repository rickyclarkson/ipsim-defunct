package ipsim.connectivity.computer.arp.outgoing;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import fpeas.lazy.Lazy;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.ip.*;
import org.jetbrains.annotations.*;

public class ComputerArpOutgoingTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network(null);
		final Computer computer=new Computer(PositionOrParent.position(new Point(0,0))).withID(network.generateComputerID());
		final PacketQueue queue=network.packetQueue;

		final StringBuilder answer=new StringBuilder();

		computer.getOutgoingPacketListeners().add(new OutgoingPacketListener()
		{
			public Function<Network, Network> packetOutgoing(final Packet packet, final PacketSource source)
			{
				return new Function<Network, Network>()
				{
					@NotNull
					public Network run(@NotNull final Network network)
					{
						if (PacketUtility2.isEthernetPacket(packet) && PacketSourceUtility.isComputer(source))
						{
							final EthernetPacket ethPacket=asNotNull(PacketUtility2.asEthernetPacket(packet));

							if (PacketUtility2.isArpPacket(ethPacket.data))
								answer.append("Pass");
						}

						return network;
					}
				};
			}
		});

		final ArpPacket arpPacket=new ArpPacket(new IPAddress(0), new MacAddress(5), new IPAddress(0), new MacAddress(10), new Object());

		queue.enqueueOutgoingPacket(network,arpPacket, computer);

		queue.processAll();

		return "Pass".equals(answer.toString());
	}

	@Override
	public String toString()
	{
		return "ComputerArpOutgoingTest";
	}
}
*/