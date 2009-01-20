package ipsim.network.connectivity.arp;
/*
import ipsim.network.IPAddress;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.ip.*;

public class ArpPacket implements Packet
{
	public final IPAddress destinationIPAddress;
	public final MacAddress destinationMacAddress;
	public final IPAddress sourceIPAddress;
	public final MacAddress sourceMacAddress;
	public final Object id;

	public ArpPacket(final IPAddress destinationIPAddress, final MacAddress destinationMacAddress, final IPAddress sourceIPAddress, final MacAddress sourceMacAddress, final Object id)
	{
		this.destinationIPAddress=destinationIPAddress;
		this.destinationMacAddress=destinationMacAddress;
		this.sourceIPAddress=sourceIPAddress;
		this.sourceMacAddress=sourceMacAddress;
		this.id=id;
	}

	public <R> R accept(final PacketVisitor2<R> visitor)
	{
		return visitor.visit(this);
	}

	public static boolean isRequest(final ArpPacket arpPacket)
	{
		return 0==arpPacket.destinationMacAddress.rawValue;
	}
        }*/