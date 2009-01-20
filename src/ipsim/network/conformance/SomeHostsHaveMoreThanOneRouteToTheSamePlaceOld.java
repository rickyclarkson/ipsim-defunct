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
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import ipsim.network.ethernet.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.util.*;

class SomeHostsHaveMoreThanOneRouteToTheSamePlace extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network,Computer, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				final Collection<NetBlock> blocks=arrayList();

				for (final Route route: getExplicitRoutes(computer.routingTable))
				{
					final NetBlock block=route.block;

					if (blocks.contains(block))
						return MaybeUtility.just("Computer with more than one route to the same network");

					blocks.add(block);
				}

				return MaybeUtility.nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/