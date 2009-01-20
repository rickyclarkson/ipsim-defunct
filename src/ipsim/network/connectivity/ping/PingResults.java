package ipsim.network.connectivity.ping;

import ipsim.lang.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.SourceIPAddress;

public interface PingResults extends Stringable
{
	boolean pingReplyReceived();

	boolean hostUnreachable();

	SourceIPAddress getReplyingHost();

	boolean ttlExpired();

	boolean timedOut();
}