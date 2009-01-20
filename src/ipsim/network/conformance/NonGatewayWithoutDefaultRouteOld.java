package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import static ipsim.lang.FunctionOOO.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

class NonGatewayWithoutDefaultRoute extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network, Computer, Maybe<String>> isARouter=ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				return ConformanceTestsUtility.isARouter().invoke(network,computer)||!(0==size(getDefaultRoutes(computer.routingTable))) ? MaybeUtility.<String>nothing() : MaybeUtility.just("Non-gateway computer without a default route");
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllComputers,isARouter,noErrors,USUAL).run(network);
	}
        }*/