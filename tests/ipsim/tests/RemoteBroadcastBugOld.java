package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import static ipsim.network.connectivity.PingTester.*;
import ipsim.network.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.connectivity.ping.*;
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;
import fpeas.lazy.Lazy;
import java.io.*;
import java.util.*;

public class RemoteBroadcastBug implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network(null);
		NetworkUtility.loadFromFile(network,new File("datafiles/fullyconnected/101.ipsim"));

		final IPAddress ip41=IPAddressUtility.valueOf("146.87.4.1").get();

		return all(testPing(network,IPAddressUtility.valueOf("146.87.1.1").get(),IPAddressUtility.valueOf("146.87.4.255").get()),new Predicate<List<PingResults>>()
		{
			public boolean invoke(@NotNull final List<PingResults> results)
			{
				final PingResults result=results.iterator().next();

				return result.pingReplyReceived() && result.getReplyingHost().ipAddress().equals(ip41) && 1==results.size();
			}
		});
	}

	@Override
	public String toString()
	{
		return "RemoteBroadcastBug";
	}
}
*/