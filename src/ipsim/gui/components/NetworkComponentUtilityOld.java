package ipsim.gui.components;
/*
import static ipsim.Caster.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import org.jetbrains.annotations.*;

public final class NetworkComponentUtility
{
    public static PacketSource create(final Network network,final NetworkContext context, final Function<Pair<Point, Point>, PacketSource> clazz, final Point point0, final Point point1)
	{
            network.modified_$eq(true);
            final PacketSource component = clazz.invoke(PairUtility.pair(point0, point1));
            context.visibleComponents.prependIfNotPresent(component);
            network.log.add("Created "+asString(network,component)+'.');
		return asNotNull(component);
	}

	public static String pointsToStringWithoutDelimiters(final Network network,final PacketSource component)
	{
		final int length=component.numPositions();

		if (length==1)
		{
			@Nullable
			final PacketSource parent=component.positionData(0).parent;

			if (parent==null)
				return "at "+getPosition(network,component,0).toString();

			return "connected to "+asString(network,parent);
		}

		if (length==0 || length>2)
			throw new UnsupportedOperationException("Can't deal with a length of "+length+" here.");

		return "from "+getPositionOrParentAsString(network,component,0)+" to "+getPositionOrParentAsString(network,component,1);
	}

	private static String getPositionOrParentAsString(Network network,final PacketSource component, final int i)
	{
		final PositionOrParent data=component.positionData(i);
		final PacketSource parent=data.parent;
		return parent==null ? data.point.toString() : asString(network,parent);
	}
}
*/