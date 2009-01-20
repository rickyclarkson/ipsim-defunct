package ipsim.network.connectivity;
/*
import ipsim.network.connectivity.arp.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ip.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import ipsim.network.IPAddressUtility;

public final class PacketUtility2
{
	public static boolean isIPPacket(final Packet packet)
	{
		return asIPPacket(packet)!=null;
	}

	@Nullable
	public static IPPacket asIPPacket(final Packet packet)
	{
		return packet.accept(new PacketVisitor2<IPPacket>()
		{
			public IPPacket visit(final IPPacket packet1)
			{
				return packet1;
			}

			public IPPacket visit(final ArpPacket packet1)
			{
				return null;
			}

			public IPPacket visit(final EthernetPacket packet1)
			{
				return null;
			}
		});
	}

	public static boolean isEthernetPacket(final Packet packet)
	{
		return asEthernetPacket(packet)!=null;
	}

	@Nullable
	public static EthernetPacket asEthernetPacket(final Packet packet)
	{
		return packet.accept(new PacketVisitor2<EthernetPacket>()
		{
			public EthernetPacket visit(final IPPacket packet1)
			{
				return null;
			}

			public EthernetPacket visit(final ArpPacket packet1)
			{
				return null;
			}

			public EthernetPacket visit(final EthernetPacket packet1)
			{
				return packet1;
			}
		});
	}

	@Nullable
	public static ArpPacket asArpPacket(final Packet packet)
	{
		return packet.accept(new PacketVisitor2<ArpPacket>()
		{
			public ArpPacket visit(final IPPacket packet1)
			{
				return null;
			}

			public ArpPacket visit(final ArpPacket packet1)
			{
				return packet1;
			}

			public ArpPacket visit(final EthernetPacket packet1)
			{
				return null;
			}
		});
	}

	public static boolean isArpPacket(final Packet packet)
	{
		return asArpPacket(packet)!=null;
	}

	public static String asString(final Packet genPacket)
	{
		return genPacket.accept(new PacketVisitor2<String>()
		{
			public String visit(final IPPacket packet)
			{
                            return Printf.sprintf("IP packet from %s to %s, containing data: %s", packet.sourceIPAddress.ipAddress().toString(),packet.destinationIPAddress.ipAddress().toString(), packet.data.toString());
			}

			public String visit(final ArpPacket packet)
			{
				final boolean request=ArpPacket.isRequest(packet);

				return Printf.sprintf("ArpPacket[%s, %s, %s (%d) to %s (%d)]", packet.id.hashCode(),request ? "REQUEST" : "REPLY", packet.sourceIPAddress.toString(), packet.sourceMacAddress.rawValue,packet.destinationIPAddress.toString(), packet.destinationMacAddress.rawValue);
			}

			public String visit(final EthernetPacket packet)
			{
				return "EthernetPacket["+packet.sourceAddress.rawValue+"->"+packet.destinationAddress.rawValue+", containing "+asString(packet.data)+']';
			}
		});
	}
        }*/