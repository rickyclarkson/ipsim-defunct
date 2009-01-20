package ipsim.connectivity;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import static ipsim.network.connectivity.PingTester.*;
import ipsim.network.*;
import static ipsim.network.Network$.*;
import ipsim.network.connectivity.ping.*;
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import fpeas.lazy.Lazy;

public class BroadcastPingTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network();
		network.loadFromFile(new File("datafiles/fullyconnected/broadcast1.ipsim"));

		return all(testPing(network, IPAddress.valueOf("146.87.1.1").get(),IPAddress.valueOf("146.87.1.255").get()),new Predicate<List<PingResults>>()
		{
			public boolean invoke(@NotNull final List<PingResults> results)
			{
				return 2==results.size();
			}
		});
	}

	@Override
	public String toString()
	{
		return "BroadcastPingTest";
	}
}
*/