package ipsim.network.conformance;

/*import fpeas.function.*;
import fpeas.predicate.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.CheckResultUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import static ipsim.network.ethernet.RouteUtility.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

class CycleInDefaultRoutes extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Stream<PacketSource> empty=new Stream<PacketSource>();

		for (final Computer computer: getAllComputers(network))
			for (final Route route: computer.routingTable.routes())
				if (isDefaultRoute(route)&&!getComputersByIP(network, route.gateway).contains(computer))
					if (detectCycle(network, FunctionUtility.<Computer,Boolean>constant(false),computer,route))
						return new CheckResult(USUAL, Stream.one("Cycle in default routes"), empty);

		return fine();
	}

	public static boolean detectCycle(final Network network,final Function<Computer,Boolean> containsComputer,final Computer computer,final Route route)
	{
		if (isRouteToSelf(network,computer,route))
			return false;

		return any(getComputersByIP(network, route.gateway),new Predicate<Computer>()
		{
			public boolean invoke(final Computer computer1)
			{
				if (containsComputer.run(computer1))
					return true;

				return !getDefaultRoutes(computer1.routingTable).only(new Predicate<Route>()
				{
					public boolean invoke(final Route route2)
					{
						return detectCycle(network, new Function<Computer, Boolean>()
						{
							@NotNull
							public Boolean run(@NotNull final Computer aComputer)
							{
								return aComputer.equals(computer1) || containsComputer.run(aComputer);
							}
						}, computer, route2);
					}
				}).isEmpty();
			}
		});
	}
        }*/