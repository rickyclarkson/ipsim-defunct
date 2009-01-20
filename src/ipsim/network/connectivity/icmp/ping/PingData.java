package ipsim.network.connectivity.icmp.ping;

public enum PingData
{
	REQUEST
	{
		@Override
		public String toString()
		{
			return "Request";
		}
	},
	REPLY
	{
		@Override
		public String toString()
		{
			return "Reply";
		}
	}
}