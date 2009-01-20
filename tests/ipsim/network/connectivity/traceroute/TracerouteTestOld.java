package ipsim.network.connectivity.traceroute;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.connectivity.traceroute.Traceroute.*;
import ipsim.network.ip.*;
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;
import fpeas.lazy.Lazy;
import java.io.*;

public class TracerouteTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network(null);
		NetworkUtility.loadFromFile(network, new File("datafiles/fullyconnected/1.6.ipsim"));

		final IPAddress ipAddress=IPAddressUtility.valueOf("146.87.1.1").get();

		return all(getComputersByIP(network, ipAddress), new Predicate<Computer>()
		{
			public boolean invoke(@NotNull final Computer computer)
			{
                            return trace(network, computer, new DestIPAddress(IPAddressUtility.valueOf("146.87.4.3").get()), 30).toString().startsWith("1: 146.87.1.3\n2: 146.87.2.3\n3: 146.87.4.3");
			}
		});
	}
}
*/