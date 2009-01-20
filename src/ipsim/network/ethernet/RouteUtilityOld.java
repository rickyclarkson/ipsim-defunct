package ipsim.network.ethernet;

/*import fpeas.predicate.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.ethernet.ComputerUtility.*;
import ipsim.util.*;

public class RouteUtility
{
	public static boolean isDefaultRoute(final Route route)
	{
		return route.block.equals(NetBlockUtility.zero());
	}

	public static boolean isRouteToSelf(final Network network,final Computer computer, final Route route)
	{
		return Collections.any(cardsWithDrivers(network,computer),new Predicate<CardDrivers>()
		{
			public boolean invoke(final CardDrivers card)
			{
				return card.ipAddress.get().equals(route.gateway);
			}
		});
	}

	public static String asCustomString(final Route route)
	{
		final NetBlock destination=route.block;
		final boolean bool=0==destination.networkNumber().rawValue() && 0==destination.netMask().rawValue();
		final String string=bool ? "default" : NetBlockUtility.asCustomString(destination);

		final IPAddress gateway=route.gateway;
		return "Destination: "+string+" Gateway: "+(0==gateway.rawValue() ? "default" : gateway.toString());
	}
        }*/