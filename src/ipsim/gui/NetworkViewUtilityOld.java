package ipsim.gui;
/*
import ipsim.awt.Point;
import ipsim.network.*;
import static ipsim.network.Position.*;
import ipsim.network.connectivity.*;
import ipsim.*;
import org.jetbrains.annotations.*;

import java.awt.*;

/**
 * Class which is responsible for the main display - i.e., the network, and the first stage in user interaction with the network.
 * /
public final class NetworkViewUtility
{

	/**
	 * Searches the network to find the point that is closest to (x,y).
	 * /
	public static PacketSource getPointAt(final Network network,final NetworkContext context, final int x, final int y)
	{
		final Iterable<PacketSource> iterable=network.getDepthFirstIterable(context);

		PacketSource answer=null;

		double distance=Double.MAX_VALUE;

		for (final PacketSource next : iterable)
		{
			final Point middle=centreOf(network,next);

			final double xDistance=middle.x()-x;
			final double yDistance=middle.y()-y;

			final double newDistance=Math.sqrt(xDistance*xDistance+yDistance*yDistance);

			if (newDistance<distance)
			{
				distance=newDistance;
				answer=next;
			}
		}

		return answer;
	}

	public static final class PointRecordDead
	{
		public int index;
		public PacketSource object=null;

		/**
		 * A utility constructor to provide easy initialisation of a PointRecord.
		 * /
		PointRecordDead(final int index)
		{
			this.index=index;
		}
	}

	@Nullable
	public static PointRecordDead getTopLevelPointAt(final Network network, final NetworkContext context,final int x, final int y)
	{
		final PointRecordDead answer=new PointRecordDead(-1);

		final Iterable<PacketSource> children=network.getDepthFirstIterable(context);

		double distance=Double.MAX_VALUE;
		for (final PacketSource nextObject : children)
		{
			final int numPositions=nextObject.numPositions();

			for (int a=0;a<numPositions;a++)
			{
				if (nextObject.positionData(a).parent!=null)
					continue;

				final Point position=getPosition(network,nextObject,a);

				final double xDistance=position.x()-x;
				final double yDistance=position.y()-y;

				final double newDistance=Math.sqrt(xDistance*xDistance+yDistance*yDistance);

				if (newDistance<distance)
				{
					distance=newDistance;
					answer.index=a;
					answer.object=nextObject;
				}
			}
		}

		if (answer.object==null || -1==answer.index)
			return null;

		return answer;
	}

	public static Dimension getUnzoomedPreferredSize(final Network network,final NetworkView view)
	{
		final Iterable<PacketSource> iterable=NetworkUtility.getDepthFirstIterable(network);

		final Dimension visibleSize=view.getVisibleRect().getSize();

		int maximumX=visibleSize.width;
		int maximumY=visibleSize.height;

		for (final PacketSource component : iterable)
		{
			final int numPositions=component.numPositions();

			for (int a=0;a<numPositions;a++)
			{
				final Point position=getPosition(network,component,a);

				if (position.x()>maximumX)
					maximumX=(int)position.x();

				if (position.y()>maximumY)
					maximumY=(int)position.y();
			}
		}

		return new Dimension(maximumX, maximumY);
	}

	public static Dimension getPreferredSizeWithBuffer(final Network network)
	{
		final Iterable<PacketSource> iterable=NetworkUtility.getDepthFirstIterable(network);

		final double zoomLevel=network.zoomLevel.get();
		int maximumX=0;
		int maximumY=0;

		for (final PacketSource component : iterable)
		{
			final int numPositions=component.numPositions();

			for (int a=0;a<numPositions;a++)
			{
				final Point position=getPosition(network,component,a);

				if (position.x()+200>maximumX)
					maximumX=(int)(position.x()+200);

				if (position.y()+200>maximumY)
					maximumY=(int)(position.y()+200);
			}
		}

		return new Dimension((int)(maximumX*zoomLevel), (int)(maximumY*zoomLevel));
	}

	public static Dimension getPreferredSize(final Network network)
	{
		return getPreferredSizeWithBuffer(network);
	}
}*/