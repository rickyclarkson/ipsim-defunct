package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetMask.*;
import ipsim.network.ip.*;
import ipsim.util.*;

import java.io.*;
import fpeas.lazy.Lazy;

public class RoutingTableBugs
{
	public static Lazy<Boolean> loadingUnreachableRoutes()
	{
		return new Lazy<Boolean>()
		{
			public Boolean invoke()
			{
				final Network network=new Network(null);
				NetworkUtility.loadFromFile(network,new File("datafiles/unconnected/1.14.ipsim"));

				final NetMask netMask=NetMaskUtility.valueOf("192.0.0.0").get();
				final Stream<Computer> computers=NetworkUtility.getComputersByIP(network,IPAddressUtility.valueOf("10.0.0.1").get());

				for (final Computer computer: computers)
				{
					final Stream<CardDrivers> possibleCards=ComputerUtility.cardsWithDrivers(network,computer).only(new Predicate<CardDrivers>()
					{
						public boolean invoke(final CardDrivers card)
						{
							return card.netMask.get().equals(netMask);
						}
					});

					if (!possibleCards.isEmpty())
						return getDefaultRoutes(computer.routingTable).iterator().hasNext();
				}

				return false;
			}

			@Override
			public String toString()
			{
				return "RoutingTableBugs.loadingUnreachableRoutes";
			}
		};
	}
}
*/