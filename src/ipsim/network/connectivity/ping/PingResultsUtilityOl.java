package ipsim.network.connectivity.ping;
/*
import ipsim.network.connectivity.ip.*;
import ipsim.network.SourceIPAddress;

public final class PingResultsUtility
{
	public static PingResults timedOut(final SourceIPAddress reporter)
	{
		return new PingResults()
		{
			@Override
			public String toString()
			{
				return reporter.toString()+" reports Request timed out";
			}

			public boolean pingReplyReceived()
			{
				return false;
			}

			public SourceIPAddress getReplyingHost()
			{
				return reporter;
			}

			public boolean ttlExpired()
			{
				return false;
			}

			public boolean hostUnreachable()
			{
				return false;
			}

			public boolean timedOut()
			{
				return true;
			}
		};
	}

	public static PingResults hostUnreachable(final SourceIPAddress reporter)
	{
		return new PingResults()
		{
			@Override
			public String toString()
			{
				return reporter.toString()+" reports Host unreachable";
			}

			public boolean pingReplyReceived()
			{
				return false;
			}

			public boolean hostUnreachable()
			{
				return true;
			}

			public SourceIPAddress getReplyingHost()
			{
				return reporter;
			}

			public boolean ttlExpired()
			{
				return false;
			}

			public boolean timedOut()
			{
				return false;
			}
		};
	}

	public static PingResults ttlExpired(final SourceIPAddress reporter)
	{
		return new PingResults()
		{
			@Override
			public String toString()
			{
				return reporter.toString()+" reports TTL expired";
			}

			public boolean pingReplyReceived()
			{
				return false;
			}

			public boolean hostUnreachable()
			{
				return false;
			}

			public SourceIPAddress getReplyingHost()
			{
				return reporter;
			}

			public boolean ttlExpired()
			{
				return true;
			}

			public boolean timedOut()
			{
				return false;
			}
		};
	}

	public static PingResults pingReplyReceived(final SourceIPAddress source)
	{
		return new PingResults()
		{
			@Override
			public String toString()
			{
				return "Reply received from "+source.toString();
			}

			public boolean pingReplyReceived()
			{
				return true;
			}

			public boolean hostUnreachable()
			{
				return false;
			}

			public SourceIPAddress getReplyingHost()
			{
				return source;
			}

			public boolean ttlExpired()
			{
				return false;
			}

			public boolean timedOut()
			{
				return false;
			}
		};
	}

	public static PingResults netUnreachable(final SourceIPAddress gatewayIPAddress)
	{
		return new PingResults()
		{
			@Override
			public String toString()
			{
				return gatewayIPAddress.toString()+" reports Net Unreachable";
			}

			public boolean pingReplyReceived()
			{
				return false;
			}

			public SourceIPAddress getReplyingHost()
			{
				return gatewayIPAddress;
			}

			public boolean ttlExpired()
			{
				return false;
			}

			public boolean hostUnreachable()
			{
				return false;
			}

			public boolean timedOut()
			{
				return false;
			}
		};
	}
        }*/