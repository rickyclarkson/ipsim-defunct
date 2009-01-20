package ipsim.ethernet;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.lazy.Lazy;
import static ipsim.Caster.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import fpeas.lazy.Lazy;

public final class ComputerTest
{
	public static final Lazy<Boolean> testCardIndexRetention=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Network network=new Network();
			final NetworkContext context=network.contexts.append(new NetworkContext());

			final Computer computer=context.visibleComponents.prependIfNotPresent(new Computer(position(PointUtility.origin())).withID(network.generateComputerID()));

			final CardDrivers card0=context.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(parent(computer)))).installDeviceDrivers(network);
			context.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(parent(computer)))).installDeviceDrivers(network);

			return 0==card0.ethNumber;
		}

		@Override
		public String toString()
		{
			return "card index retention";
		}
	};
}
*/