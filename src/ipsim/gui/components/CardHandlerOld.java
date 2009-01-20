package ipsim.gui.components;
/*
import fpeas.function.*;
import static fpeas.function.math.Doubles.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import ipsim.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.awt.Point;
import ipsim.gui.*;
import static ipsim.gui.components.ContextMenuUtility.*;
import ipsim.gui.event.*;
import ipsim.lang.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.textmetrics.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;

public final class CardHandler
{
	public static final ImageIcon icon=new ImageIcon(CardHandler.class.getResource("/images/card.png"));

	public static void render(final Network network, final NetworkView networkView,final Card card, final Graphics2D graphics)
	{
		final Point position=getPosition(network,card,0);

		final Image cardImage=icon.getImage();

		final int imageWidth=cardImage.getWidth(null)/2;

		final int imageHeight=cardImage.getHeight(null)/2;

		graphics.drawImage(cardImage, (int)position.x()-imageWidth, (int)position.y()-imageHeight, networkView);

		final MouseTracker mouseTracker=networkView.mouseTracker;

		final Maybe<Double> mouseX=bind2(mouseTracker.getX(), divideBy(network.zoomLevel.get()));
		final Maybe<Double> mouseY=bind2(mouseTracker.getY(), divideBy(network.zoomLevel.get()));

		final Function<Double, Double> identity=FunctionUtility.identity();

		boolean mouseIsNear=constIfNothing(mouseX, false, lessThan(abs(minus(identity, position.x())), 40));
		mouseIsNear&=constIfNothing(mouseY, false, lessThan(abs(minus(identity, position.y())), 40));

		// look at parent to find out what index card has
		if (card.positionData(0).parent!=null && card.withDrivers!=null && mouseIsNear)
			TextMetrics.drawString(graphics, "Card "+card.withDrivers.ethNumber, (int)position.x(), (int)position.y()+imageHeight/2+5, HorizontalAlignment.CENTRE, VerticalAlignment.TOP, true);
	}

	public static void componentMoved(final Network network, final NetworkContext context,final Card card, final int pointIndex)
	{
		// An EthernetCard can be attached to a Computer.
		// Is there an Computer 'near' the moved point?

		final Iterable<PacketSource> childNodes=NetworkUtility.getDepthFirstIterable(network,context);

		for (final PacketSource component2 : childNodes)
		{
			if (!PacketSourceUtility.isComputer(component2))
				continue;

			final int size=component2.numPositions();

			for (int a=0;a<size;a++)
				if (ObjectRenderer.isNear(network,card, pointIndex, component2, a))
				{
					card.setPositionData(a, Caster.asNotNull(parent(component2)));
					return;
				}
		}
	}

	public static JPopupMenu createContextMenu(final JFrame frame, final Network network, final NetworkView networkView, final Card card)
	{
		final JPopupMenu menu=new JPopupMenu();
		menu.add(item("Install/Uninstall Device Drivers", 'I', new Runnable()
		{
			public void run()
			{
				if (card.withDrivers!=null)
					card.uninstallDeviceDrivers();
				else
					card.installDeviceDrivers(network);

				networkView.repaint();
			}
		}));
		menu.add(item("Disconnect cable from card", 'C', new Runnable()
		{
			public void run()
			{
				@Nullable final Cable cable=card.getCable(network);

				if (cable==null)
				{
					errors(frame,"There is no cable to disconnect");
					return;
				}

				cable.setPositionData(0,PositionOrParent.position(getPosition(network,cable.positionData(0).parent,0)));
				cable.setPositionData(1,PositionOrParent.position(getPosition(network,cable.positionData(1).parent,0)));
			}
		}));

		menu.add(item("Delete", 'D', new Runnable()
		{
			public void run()
			{
				if (card.withDrivers!=null)
					NetworkContext.errors(frame,"You must remove the drivers first");
				else
				{
					network.log.add("Deleted "+PacketSourceUtility.asString(network, card)+'.');
					network.delete(card);
					networkView.repaint();
				}
			}
		}));

		return menu;
	}

	public static Function<Integer, Double> divideBy(final double divisor)
	{
		return new Function<Integer, Double>()
		{
			@NotNull
			public Double run(@NotNull final Integer divisible)
			{
				return divisible/divisor;
			}
		};
	}
}
*/