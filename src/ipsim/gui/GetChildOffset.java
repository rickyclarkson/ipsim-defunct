package ipsim.gui;

import fpeas.function.*;
import ipsim.awt.*;
import static ipsim.lang.Comparators.*;
import ipsim.network.*;
import static ipsim.network.PacketSourceUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import static ipsim.network.connectivity.card.Card.*;
import static ipsim.network.connectivity.card.CardDrivers.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;

public class GetChildOffset
{
	public static Point getChildOffset(final Network network,final PacketSource parent, final PacketSource child)
	{
		return parent.accept(new PacketSourceVisitor<Point>()
		{
			public Point visit(final Card card)
			{
                            return PointUtility.origin();
			}

			public Point visit(final Computer computer)
			{
				final Function<Stream<Card>, Stream<Card>> id=FunctionUtility.identity();
				final Function2<Stream<Card>, Stream<Card>, Stream<Card>> concat=Stream.concat();
				final Function<Stream<Card>, Stream<Card>> sortByEthNo=sortByRef(fromFunction(withDriversRef.then(ethNumberRef)));

				final Stream<Card> cards=computer.getCards(network).partition(hasDrivers).map(sortByEthNo,id).fold(concat);

				final int index=cards.indexOf(asCard(child));
				final double angle=index*2*Math.PI/cards.size();

				return new Point(50*Math.cos(angle), -50*Math.sin(angle));
			}

			public Point visit(final Cable cable)
			{
				throw new UnsupportedOperationException();
			}

			public Point visit(final Hub hub)
			{
                            return PointUtility.origin();
			}
		});
	}
}