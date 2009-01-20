package ipsim.network.connectivity.computer;
/*
import fpeas.maybe.*;
import fpeas.sideeffect.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.ethernet.*;
import static ipsim.network.ethernet.RouteUtility.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;

import static java.util.Collections.*;
import java.util.*;

/* TODO make sure that the routing table view is updated after editing without closing and reopening * /
public final class RoutingTable implements Stringable
{
	private List<Route> routes=arrayList();

	public boolean add(final Network network,final Maybe<Computer> maybeComputer, final Route route)
	{
		final boolean[] result={true};

		MaybeUtility.run(maybeComputer, new SideEffect<Computer>()
		{
			public void run(final Computer computer)
			{
				if (ComputerUtility.isLocallyReachable(network,computer, route.gateway))
					routes.add(route);
				else
					result[0]=false;
			}
		}, new Runnable()
		{
			public void run()
			{
				routes.add(route);
			}
		});

		return result[0];
	}

	public void remove(@NotNull final Route route)
	{
		for (Iterator<Route> iter=routes.iterator();iter.hasNext();)
			if (iter.next().equals(route))
				iter.remove();
	}

	@Override
	public String toString()
	{
		return join(fromIterable(routes).map(Stringables.<Route>asString()), "\n");
	}

	@NotNull
	public Stream<Route> routes()
	{
		final List<Route> result=arrayList();

		for (final Route route: routes)
			result.add(route);

		sort(result, new Comparator<Route>()
		{
			public int compare(final Route r1, final Route r2)
			{
				if (isDefaultRoute(r1)==isDefaultRoute(r2))
					return 0;

				if (isDefaultRoute(r2))
					return -1;

				return 1;
			}
		});

		return fromIterable(result);
	}

	public void replace(@NotNull final Route previous, @NotNull final Route newRoute)
	{
		routes.set(routes.indexOf(previous),newRoute);
	}
}*/