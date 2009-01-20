package ipsim.network.conformance;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import fpeas.predicate.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.PacketSourceUtility.*;
import ipsim.network.conformance.ConformanceTests.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.hub.*;
import static ipsim.network.ethernet.CableUtility.*;
import ipsim.network.ethernet.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.Random;

public class HubWithMoreThanOneSubnet extends Function<Network, CheckResult>
{
	public static final String errorMessage="Hub with more than one subnet connected to it";

	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		Stream<PacketSource> warnings=new Stream<PacketSource>();

		for (final Hub hub: NetworkUtility.getAllHubs(network))
		{
			NetBlock netBlock=null;

			for (final Cable cable: hub.getCables(network))
			{
				final PacketSource otherEnd=getOtherEnd(cable, hub);

				if (otherEnd==null)
					continue;

				@Nullable final Card card=asCard(otherEnd);

				if (card==null)
					continue;

				@Nullable final CardDrivers cardWithDrivers=card.withDrivers;

				if (cardWithDrivers==null || cardWithDrivers.ipAddress.get().rawValue()==0)
					continue;

				final NetBlock temp=CardUtility.getNetBlock(cardWithDrivers);

				if (netBlock==null)
					netBlock=temp;
				else
					if (!netBlock.equals(temp))
						warnings=warnings.prepend(hub);
			}
		}

		if (warnings.isEmpty())
			return CheckResultUtility.fine();

		return new CheckResult(TypicalScores.SEVERE, Stream.one(errorMessage), warnings);
	}

	public static Predicate<Random> testFalsePositiveWithZeroIP()
	{
            return new Predicate<Random>()
		{
			public boolean invoke(final Random random)
			{
				final Network network=new Network(null);
				loadFromFile(network,new File("datafiles/unconnected/hubwithmorethanonesubnet-zerotest.ipsim"));

				final ResultsAndSummaryAndPercent allChecks=ConformanceTests.allChecks(network);
				final Stream<CheckResult> results=allChecks.results;

				return results.only(new Predicate<CheckResult>()
				{
					public boolean invoke(final CheckResult checkResult)
					{
						return !checkResult.withWarnings.only(isHub).isEmpty();
					}
				}).isEmpty();
			}

			@Override
			public String toString()
			{
				return "testFalsePositiveWithZeroIP";
			}
		};
	}
}
*/