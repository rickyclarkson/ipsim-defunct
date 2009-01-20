package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.*;
import fpeas.lazy.Lazy;

public class CardTests
{
	public static Lazy<Boolean> testDrivers()
	{
		return new Lazy<Boolean>()
		{
			public Boolean invoke()
			{
				final Network network=new Network(null);
				final NetworkContext context=network.contexts.append(new NetworkContext());

				final Computer computer=context.visibleComponents.prependIfNotPresent(new Computer(PositionOrParent.position(new Point(300, 300))));
				context.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(PositionOrParent.parent(computer)))).installDeviceDrivers(network);

				final String saved=saveToString(network);
				loadFromString(network, saved);
				return !getAllCardsWithDrivers(network).isEmpty();
			}

			@Override
			public String toString()
			{
				return "CardTest.testDrivers";
			}
		};
	}
}
*/