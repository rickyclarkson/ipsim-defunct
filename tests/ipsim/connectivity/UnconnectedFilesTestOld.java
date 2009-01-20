package ipsim.connectivity;
/*
import fpeas.lazy.Lazy;
import fpeas.sideeffect.SideEffectUtility;
import ipsim.network.Network;
import ipsim.network.connectivity.ConnectivityResults;
import static ipsim.network.connectivity.ConnectivityTest.testConnectivity;

import java.io.File;

public class UnconnectedFilesTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final File directory=new File("datafiles/unconnected");

		for (final File file : directory.listFiles())
		{
			final Network network=new Network(null);
			loadFromFile(network, file);
			final ConnectivityResults results=testConnectivity(network, SideEffectUtility.<String>doNothing(), SideEffectUtility.<Integer>doNothing());

			if (100==results.getPercentConnected())
				throw new RuntimeException(file.toString());
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "UnconnectedFilesTest";
	}
}
*/