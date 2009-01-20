package ipsim.network.connectivity.icmp.ttl;

public enum TimeExceededData
{
	TIME_TO_LIVE_EXCEEDED
	{
		@Override
		public String toString()
		{
			return "Time to Live Exceeded";
		}
	}
}