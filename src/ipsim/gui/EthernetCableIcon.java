package ipsim.gui;
/**
import fpeas.maybe.*;
import fpeas.sideeffect.*;
import ipsim.*;
import ipsim.awt.Point;
import static ipsim.gui.Global.*;
import ipsim.gui.components.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public final class EthernetCableIcon
{
	public static JToggleButton newButton()
	{
            final JToggleButton button=new JToggleButton("Ethernet Cable", EthernetCableHandler.icon());

		final MouseInputAdapter listener=new MouseInputAdapter()
		{
			public Maybe<Point> startDrag=MaybeUtility.nothing();

			public MouseEvent zoomMouseEvent(final MouseEvent event)
			{
                            return new MouseEvent(event.getComponent(), event.getID(), event.getWhen(), event.getModifiers(), (int)((double)event.getX()/network().zoomLevel()), (int)((double)event.getY()/network().zoomLevel()), event.getClickCount(), event.isPopupTrigger(), event.getButton());
			}

			/**
			 * Keeps track of where the mouse drag was started from, and renders the
			 * object that was closest to the starting position, giving the
			 * impression that the object (or part of it) is being dragged across
			 * the display.
			 * /
			@Override
			public void mouseDragged(final MouseEvent originalEvent)
			{
				super.mouseDragged(originalEvent);

				final MouseEvent event=zoomMouseEvent(originalEvent);

				final NetworkView view=networkView();
				view.mouseTracker().mouseEvent(event);

				view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

				if (!MaybeUtility.isJust(startDrag))
					startDrag=MaybeUtility.just(new Point((double)event.getX(), (double)event.getY()));

				final Rectangle visibleRect=view.getVisibleRect();

				int changedX=0;
				if (originalEvent.getX()>=visibleRect.x+visibleRect.width-10)
				{
					changedX=1;
					visibleRect.x+=10;
				}

				int changedY=0;
				if (originalEvent.getY()>=visibleRect.y+visibleRect.height-10)
				{
					changedY=1;
					visibleRect.y+=10;
				}

				if (originalEvent.getX()<=visibleRect.x+10)
				{
					changedX=-1;
					visibleRect.x-=10;
				}

				if (originalEvent.getY()<=visibleRect.y+10)
				{
					changedY=-1;
					visibleRect.y-=10;
				}

				if (!(0==changedX) || !(0==changedY))
				{
                                    Positions.translateAllWhenNecessary(visibleRect, network());

                                    view.scrollRectToVisible(visibleRect);
				}

				view.invalidate();
				view.validate();
				view.repaint();
			}

			@Override
			public void mousePressed(final MouseEvent originalEvent)
			{
				super.mousePressed(originalEvent);
				final MouseEvent event=zoomMouseEvent(originalEvent);

				networkView().mouseTracker().mouseEvent(event);

				final Point point=new Point((double)event.getX(), (double)event.getY());

				if (event.getButton()==MouseEvent.BUTTON1)
					startDrag=MaybeUtility.just(point);
			}

			/**
			 * Redraws the display, and loses the maintained information about where
			 * the drag was started from (to allow a new drag to start).
			 * /
			@Override
			public void mouseReleased(final MouseEvent originalEvent)
			{
				super.mouseReleased(originalEvent);

				final MouseEvent event=zoomMouseEvent(originalEvent);

				networkView().mouseTracker().mouseEvent(event);

				final NetworkContext context=Global.networkContext();

				final boolean mouseDragOccurred=true;
				if (mouseDragOccurred)
					MaybeUtility.run(startDrag, new SideEffect<Point>()
					{
						public void run(final Point point)
						{
                                                    network().modified_$eq(true);

                                                    final PacketSource cable=new Cable(point, new Point((double)event.getX(), (double)event.getY()));

                                                    button.doClick();

                                                    ComponentMoved.componentMoved(cable, new int[]{0, 1}, network(), context);
						}
					});
				
				startDrag=MaybeUtility.nothing();
				networkView().repaint();
			}
		};

		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				final NetworkView view=networkView();

				if (button.isSelected())
				{
                                    view.toggleListeners().off();

					view.addMouseListener(listener);
					view.addMouseMotionListener(listener);

					view.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				else
				{
					view.removeMouseListener(listener);
					view.removeMouseMotionListener(listener);

					view.toggleListeners().on();

					view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});

		button.setToolTipText("Click on this to draw an Ethernet Cable, then drag on the display to make one appear");
		return button;
	}
}*/