package ipsim.network.connectivity.icmp.unreachable;

public enum UnreachableData
{
	NET_UNREACHABLE
	{
		@Override
		public String toString()
		{
			return "Net Unreachable";
		}
	},
	HOST_UNREACHABLE
	{
		@Override
		public String toString()
		{
			return "Host Unreachable";
		}
	}
}