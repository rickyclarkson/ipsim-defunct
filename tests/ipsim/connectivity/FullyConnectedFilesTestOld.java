package ipsim.connectivity;
/*
import fpeas.sideeffect.SideEffect;
import static fpeas.sideeffect.SideEffectUtility.doNothing;
import ipsim.network.Network;
import ipsim.network.Network$;
import ipsim.network.connectivity.ConnectivityResults;
import ipsim.network.connectivity.ConnectivityTest;

import java.io.File;
import fpeas.predicate.Predicate;
import java.util.Random;
import fpeas.lazy.Lazy;

public class FullyConnectedFilesTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final File directory=new File("datafiles/fullyconnected");

		for (final File file: directory.listFiles())
		{
			final Network network=new Network();
			network.loadFromFile(file);

			final SideEffect<String> log=doNothing();
			final SideEffect<Integer> progress=doNothing();
			final ConnectivityResults results=ConnectivityTest.testConnectivity(network, log, progress);

			if (100!=results.getPercentConnected())
				throw new RuntimeException(file.toString()+": "+results.toString());
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "FullyConnectedFilesTest";
	}
}
*/