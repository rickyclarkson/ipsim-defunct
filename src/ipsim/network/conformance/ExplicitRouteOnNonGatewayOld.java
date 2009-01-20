package ipsim.network.conformance;

/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.ConformanceTestsUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import org.jetbrains.annotations.*;

class ExplicitRouteOnNonGateway extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network,Computer, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				return getExplicitRoutes(computer.routingTable).iterator().hasNext()&&!isARouter().invoke(network,computer) ? MaybeUtility.just("An explicit route on a computer that is not a gateway") : MaybeUtility.<String>nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=NonsensicalArrangement.noErrors();
		return NonsensicalArrangement.customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/