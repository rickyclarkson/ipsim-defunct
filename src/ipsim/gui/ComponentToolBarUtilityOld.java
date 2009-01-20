package ipsim.gui;
/*
import anylayout.*;
import static anylayout.extras.ConstraintBuilder.*;
import static anylayout.extras.SizeCalculatorUtility.*;
import fpeas.function.*;
import static ipsim.gui.Global.*;
import static ipsim.gui.NetworkComponentIconMouseListenerUtility.*;
import ipsim.gui.components.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import static ipsim.swing.DragNDropIconCreator.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;
import ipsim.network.*;
import fpeas.lazy.Lazy;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

final class ComponentToolBarUtility
{
	public static Container newBar()
	{
		final JPanel panel=new JPanel();
		final JLabel label=new JLabel("<html><body><h3>Components:</h3></body></html>");
		final JToggleButton cable=EthernetCableIcon.newButton();

		final Container computer=newInstance(ComputerHandler.icon(),"Computer");
		final Container card=newInstance(CardHandler.icon(),"Ethernet Card");
		final Container hub=newInstance(HubHandler.icon(),"Hub");

		AnyLayout.useAnyLayout(panel,0.5f,0.5f,new SizeCalculator()
		{
			public int getHeight()
			{
				return sum(getPreferredHeight(),label,computer,cable,card,hub);
			}

			public int getWidth()
			{
				return max(getPreferredWidth(),label,computer,cable,card,hub);
			}
		},FunctionUtility.<Component,Constraint>throwRuntimeException());

		final Function<LayoutContext,Integer> maxPreferredHeight=new Function<LayoutContext,Integer>()
		{
			@NotNull
			public Integer run(@NotNull final LayoutContext layoutContext)
			{
				final Component[] components={label,computer,cable,card,hub};

				return Math.min(layoutContext.getParentSize()/components.length,max(getPreferredHeight(),components));
			}
		};

		final Function<LayoutContext,Integer> zero=FunctionUtility.constant(0);

		panel.add(label,buildConstraint().setLeft(zero).setTop(zero).setWidth(fill()).setHeight(maxPreferredHeight));
		panel.add(cable,buildConstraint().setLeft(zero).setTop(after(label)).setWidth(fill()).setHeight(maxPreferredHeight));
		panel.add(computer,buildConstraint().setLeft(zero).setTop(after(cable)).setWidth(fill()).setHeight(maxPreferredHeight));
		panel.add(card,buildConstraint().setLeft(zero).setTop(after(computer)).setWidth(fill()).setHeight(maxPreferredHeight));
		panel.add(hub,buildConstraint().setLeft(zero).setTop(after(card)).setWidth(fill()).setHeight(maxPreferredHeight));

                Lazy<NetworkContext> networkContextRef = new Lazy<NetworkContext>() { public NetworkContext invoke() { return networkContext(); } };

		final MouseInputListener computerListener=createNetworkComponentIconMouseListener(Computer.class,networkContextRef);
		final MouseInputListener cardListener=createNetworkComponentIconMouseListener(Card.class,networkContextRef);
		final MouseInputListener hubListener=createNetworkComponentIconMouseListener(Hub.class,networkContextRef);

		computer.addMouseListener(computerListener);
		computer.addMouseMotionListener(computerListener);

		card.addMouseListener(cardListener);
		card.addMouseMotionListener(cardListener);

		hub.addMouseListener(hubListener);
		hub.addMouseMotionListener(hubListener);
		hub.setPreferredSize(card.getPreferredSize());

		return panel;
	}
        }*/