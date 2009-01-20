package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.util.Collections;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultipleRoutesToTheSameSubnet extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network,Computer, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				final Collection<IPAddress> subnets=Collections.hashSet();

				for (final Route route: computer.routingTable.routes())
				{
                                    if (subnets.contains(route.block.networkNumber()) && !route.block.networkNumber().equals(new IPAddress(0)))
						return MaybeUtility.just("Computer with more than one route to the same subnet");

                                    subnets.add(route.block.networkNumber());
				}

				return MaybeUtility.nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return NonsensicalArrangement.customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/