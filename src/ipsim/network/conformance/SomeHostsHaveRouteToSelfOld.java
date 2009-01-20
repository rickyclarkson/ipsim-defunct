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
import static ipsim.network.ethernet.ComputerUtility.*;
import org.jetbrains.annotations.*;

class SomeHostsHaveRouteToSelf extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network context)
	{
		final FunctionOOO<Network,Computer, Maybe<String>> warning=new FunctionOOO<Network,Computer, Maybe<String>>()
		{
			@Override
			@NotNull
			public Maybe<String> invoke(Network network,@NotNull final Computer computer)
			{
				for (final Route route: computer.routingTable.routes())
					if (getIPAddresses(network,computer).contains(route.gateway))
						return MaybeUtility.just("Computer with a route that points at itself");

				return MaybeUtility.nothing();
			}
		};

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllComputers,warning,noErrors,USUAL).run(context);
	}
        }*/