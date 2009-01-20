package ipsim.gui.components;
/*
import fpeas.sideeffect.*;
import ipsim.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import org.jetbrains.annotations.*;

public class ComponentMoved
{
	public static void componentMoved(final Network network,final NetworkContext context, final PacketSource component, final int... points)
	{
		final PacketSourceVisitor2 v=new PacketSourceVisitor2();

		v.cardEffect=new SideEffect<Card>()
		{
			public void run(final Card card)
			{
				@Nullable
				final PacketSource parent=card.positionData(0).parent;

				CardHandler.componentMoved(network,context, card, points[0]);

				if (parent!=null)
					network.log.add("Connected "+PacketSourceUtility.asString(network, card)+" to "+PacketSourceUtility.asString(network, parent)+'.');
			}
		};

		v.cableEffect=new SideEffect<Cable>()
		{
			public void run(final Cable cable)
			{
				EthernetCableHandler.componentMoved(context, network,cable, points);
			}
		};

		component.accept(v);
	}
        }*/