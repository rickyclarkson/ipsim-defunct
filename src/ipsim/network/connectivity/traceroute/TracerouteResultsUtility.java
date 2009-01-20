package ipsim.network.connectivity.traceroute;

public final class TracerouteResultsUtility
{
	public static TracerouteResults newTracerouteResults()
	{
		return new TracerouteResults()
		{
			public final StringBuilder builder=new StringBuilder();

			@Override
			public String toString()
			{
				return builder.toString();
			}

			public void add(final String object)
			{
				builder.append(object);
				builder.append('\n');
			}

			public int size()
                        {
	                        return builder.toString().split("\n").length;
                        }
		};
	}
}
