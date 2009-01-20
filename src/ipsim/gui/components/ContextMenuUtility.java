package ipsim.gui.components;

import javax.swing.*;
import java.awt.event.*;

public class ContextMenuUtility
{
	public static JMenuItem item(final String string,final char mnemonic,final Runnable action)
	{
		final JMenuItem item=new JMenuItem(string);
		item.setMnemonic(mnemonic);

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				action.run();
			}
		});

		return item;
	}

	public static JMenuItem item(final String string,final char mnemonic,final int accelerator,final Runnable action)
	{
		final JMenuItem item=item(string,mnemonic,action);
		item.setAccelerator(KeyStroke.getKeyStroke(accelerator,InputEvent.CTRL_DOWN_MASK));
		return item;
	}
}
