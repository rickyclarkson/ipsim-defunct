package ipsim.network.connectivity.ping;

import fpeas.function.*;
import fpeas.predicate.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import static ipsim.network.connectivity.PacketUtility2.*;
import static ipsim.network.connectivity.icmp.ping.PingData.*;
import static ipsim.network.connectivity.icmp.ttl.TimeExceededData.*;
import static ipsim.network.connectivity.icmp.unreachable.UnreachableData.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.connectivity.ping.PingResultsUtility.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class PingListener implements IncomingPacketListener
{
	private final Object identifier;
	private final List<PingResults> pingResults=arrayList();

	public PingListener(final Object identifier)
	{
		this.identifier=identifier;
	}

	public Function<Network, Network> packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination)
	{
		return new Function<Network, Network>()
		{
			@NotNull
			public Network run(@NotNull final Network network)
			{
				@Nullable
				final IPPacket ipPacket=asIPPacket(packet);

				if (ipPacket==null || !ipPacket.identifier.equals(identifier))
					return network;

				final Predicate<Object> equalT=equalT(ipPacket.data);

				if (equalT.invoke(TIME_TO_LIVE_EXCEEDED))
					pingResults.add(ttlExpired(ipPacket.sourceIPAddress));

				if (equalT.invoke(REPLY))
					pingResults.add(pingReplyReceived(ipPacket.sourceIPAddress));

				if (equalT.invoke(NET_UNREACHABLE))
					pingResults.add(netUnreachable(ipPacket.sourceIPAddress));

				if (equalT.invoke(HOST_UNREACHABLE))
					pingResults.add(hostUnreachable(ipPacket.sourceIPAddress));

				return network;
			}
		};

	}

	@Nullable
	public List<PingResults> getPingResults()
	{
		if (pingResults.isEmpty())
			return null;

		return pingResults;
	}

	public void timedOut(final SourceIPAddress address)
	{
		if (pingResults.isEmpty())
			pingResults.add(PingResultsUtility.timedOut(address));
	}
}