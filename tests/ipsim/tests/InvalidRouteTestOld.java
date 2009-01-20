package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import static fpeas.maybe.MaybeUtility.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.ethernet.*;
import static ipsim.network.IPAddress.*;
import fpeas.lazy.Lazy;

public class InvalidRouteTest
{
	public static final Lazy<Boolean> testProgrammaticallyAddingInvalidRoute=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Network network=new Network();
			final NetworkContext context=network.contexts().append(new NetworkContext());

			final Computer computer=context.visibleComponents.prependIfNotPresent(new Computer(PositionOrParent.position(new Point(200, 200)))).withID(network.generateComputerID());
			final Route route=new Route(NetBlockUtility.zero(), IPAddressUtility.valueOf("146.87.1.1").get());

			return !computer.routingTable.add(network, just(computer), route);
		}
	};
}
*/