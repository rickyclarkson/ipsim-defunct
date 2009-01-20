package ipsim.gui.components;
/*
import ipsim.*;
import ipsim.gui.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;

import javax.swing.*;

public class CreateContextMenu
{
	public static JPopupMenu createContextMenu(final JFrame frame,final Network network,final NetworkView view,final NetworkContext context,final PacketSource component)
	{
		return component.accept(new PacketSourceVisitor<JPopupMenu>()
		{
			public JPopupMenu visit(final Card card)
			{
				return CardHandler.createContextMenu(frame,network, view,card);
			}

			public JPopupMenu visit(final Computer computer)
			{
				return ComputerHandler.createContextMenu(frame,network,view,context,computer);
			}

			public JPopupMenu visit(final Cable cable)
			{
				return EthernetCableHandler.createContextMenu(frame,network,context,cable);
			}

			public JPopupMenu visit(final Hub hub)
			{
				return HubHandler.createContextMenu(frame,network, view, hub);
			}
		});
	}
        }*/