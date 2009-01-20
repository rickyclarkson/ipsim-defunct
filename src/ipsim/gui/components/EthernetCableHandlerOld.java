package ipsim.gui.components;
/*
import fpeas.sideeffect.*;
import static ipsim.Caster.*;
import ipsim.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.awt.Point;
import ipsim.gui.*;
import static ipsim.gui.ObjectRenderer.*;
import static ipsim.gui.components.ContextMenuUtility.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.Positions.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class EthernetCableHandler
{
	public static final ImageIcon icon=new ImageIcon(EthernetCableHandler.class.getResource("/images/cable.png"));

	public static void render(Network network,final Cable cable, final Graphics2D graphics2d)
	{
		final Stroke initialStroke=graphics2d.getStroke();
		final Color initialColor=graphics2d.getColor();

		graphics2d.setColor(Color.gray.brighter());

		graphics2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		final Point position1=asNotNull(getPosition(network,cable,0));
		final Point position2=asNotNull(getPosition(network,cable,1));

		graphics2d.drawLine((int)position1.x(), (int)position1.y(), (int)position2.x(), (int)position2.y());

		graphics2d.setStroke(initialStroke);
		graphics2d.setColor(initialColor);
	}

	public static void componentMoved(final NetworkContext context,final Network network, final Cable cable, final int... points)
	{
		for (final int pointIndex : points)
			for (final PacketSource next : context.visibleComponents.get())
			{
				if (!(PacketSourceUtility.isCard(next) || PacketSourceUtility.isHub.invoke(next)))
					continue;

				if (isNear(network,cable, pointIndex, next, 0))
				{
					final PositionOrParent positionData=parent(next);
					cable.setPositionData(pointIndex, positionData);
				}
			}
	}

	public static JPopupMenu createContextMenu(final JFrame frame,final Network network,final NetworkContext context,final Cable cable)
	{
		final JPopupMenu menu=new JPopupMenu();

		menu.add(item("Delete", 'D', new Runnable()
		{
			public void run()
			{
				if (confirm(frame,"Really delete this Ethernet cable?"))
				{
					network.log.add("Deleted "+PacketSourceUtility.asString(network, cable)+'.');

					context.visibleComponents.remove(cable);
					network.modified=true;
				}
			}
		}));

		menu.add(item("Test Cable", 'T', new Runnable()
		{
			public void run()
			{
				final String result=cable.getCableType().toString()+" cable.";

				network.log.add("Tested a cable, result: "+result);

				UserMessages.message(result);
			}
		}));

		menu.add(item("Change Cable Type", 'C', new Runnable()
		{
			public void run()
			{
				final JList list=new JList();
				list.setListData(new Vector<CableType>(Arrays.asList(CableType.STRAIGHT_THROUGH, CableType.CROSSOVER)));

				if (cable.getCableType().equals(CableType.BROKEN))
					list.clearSelection();
				else
					list.setSelectedValue(cable.getCableType(), true);

				final int result=JOptionPane.showConfirmDialog(frame, list, "Cable Type", JOptionPane.OK_CANCEL_OPTION);

				final CableType cableType=(CableType)list.getSelectedValue();
				if (result==JOptionPane.OK_OPTION && cableType!=null)
					cable.setCableType(cableType);
			}
		}));

		return menu;
	}

	public static SideEffect<Cable> renderRef(final Network network,final Graphics2D graphics)
	{
		return new SideEffect<Cable>()
		{
			public void run(final Cable cable)
			{
				render(network,cable,graphics);
			}
		};
	}
}
*/