package ipsim.gui;
/*
import static fpeas.maybe.MaybeUtility.just;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import fpeas.sideeffect.*;
import static ipsim.gui.Global.*;
import static ipsim.gui.NetworkViewMouseListenerUtility.*;
import static ipsim.gui.ObjectRenderer.*;
import ipsim.gui.event.*;
import static ipsim.lang.Predicates.*;
import ipsim.lang.*;
import static ipsim.network.PacketSourceUtility.*;
import ipsim.network.connectivity.*;
import ipsim.property.*;
import ipsim.util.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class NetworkView extends JPanel
{
	public final Property<Boolean> ignorePaints=PropertyUtility.newProperty(false);

	public final Runnable repaintRef=new Runnable()
	{
		public void run()
		{
			repaint();
		}
	};

	public final Toggle toggleListeners=new Toggle()
	{
		public MouseListener[] mouseListeners=null;
		public MouseMotionListener[] motionListeners=null;

		public void off()
		{
			mouseListeners=getMouseListeners();

			motionListeners=getMouseMotionListeners();

			for (final MouseListener listener : mouseListeners)
				removeMouseListener(listener);

			for (final MouseMotionListener listener : motionListeners)
				removeMouseMotionListener(listener);
		}

		public void on()
		{
			for (final MouseListener listener : mouseListeners)
				addMouseListener(listener);

			for (final MouseMotionListener listener : motionListeners)
				addMouseMotionListener(listener);
		}
	};

	public NetworkView()
	{
		ignorePaints.addPropertyListener(SideEffects.<Boolean>fromRunnable(repaintRef));
		setOpaque(true);
		setBackground(Color.white);

		MouseInputListener listener=createNetworkViewMouseListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	@Override
	public void paintComponent(final Graphics originalGraphics)
	{
		super.paintComponent(originalGraphics);

		if (ignorePaints.get())
			return;

		//final Graphics graphics=originalGraphics.create();

		final Graphics2D g2d=(Graphics2D)originalGraphics; //graphics;

		final AffineTransform transform=g2d.getTransform();
		final double zoomLevel=network.zoomLevel.get();
		transform.scale(zoomLevel, zoomLevel);
		g2d.setTransform(transform);

		/*
		 * EthernetCables get drawn first, and hence do not appear above the cards and hubs that they are connected to.
		* /

		final int index=tabbedPane.indexOfComponent(this);

		if (index>=network.contexts.size())
                    return;

		final SideEffect<PacketSource> render=render(network, this, g2d);

		final Stream<PacketSource> components=network.contexts.get().get(index).visibleComponents.get();

		components.only(isCableRef).foreach(render);

		components.only(neither(isCardRef, isCableRef)).foreach(render);

		components.only(isCardRef).foreach(render);
	}

	public final MouseTracker mouseTracker=new MouseTracker()
	{
		public Maybe<Integer> x=nothing();
		public Maybe<Integer> y=nothing();
		public Maybe<MouseEvent> lastMousePressedEvent=nothing();

		public void mouseEvent(final MouseEvent event)
		{
			x=just(event.getX());
			y=just(event.getY());

			if (MouseEvent.MOUSE_PRESSED==event.getID())
				lastMousePressedEvent=just(event);
		}

		public Maybe<Integer> getX()
		{
			return x;
		}

		public Maybe<Integer> getY()
		{
			return y;
		}

		public Maybe<MouseEvent> getLastMousePressedEvent()
		{
			return lastMousePressedEvent;
		}
	};
}
*/