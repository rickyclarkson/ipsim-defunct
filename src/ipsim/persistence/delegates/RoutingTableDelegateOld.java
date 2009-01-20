package ipsim.persistence.delegates;
/*
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import ipsim.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import static ipsim.persistence.delegates.RouteDelegate.*;
import ipsim.util.Collections;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

import static java.util.Collections.*;
import java.util.*;

public final class RoutingTableDelegate
{
	public static SerialisationDelegate<RoutingTable,RoutingTable> routingTableDelegate(final Network network)
	{
		return new SerialisationDelegate<RoutingTable,RoutingTable>()
		{
			public void writeXML(final XMLSerialiser serialiser, @NotNull final RoutingTable table)
			{
				int a=0;
				for (final Route entry : table.routes())
				{
					serialiser.writeObject(entry, "entry "+a, routeDelegate);
					a++;
				}
			}

			public RoutingTable readXML(final XMLDeserialiser deserialiser, final Node node, final RoutingTable table)
			{
				final List<String> names=Collections.arrayList();

				names.addAll(getObjectNames(node));

				names.remove("computer");

				sort(names);
				final Maybe<Computer> nothing=nothing();

				for (final String name : names)
					table.add(network, nothing, deserialiser.readObject(node, name, routeDelegate, Caster.asFunction(Route.class)));

				return table;
			}

			public RoutingTable construct()
			{
				return createRoutingTable();
			}

			@NotNull
			public String getIdentifier()
			{
				return "ipsim.persistence.delegates.RoutingTableDelegate";
			}
		};
	}
        }*/