package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import static ipsim.network.connectivity.PingTester.*;
import static ipsim.lang.Assertion.*;
import ipsim.network.*;
import ipsim.network.connectivity.ping.*;
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;
import fpeas.lazy.Lazy;
import java.io.*;
import java.util.*;


public class IncompleteArpTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network();
		network.loadFromFile(new File("datafiles/unconnected/hubdisabled.ipsim"));

		if (!all(testPing(network,IPAddressUtility.valueOf("146.87.1.1").get(),IPAddressUtility.valueOf("146.87.1.2").get()),new Predicate<List<PingResults>>()
		{
			public boolean invoke(@NotNull final List<PingResults> results)
			{
				return 1==results.size() && results.iterator().next().hostUnreachable();
			}
		}))
			return false;

		getAllHubs(network).iterator().next().setPower(true);

		for (final List<PingResults> results: testPing(network,IPAddressUtility.valueOf("146.87.1.1").get(),IPAddressUtility.valueOf("146.87.1.2").get()))
			assertTrue(1==results.size(),results.iterator().next().hostUnreachable());

		return true;
	}
}
*/