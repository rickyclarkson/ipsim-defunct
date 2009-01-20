package ipsim.connectivity;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import static ipsim.gui.Global.*;
import ipsim.network.*;
import static ipsim.network.Network$.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.connectivity.ping.*;
import static ipsim.network.connectivity.ping.Pinger.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import fpeas.lazy.Lazy;

public class RoutingLoopTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network(null);
		NetworkUtility.loadFromFile(network,new File("datafiles/fullyconnected/routingloop1.ipsim"));

		final IPAddress ipAddress=valueOf("146.87.1.1").get(),ipAddress2=valueOf("146.87.2.1").get();

		return all(getComputersByIP(network,ipAddress),new Predicate<Computer>()
		{
			public boolean invoke(@NotNull final Computer computer)
			{
				final List<PingResults> results=ping(network,computer, new DestIPAddress(ipAddress2), DEFAULT_TIME_TO_LIVE);
				return 1==results.size() && results.iterator().next().ttlExpired();
			}
		});
	}

	@Override
	public String toString()
	{
		return "RoutingLoopTest";
	}
}
*/