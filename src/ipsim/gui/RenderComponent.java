package ipsim.gui;

import fpeas.sideeffect.*;
import static ipsim.gui.components.CardHandler.*;
import ipsim.gui.components.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;

import java.awt.*;

public class RenderComponent
{
	public static void renderComponent(final Network network,final NetworkView view,final PacketSource component, final Graphics2D graphics)
	{
		final PacketSourceVisitor2 v=new PacketSourceVisitor2();

		v.cardEffect=renderRef(network,view,graphics);
		v.computerEffect=ComputerHandler.renderRef(network,view,graphics);
		v.cableEffect=EthernetCableHandler.renderRef(network,graphics);
		v.hubEffect=HubHandler.renderRef(network,view,graphics);

		component.accept(v);
	}

	public static SideEffect<Card> renderRef(final Network network, final NetworkView view, final Graphics2D graphics)
	{
		return new SideEffect<Card>()
		{
			public void run(final Card card)
			{
				render(network,view,card,graphics);
			}
		};
	}
}