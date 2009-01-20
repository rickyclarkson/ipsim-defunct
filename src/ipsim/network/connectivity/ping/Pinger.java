package ipsim.network.connectivity.ping;

import static ipsim.Caster.asNotNull;
import ipsim.*;
import static ipsim.lang.Assertion.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import ipsim.network.connectivity.icmp.ping.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.connectivity.ping.PingResultsUtility.*;
import static ipsim.network.ethernet.ComputerUtility.*;
import ipsim.network.ip.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import static java.util.Collections.*;
import java.util.*;

public final class Pinger
{
	public static List<PingResults> ping(final Network network,final Computer computer,final DestIPAddress ipAddress,final int ttl)
	{
		final boolean hasRoute=hasRouteFor(network,computer,ipAddress);

		final Object identifier=new Object();

		final PingListener pingListener=new PingListener(identifier);

		computer.getIncomingPacketListeners().add(pingListener);

		try
		{
			@Nullable
			final IPAddress ipAddress1=getTheFirstIPAddressYouCanFind(network,computer);

			if (ipAddress1==null)
                            return singletonList(netUnreachable(new SourceIPAddress(IPAddressUtility.valueOf("127.0.0.1").get())));

			final SourceIPAddress aRandomSourceIP=new SourceIPAddress(ipAddress1);

			if (!hasRoute)
				return singletonList(netUnreachable(aRandomSourceIP));

			final Route route=asNotNull(getRouteFor(network, computer, ipAddress));

			@Nullable
			final CardDrivers card=getCardFor(network,computer,route);

			if (card==null)
				return singletonList(netUnreachable(aRandomSourceIP));

			assertFalse(0==card.ipAddress.get().rawValue());

			final IPPacket packet=new IPPacket(new SourceIPAddress(card.ipAddress.get()), ipAddress, ttl, identifier,PingData.REQUEST);

			final PacketQueue packetQueue=network.packetQueue;
			packetQueue.enqueueOutgoingPacket(network,packet,computer);

			packetQueue.processAll();

			final List<PingResults> pingResults2=pingListener.getPingResults();

			if (pingResults2==null)
				pingListener.timedOut(new SourceIPAddress(card.ipAddress.get()));

			final List<PingResults> pingResults=arrayList();

			pingResults.addAll(pingListener.getPingResults());

			for (int a=0;a<pingResults.size();a++)
			{
				final PingResults result=pingResults.get(a);

				if (result.timedOut()&& Caster.equalT(route.gateway).invoke(card.ipAddress.get()))
					pingResults.set(a,hostUnreachable(result.getReplyingHost()));
			}

			return pingResults;
		}
		finally
		{
			computer.getIncomingPacketListeners().remove(pingListener);
		}
	}
}