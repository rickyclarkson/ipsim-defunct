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
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import fpeas.lazy.Lazy;

public class PingerTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network();
		NetworkUtility.loadFromFile(network,new File("datafiles/unconnected/pingertest1.ipsim"));

		final IPAddress ip4_3=IPAddressUtility.valueOf("146.87.4.3").get(),ip4_1=IPAddressUtility.valueOf("146.87.4.1").get();

		return all(getComputersByIP(network,IPAddressUtility.valueOf("146.87.1.1").get()),new Predicate<Computer>()
		{
			public boolean invoke(@NotNull final Computer computer)
			{
				final List<PingResults> results=ping(network,computer, new DestIPAddress(ip4_3), DEFAULT_TIME_TO_LIVE);

				return 1==results.size() && all(results,new Predicate<PingResults>()
				{
					public boolean invoke(@NotNull final PingResults result)
					{
						return result.hostUnreachable() && result.getReplyingHost().ipAddress().equals(ip4_1);
					}
				});
			}
		});
	}
}
*/