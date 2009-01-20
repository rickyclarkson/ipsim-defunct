package ipsim.network.conformance;

/*import static ipsim.network.conformance.RoutePointsAtNonLocalGateway.routePointsAtNonLocalGateway;
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import static ipsim.lang.FunctionOOO.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.ethernet.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ConformanceTestsUtility
{
	public static Collection<Function<Network, CheckResult>> createNetworkCheck()
	{
		final Collection<Function<Network, CheckResult>> checks=arrayList();

		checks.add(new PercentOfIPsMatchingProblem());
		checks.add(new CycleInDefaultRoutes());
		checks.add(new ZeroSubnetMaskUsed());
		checks.add(new OneSubnetMaskUsed());
		checks.add(new PercentOfRequiredSubnets());

		checks.add(computerWithoutNetworkCard());
		checks.add(cardWithoutCable());
		checks.add(cableWithEndsDisconnected());
		checks.add(cardWithoutDeviceDrivers());
		checks.add(cardWithZeroIP());
		checks.add(hubWithNoCables());

		checks.add(new PercentUniqueIPAddresses());
		checks.add(new HubWithMoreThanOneSubnet());
		checks.add(new SomeHostsHaveMoreThanThreeCards());

		checks.add(new ZeroHostNumberUsed());

		checks.add(new OneHostNumberUsed());

		checks.add(new SomeHubsHaveNoStandaloneHost());

		checks.add(new SomeHostsHaveTwoCardsWithTheSameNetworkNumber());

		checks.add(new SomeHostsHaveRouteToSelf());

		checks.add(new SomeHostsHaveMoreThanOneDefaultRoute());

		checks.add(new ExplicitRouteOnNonGateway());

		checks.add(new SomeRoutesToNonGateways());

		checks.add(new ExplicitRouteToLocalNetwork());

		checks.add(new NonGatewayWithoutDefaultRoute());

		checks.add(new PacketForwardingEnabledOnNonGateway());

		checks.add(new SomeHostsHaveMoreThanOneRouteToTheSamePlace());
		checks.add(new MultipleRoutesToTheSameSubnet());
		checks.add(routePointsAtNonLocalGateway);

		return checks;
	}

	public static CheckResult someHostsHaveThisManyCards(final Network network, final String string, final Function<Integer, Boolean> thisMany)
	{
		final FunctionOOO<Network,Computer, Maybe<String>> haveThisMany=ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				return thisMany.run(computer.getCards(network).size()) ? MaybeUtility.just("A computer with "+string+" cards") : MaybeUtility.<String>nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllComputers, haveThisMany, noErrors, USUAL).run(network);
	}

	public static FunctionOOO<Network,Computer, Boolean> isARouter()
	{
		return new FunctionOOO<Network,Computer, Boolean>()
		{
			@Override
			@NotNull
			public Boolean invoke(final Network network,@NotNull final Computer computer)
			{
				return ComputerUtility.getIPAddresses(network,computer).size()>1;
			}
		};
	}

        }*/