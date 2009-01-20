package ipsim.gui;

import fpeas.sideeffect.*;
import ipsim.awt.Point;
import ipsim.awt.*;
import static ipsim.awt.Point$.*;
import ipsim.network.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.*;
import ipsim.util.Collections;

import java.awt.*;
import java.util.*;

public class ObjectRenderer
{
	public static SideEffect<PacketSource> render(final Network network,final NetworkView view,final Graphics2D graphics)
	{
		return new SideEffect<PacketSource>()
		{
			public void run(final PacketSource packetSource)
			{
				render(network,view,packetSource,graphics);
			}
		};
	}

	public static void render(final Network network,final NetworkView view,final PacketSource component,final Graphics2D graphics)
	{
		final Map<RenderingHints.Key,Object> map=Collections.hashMap();

		map.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setRenderingHints(map);
		RenderComponent.renderComponent(network,view,component,graphics);
	}

	public static boolean isNear(Network network,final PacketSource componentHandler1, final int pointIndex1, final PacketSource componentHandler2, final int pointIndex2)
	{
		return isNear(getPosition(network,componentHandler1,pointIndex1), getPosition(network,componentHandler2,pointIndex2));
	}

	public static boolean isNear(final Point point1,final Point point2)
	{
		return Math.abs(point1.x()-point2.x())<45&&Math.abs(point1.y()-point2.y())<45;
	}

	public static Point getCentre(Network network,final PacketSource component)
	{
		final int numPositions=component.numPositions();
		Point total=new Point((double)0, (double)0);

		for (int a=0;a<numPositions;a++)
                    total=total.$plus(getPosition(network,component,a));

		return total.$div(numPositions);
	}
}
