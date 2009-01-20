package ipsim.gui;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import ipsim.awt.Point;
import static ipsim.gui.Global.networkContext;
import static ipsim.gui.Global.networkView;
import ipsim.gui.NetworkView.*;
import ipsim.gui.components.*;
import static ipsim.gui.CreateContextMenu.*;
import ipsim.lang.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.*;
import org.jetbrains.annotations.*;

import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public final class NetworkViewMouseListenerUtility
{
	public static MouseInputListener createNetworkViewMouseListener()
	{
		return new MouseInputListener()
		{
			public PointRecordDead startDrag=null;

			public int changedX=0;

			public int changedY=0;

			public void mouseClicked(final MouseEvent originalEvent)
			{
				final MouseEvent event=zoomMouseEvent(originalEvent);

				networkView().mouseTracker.mouseEvent(event);
			}

			public void popupTriggered(final MouseEvent event)
			{
				if (event.isPopupTrigger())
				{
					final PacketSource component=NetworkViewUtility.getPointAt(Global.network,Global.networkContext(),event.getX(),event.getY());

					if (component!=null)
						InvokeContextMenu.invokeContextMenu(createContextMenu(Global.frame,Global.network,networkView(),networkContext(),component), Global.network,networkView(),component);
				}
			}

			MouseEvent zoomMouseEvent(final MouseEvent event)
			{
				final double zoomLevel=Global.network.zoomLevel.get();

				return new MouseEvent(event.getComponent(),event.getID(),event.getWhen(),event.getModifiers(),(int)(event.getX()/zoomLevel),(int)(event.getY()/zoomLevel),event.getClickCount(),event.isPopupTrigger(),event.getButton());
			}

			/**
			 * Keeps track of where the mouse drag was started from, and renders the object that was closest to the starting position, giving the impression that the object (or part of it) is being dragged across the display.
			 * /
			public void mouseDragged(MouseEvent originalEvent)
			{
				changedX=0;
				changedY=0;

				networkView().mouseTracker.mouseEvent(originalEvent); //swapped this and the next
				originalEvent=zoomMouseEvent(originalEvent);

				if (startDrag==null&& originalEvent.getButton()==MouseEvent.BUTTON1)
					startDrag=NetworkViewUtility.getTopLevelPointAt(Global.network, networkContext(),originalEvent.getX(), originalEvent.getY());

				if (startDrag==null)
					return;

				startDrag.object.setPositionData(startDrag.index, PositionOrParent.position(new Point((double)originalEvent.getX(), (double)originalEvent.getY())));

				final Rectangle visibleRect=networkView().getVisibleRect();

				if (!(changedX==0) ||!(changedY==0))
				{
					translateAllWhenNecessary(Global.network,visibleRect);

					networkView().scrollRectToVisible(visibleRect);
				}

				jiggleLayout();
				networkView().invalidate();
				networkView().validate();
				networkView().repaint();
			}

			public void mouseEntered(final MouseEvent event)
			{
				networkView().mouseTracker.mouseEvent(event);
			}

			public void mouseExited(final MouseEvent event)
			{
				networkView().mouseTracker.mouseEvent(event);
			}

			public void mouseMoved(final MouseEvent event)
			{
				networkView().mouseTracker.mouseEvent(event);
				networkView().repaint();
			}

			public void mousePressed(final MouseEvent originalEvent)
			{
				final MouseEvent event=zoomMouseEvent(originalEvent);

				networkView().mouseTracker.mouseEvent(originalEvent);

				final PointRecordDead point=NetworkViewUtility.getTopLevelPointAt(Global.network,networkContext(),event.getX(),event.getY());

				if (event.isPopupTrigger())
					popupTriggered(event);
				else
					if (event.getButton()==MouseEvent.BUTTON1)
						startDrag=point;

				networkView().requestFocus();
			}

			/**
			 * Redraws the display, and loses the maintained information about where the drag was started from (to allow a new drag to start).
			 * /
			public void mouseReleased(final MouseEvent originalEvent)
			{
				final MouseEvent event=zoomMouseEvent(originalEvent);

				if (event.isPopupTrigger())
				{
					popupTriggered(event);
					return;
				}

				final Maybe<MouseEvent> maybeMousePressedEvent=networkView().mouseTracker.getLastMousePressedEvent();

				final boolean mouseDragOccurred=constIfNothing(maybeMousePressedEvent,false,new Function<MouseEvent,Boolean>()
				{
					@NotNull
					public Boolean run(@NotNull final MouseEvent mousePressedEvent)
					{
						return !(mousePressedEvent.getX()==event.getX()) ||!(mousePressedEvent.getY()==event.getY());
					}
				});

				networkView().mouseTracker.mouseEvent(event);

				if (startDrag!=null && mouseDragOccurred)
				{
					final PacketSource component=startDrag.object;

					ComponentMoved.componentMoved(Global.network,networkContext(),component, startDrag.index);
				}

				jiggleLayout();

				startDrag=null;
			}

			public void jiggleLayout()
			{
				final Dimension preferredSize=NetworkViewUtility.getPreferredSize(Global.network);

				networkView().setPreferredSize(preferredSize);

				final Container scrollPane=networkView().getParent().getParent();
				scrollPane.invalidate();
				scrollPane.validate();

				networkView().repaint();
			}
		};
	}
}*/