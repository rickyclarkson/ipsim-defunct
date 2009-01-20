package ipsim.persistence.delegates;

/*
import static ipsim.lang.PositionOrParent.origin;
import static ipsim.network.IPAddressUtility.valueOf;
import ipsim.*;
import ipsim.lang.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import ipsim.network.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import static ipsim.persistence.delegates.NetBlockDelegate.*;
import org.w3c.dom.*;
import org.jetbrains.annotations.*;
import com.rickyclarkson.testsuite.*;
import fpeas.maybe.*;
import fpeas.predicate.Predicate;
import java.util.Random;
import fpeas.lazy.Lazy;
import ipsim.network.NetMaskUtility;

public final class RouteDelegate
{
	public static final SerialisationDelegate<Route,Route> routeDelegate=new SerialisationDelegate<Route,Route>()
	{
		public void writeXML(final XMLSerialiser serialiser, final Route route)
		{
			serialiser.writeObject(route.block, "destination", netBlockDelegate);
			serialiser.writeAttribute("gateway", route.gateway.toString());
		}

		public Route readXML(final XMLDeserialiser deserialiser, final Node node, final Route object)
		{
			final NetBlock destination=deserialiser.readObject(node, "destination", netBlockDelegate, Caster.asFunction(NetBlock.class));
			final IPAddress gateway=IPAddressUtility.valueOf(readAttribute(node, "gateway")).get();

			return new Route(destination, gateway);
		}

		public Route construct()
		{
			return null;
		}

		@NotNull
		public String getIdentifier()
		{
			return "ipsim.persistence.delegates.RoutingTableEntryDelegate";
		}
	};

	public static final Lazy<Boolean> testRouteDelegate=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			Network network=new Network(null);
			NetworkContext context=network.contexts.prepend(new NetworkContext());
			Computer computer=context.visibleComponents.prepend(new Computer(origin));
			Card card=context.visibleComponents.prepend(new Card(false,PositionOrParent.parent(computer)));
			CardDrivers drivers=card.installDeviceDrivers(network);
			drivers.ipAddress.set(IPAddressUtility.valueOf("146.87.1.1").get());
			drivers.netMask.set(NetMaskUtility.valueOf("255.255.255.0").get());
			computer.routingTable.add(network, MaybeUtility.just(computer),new Route(NetBlockUtility.zero(),IPAddressUtility.valueOf("146.87.1.2").get()));

			NetworkUtility.loadFromString(network,NetworkUtility.saveToString(network));
			return network.contexts.get().get(0).visibleComponents.get().only(PacketSourceUtility.isComputerRef).map(PacketSourceUtility.asComputerRef).car().routingTable.routes().size()==1;
		}
	};
}
*/