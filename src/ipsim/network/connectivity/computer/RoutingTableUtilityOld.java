package ipsim.network.connectivity.computer;
/*
import fpeas.predicate.*;
import static fpeas.predicate.PredicateUtility.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

public class RoutingTableUtility
{
	public static boolean hasRouteFor(Network network,final Computer computer,final DestIPAddress destIP)
	{
		return getRouteFor(network,computer,destIP)!=null;
	}

	@Nullable
	public static Route getRouteFor(final Network network,final Computer computer, final DestIPAddress destIP)
	{
		for (final CardDrivers card: ComputerUtility.cardsWithDrivers(network,computer))
		{
			final NetBlock block=CardUtility.getNetBlock(card);
			final IPAddress cardIP=card.ipAddress.get();

			if (0==cardIP.rawValue())
				continue;

			if (block.networkContains(destIP.ipAddress()))
				return new Route(block, cardIP);
		}

		final RoutingTable routingTable=computer.routingTable;

		for (final Route entry: routingTable.routes())
		{
			final NetBlock destination=entry.block;

			if (destination.networkContains(destIP.ipAddress()))
				return entry;
		}

		return null;
	}

	public static final Predicate<Route> isDefaultRoute=new Predicate<Route>()
	{
		public boolean invoke(@NotNull final Route route)
		{
			return RouteUtility.isDefaultRoute(route);
		}
	};

	public static Iterable<Route> getExplicitRoutes(final RoutingTable table)
	{
		return table.routes().only(not(isDefaultRoute));
	}

	public static Stream<Route> getDefaultRoutes(final RoutingTable table)
	{
		return table.routes().only(isDefaultRoute);
	}
        }*/