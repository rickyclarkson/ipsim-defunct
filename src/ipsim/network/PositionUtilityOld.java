package ipsim.network;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import fpeas.lazy.Lazy;
import static ipsim.Caster.*;
import ipsim.awt.Point;
import ipsim.gui.*;
import ipsim.lang.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import fpeas.lazy.Lazy;
import java.awt.*;
import java.util.*;

public final class PositionUtility
{
	public static final Lazy<Boolean> testSetPosition=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Cable cable=new Cable(new Point(50, 50), new Point(100, 100));
			return Math.abs(asNotNull(getPosition(null, cable, 0).x())-50)<0.005;
		}
	};

	public static Point centreOf(Network network, final PacketSource component)
	{
		final Map<Integer, Point> results=hashMap();

		for (int a=0;a<component.positions().size();a++)
			results.put(a, getPosition(network, component, a));

		int totalX=0;
		int totalY=0;

		for (final Point point : results.values())
		{
			totalX+=point.x();
			totalY+=point.y();
		}

		return new Point((double)totalX/results.size(), (double)totalY/results.size());
	}

	public static void translateAllWhenNecessary(final Network network, final Rectangle visibleRect)
	{
		if (visibleRect.x<0 || visibleRect.y<0)
			translateAll(network, -Math.min(visibleRect.x, 0), -Math.min(visibleRect.y, 0));
	}

	private static void translateAll(final Network network, final int x, final int y)
	{
		final Point toAdd=new Point(x, y);

		network.getAll().foreach(new SideEffect<PacketSource>()
		{
			public void run(final PacketSource p)
			{
                            for (int a=0;a<p.positions().size();a++)
                                if (p.positions().apply(a).parent==null)
						p.setPositionData(a, position(getPosition(network, p, a).$plus(toAdd)));
			}
		});
	}

	@NotNull
	public static Stream<PacketSource> children(final Stream<PacketSource> all, final PacketSource component)
	{
		return all.only(new Predicate<PacketSource>()
		{
			public boolean invoke(final PacketSource packetSource)
			{
				for (int a=0;a<packetSource.numPositions();a++)
					if (component.equals(packetSource.positionData(a).parent))
						return true;

				return false;
			}
		});
	}

	@NotNull
	public static Point getPosition(final Network network, PacketSource component, int index)
	{
		final PositionOrParent data=component.positionData(index);
		return data.point==null ? GetChildOffset.getChildOffset(network, data.parent, component).$plus(getPosition(network, data.parent, 0)) : data.point;
	}
        }*/