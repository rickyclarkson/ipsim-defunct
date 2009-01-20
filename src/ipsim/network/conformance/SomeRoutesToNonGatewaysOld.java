package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import fpeas.predicate.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

class SomeRoutesToNonGateways extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network,Computer, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				for (final Route route: computer.routingTable.routes())
				{
					final Stream<Computer> gateways=getComputersByIP(network, route.gateway);

					if (gateways.isEmpty())
						return MaybeUtility.just("Computer with a route to a non-existent gateway");

					if (!any(gateways,new Predicate<Computer>()
					{
						public boolean invoke(final Computer aComputer)
						{
							return ConformanceTestsUtility.isARouter().invoke(network,aComputer);
						}
					}))
						return MaybeUtility.just("Computer with a route to a computer that is not a gateway");
				}

				return MaybeUtility.nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/