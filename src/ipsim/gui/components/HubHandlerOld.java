package ipsim.gui.components;
/*
import static ipsim.gui.Global.networkView;
import fpeas.sideeffect.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.awt.Point;
import ipsim.gui.*;
import static ipsim.gui.components.ContextMenuUtility.*;
import ipsim.network.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.hub.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class HubHandler
{
	public static final ImageIcon icon=new ImageIcon(HubHandler.class.getResource("/images/hub.png"));

	public static void render(final Network network, final NetworkView view,final Hub hub,final Graphics2D graphics)
	{
		if (hub.numPositions()==0)
			return;

		final Point position=getPosition(network,hub,0);

		final Image hubImage=icon.getImage();

		final int imageWidth=hubImage.getWidth(null);
		final int imageHeight=hubImage.getHeight(null);

		graphics.drawImage(hubImage,(int)position.x()-imageWidth/2,(int)position.y()-imageHeight/2, view);

		final Color originalColor=graphics.getColor();

		graphics.setColor(Color.green.brighter());

		if (hub.isPowerOn())
			graphics.fillOval((int)position.x()-imageWidth/3-4,(int)position.y()-imageHeight/3+4,8,8);

		graphics.setColor(originalColor);
	}

	public static JPopupMenu createContextMenu(final JFrame frame, final Network network, final NetworkView view, final Hub hub)
	{
		final JPopupMenu menu=new JPopupMenu();

		menu.add(item("Delete",'D', new Runnable()
		{
			public void run()
			{
				if (confirm(frame,"Really delete this hub?"))
				{
					network.log.add("Deleted "+PacketSourceUtility.asString(network, hub)+'.');
					network.delete(hub);
					network.modified=true;
					view.repaint();
				}
			}
		}));

		final JRadioButtonMenuItem powerItem=new JRadioButtonMenuItem("Toggle Power");
		powerItem.setMnemonic('T');
		powerItem.setSelected(hub.isPowerOn());

		powerItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				Props.setProperty(network, networkView(),hub,hub.power,powerItem.isSelected());
			}
		});

		menu.add(powerItem);

		return menu;
	}

	public static SideEffect<Hub> renderRef(final Network network, final NetworkView view,final Graphics2D graphics)
	{
		return new SideEffect<Hub>()
		{
			public void run(final Hub hub)
			{
				render(network,view,hub,graphics);
			}
		};
	}
}
*/