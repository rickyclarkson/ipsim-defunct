package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.Network$.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.ethernet.ComputerUtility.*;
import org.jetbrains.annotations.*;

public class RoutePointsAtNonLocalGateway
{
	public static final Function<Network, CheckResult> routePointsAtNonLocalGateway=new Function<Network, CheckResult>()
	{
		@NotNull
		public CheckResult run(@NotNull final Network network)
		{
			final FunctionOOO<Network, Computer, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
			{
				@NotNull
				public Maybe<String> run(@NotNull final Computer computer)
				{
					for (final Route route : computer.routingTable.routes())
						if (!isLocallyReachable(network, computer, route.gateway))
							return MaybeUtility.just("Route with a non-local gateway (this is a bug if it isn't from an old saved file)");

					return MaybeUtility.nothing();
				}
			});

			final Function<Computer, Maybe<String>> noErrors=noErrors();

			return NonsensicalArrangement.customCheck(getAllComputers, warning, noErrors, USUAL).run(network);
		}
	};
        }*/