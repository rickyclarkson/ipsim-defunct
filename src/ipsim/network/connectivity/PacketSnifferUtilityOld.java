package ipsim.network.connectivity;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.swing.*;
import static ipsim.swing.Dialogs.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.event.*;

public final class PacketSnifferUtility
{
	private PacketSnifferUtility()
	{
	}

	public static PacketSniffer instance(final JFrame frame,final Network network,final PacketSource toBeSniffed)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose("Packet Sniffer on "+toBeSniffed, frame);

		final JTextArea output=new JTextArea(5,80);
		ContainerUtility.add(dialog,new JScrollPane(output));
		dialog.pack();
		dialog.setVisible(true);

		final PacketSniffer sniffer=new PacketSniffer()
		{
                    public void packetIncoming(final Packet packet, final PacketSource source, final PacketSource destination, Network network)
                    {
                        final String s="Incoming "+packet+" to "+destination;
                        append(s);                        
                    }

                    private void append(final String string)
                    {
                        output.setText(output.getText()+'\n'+string);
                    }

                    public void packetOutgoing(final Packet packet,final PacketSource source, Network network)
                    {
			final String s="Outgoing "+packet+" from "+source;
			append(s);			
                    }
		};

		dialog.addWindowListener(new WindowListener()
		{
			public void windowActivated(final WindowEvent e)
			{
			}

			public void windowClosing(final WindowEvent e)
			{
                            network.log().add("Removed a packet sniffer from "+toBeSniffed+'.');

				toBeSniffed.incomingPacketListeners().remove(sniffer);
				toBeSniffed.outgoingPacketListeners().remove(sniffer);
			}

			public void windowClosed(final WindowEvent e)
			{
			}

			public void windowDeactivated(final WindowEvent e)
			{
			}

			public void windowDeiconified(final WindowEvent e)
			{
			}

			public void windowIconified(final WindowEvent e)
			{
			}

			public void windowOpened(final WindowEvent e)
			{
			}
		});

		return sniffer;
	}
        }*/