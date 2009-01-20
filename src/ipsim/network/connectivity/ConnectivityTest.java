package ipsim.network.connectivity;

import fpeas.sideeffect.*;
import static ipsim.Caster.*;
import ipsim.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.connectivity.ping.*;
import ipsim.network.ethernet.*;
import static ipsim.network.ethernet.ComputerUtility.*;
import static ipsim.network.NetBlockUtility.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class ConnectivityTest
{
	private ConnectivityTest()
	{
	}

	public static ConnectivityResults testConnectivity(final Network network,final SideEffect<String> log, final SideEffect<Integer> progress)
	{
		final Stream<Computer> computers=NetworkUtility.getAllComputers(network);

		final List<String> results=arrayList();

		final int[] total={0};

		int currentComputer=0;

		for (final Computer computer: computers)
		{

			@Nullable
			final IPAddress sourceIPAddress=getTheFirstIPAddressYouCanFind(network,computer);

			if (sourceIPAddress==null)
				continue;

			for (final Computer computer2: computers)
			{
				if (computer2.equals(computer))
					continue;

				final Stream<CardDrivers> cards=ComputerUtility.cardsWithDrivers(network,computer2);

				for (final CardDrivers card: cards)
				{
					final IPAddress ipAddress=card.ipAddress.get();
					total[0]++;

					final boolean isBroadcast=CardUtility.getNetBlock(card).broadcastAddress().equals(ipAddress);

					final List<PingResults> pingResults=arrayList();

					for (final Computer anotherComputer: computers)
						anotherComputer.arpTable.clear();

					log.run("Pinging "+ipAddress.toString()+" from "+asNotNull(getTheFirstIPAddressYouCanFind(network,computer)).toString());

					final List<PingResults> tempPingResult=Pinger.ping(network,computer, new DestIPAddress(ipAddress), Globals.DEFAULT_TIME_TO_LIVE);
					pingResults.addAll(tempPingResult);

					final PingResults firstResult=pingResults.get(0);

					if (!firstResult.pingReplyReceived()||isBroadcast)
					{
						if (isBroadcast)
							pingResults.set(0, PingResultsUtility.hostUnreachable(new SourceIPAddress(sourceIPAddress)));

						results.add(sourceIPAddress.toString()+" cannot ping "+ipAddress.toString()+": "+firstResult.toString());
					}
				}
			}

			progress.run(currentComputer*100/computers.size());
			currentComputer++;
		}

		final int finalTotal=total[0];

		return new ConnectivityResults()
		{
			public int getPercentConnected()
			{
				return (int)(100-results.size()*100.0/finalTotal);
			}

			public Collection<String> getOutputs()
			{
				return results;
			}

			@Override
			public String toString()
			{
				final StringBuilder builder=new StringBuilder();

				builder.append(getPercentConnected()).append("% connected\n");

				for (final String string: results)
				{
					builder.append(string);
					builder.append('\n');
				}

				return builder.toString();
			}
		};
	}
}