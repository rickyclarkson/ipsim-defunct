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

class SomeHostsHaveMoreThanOneDefaultRoute extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network, Computer, Maybe<String>> warning=ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				return size(getDefaultRoutes(computer.routingTable))>1 ? MaybeUtility.just("Computer with more than one default route") : MaybeUtility.<String>nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return NonsensicalArrangement.customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/