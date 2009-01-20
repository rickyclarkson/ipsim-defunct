package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import ipsim.connectivity.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.ip.*;
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;
import fpeas.lazy.Lazy;
import java.io.*;

public class ArpStoredFromForeignNetworkBug implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network(null);
		NetworkUtility.loadFromFile(network,new File("datafiles/fullyconnected/arpforeign.ipsim"));

		PingTester.testPing(network,IPAddressUtility.valueOf("146.87.1.1").get(),IPAddressUtility.valueOf("146.87.2.1").get());

		return all(getComputersByIP(network,IPAddressUtility.valueOf("146.87.1.1").get()),new Predicate<Computer>()
		{
			public boolean invoke(@NotNull final Computer computer)
			{
                            return computer.arpTable.getMacAddress(IPAddressUtility.valueOf("146.87.2.1").get())==null;
			}
		});
	}

	@Override
	public String toString()
	{
		return "ArpStoredFromForeignNetworkBug";
	}
}
*/