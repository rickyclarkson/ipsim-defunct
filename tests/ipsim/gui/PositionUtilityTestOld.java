package ipsim.gui;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import fpeas.lazy.Lazy;
import ipsim.*;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.lang.*;
import static ipsim.lang.Assertion.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import fpeas.lazy.Lazy;
import static ipsim.util.Collections.*;

public final class PositionUtilityTest
{
	public static final Lazy<Boolean> testRetention=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Network network=new Network();
			final NetworkContext context=network.contexts.append(new NetworkContext());

			final Computer computer=context.visibleComponents.prependIfNotPresent(new Computer(position(new Point(0, 0))).withID(network.generateComputerID()));
			final Card card=context.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(parent(computer))));

			return asNotNull(card.positionData(0).parent).equals(computer);
		}

		@Override
		public String toString()
		{
			return "test retention";
		}
	};

	public static final Lazy<Boolean> testRetention2=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Network network=new Network(null);
			final NetworkContext context=network.contexts.prepend(new NetworkContext());
			final Cable cable=context.visibleComponents.prepend(new Cable(position(new Point(0, 0)), position(new Point(50, 0))));

			Point point1=new Point(0, 0);
			final Card card1=context.visibleComponents.prepend(new Card(false, position(point1)));

			final Card card2=context.visibleComponents.prepend(new Card(false, position(point1)));

			card1.setPositionData(0, position(new Point((double)200, (double)200)));
			final Point point=PositionUtility.getPosition(network,card1,0);

			Assertion.assertTrue(Maths.approxEqual(point.x(),200));
			card2.setPositionData(0, position(new Point((double)300, (double)300)));

			cable.setPositionData(0, parent(card1));

			cable.setPositionData(1,parent(card2));

			final Boolean equal1=cable.positionData(0).parent.equals(card1);
			return equal1 && cable.positionData(1).parent.equals(card2);
		}

		@Override
		public String toString()
		{
			return "test retention 2";
		}
	};

	public static final Lazy<Boolean> setParentTwice=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Network network=new Network(null);
			final NetworkContext context=network.contexts.prepend(new NetworkContext());

			Point point=new Point(5, 5);
			final Card card=context.visibleComponents.prepend(new Card(false, position(point)));
			final Hub hub=context.visibleComponents.prepend(new Hub(position(new Point(10, 10))));
			final Cable cable=context.visibleComponents.prepend(new Cable(position(new Point(20, 20)), position(new Point(40, 40))));

			cable.setPositionData(0,asNotNull(parent(card)));
			cable.setPositionData(0,asNotNull(parent(hub)));

			return asNotNull(cable.positionData(0).parent).equals(hub);
		}

		@Override
		public String toString()
		{
			return "setParentTwice";
		}
	};

	public static final Lazy<Boolean> cableWithTwoEnds=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Network network=new Network(null);
			final NetworkContext context=network.contexts.append(new NetworkContext());

			final Computer computer1=context.visibleComponents.prependIfNotPresent(new Computer(position(new Point(50, 50))));
			final Computer computer2=context.visibleComponents.prependIfNotPresent(new Computer(position(new Point(100, 100))));

			final Card card1=context.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(parent(computer1))));
			final Card card2=context.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(parent(computer2))));

			final Cable cable=context.visibleComponents.prependIfNotPresent(new Cable(asNotNull(parent(card1)),asNotNull(parent(card2))));
			final Predicate<PacketSource> equalT=Caster.<PacketSource>equalT(cable);

			assertTrue(any(getDepthFirstIterable(network), equalT));
			return asNotNull(cable.positionData(0).parent).equals(card1) && asNotNull(cable.positionData(1).parent).equals(card2);
		}

		@Override
		public String toString()
		{
			return "PositionUtilityTest.cableWithTwoEnds";
		}
	};
}
*/