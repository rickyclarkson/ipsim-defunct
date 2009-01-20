package ipsim.gui;

import fpeas.lazy.*;
import ipsim.network.*;
import ipsim.awt.Point;
import static ipsim.gui.Global.network;
import static ipsim.gui.Global.*;
import ipsim.gui.components.*;
import ipsim.network.connectivity.*;

import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import fpeas.function.Function;
import fpeas.pair.Pair;
import static fpeas.pair.PairUtility.pair;

final class NetworkComponentIconMouseListenerUtility
{
    public static MouseInputListener createNetworkComponentIconMouseListener(final Function<Pair<Point, Point>, PacketSource> name,final Lazy<NetworkContext> context)
	{
		final Cursor cursor=new Cursor(Cursor.MOVE_CURSOR);

		return new MouseInputListener()
		{
			public void mouseDragged(final MouseEvent event)
			{
				networkView().setCursor(cursor);
			}

			public void mouseReleased(final MouseEvent event)
			{
				final java.awt.Point buttonLocation=event.getComponent().getLocationOnScreen();

				final Component view=networkView();

				if (!view.isShowing())
					return;

				final java.awt.Point viewLocation=view.getLocationOnScreen();

				int x=event.getX()+buttonLocation.x-viewLocation.x;
				int y=event.getY()+buttonLocation.y-viewLocation.y;

				x=Math.max(x,10);
				y=Math.max(y,10);

				x=Math.min(x,view.getWidth()-10);
				y=Math.min(y,view.getHeight()-10);

				x/=network().zoomLevel();
				y/=network().zoomLevel();

				final PacketSource component=NetworkComponentUtility.create(name.run(pair(new Point(x, y), new Point(x, y))), networkContext(), network());

				ComponentMoved.componentMoved(component, new int[]{0}, network(),context.invoke());

				view.paint(view.getGraphics());
				view.repaint();

				view.setCursor(Cursor.getDefaultCursor());
			}

			public void mouseClicked(final MouseEvent e)
			{
			}

			public void mousePressed(final MouseEvent e)
			{
			}

			public void mouseEntered(final MouseEvent e)
			{
			}

			public void mouseExited(final MouseEvent e)
			{
			}

			public void mouseMoved(final MouseEvent e)
			{
			}
		};
	}
}
