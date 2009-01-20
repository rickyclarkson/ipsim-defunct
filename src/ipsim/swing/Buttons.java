package ipsim.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class Buttons
{
	public static JButton closeButton(final String caption,final Window window)
	{
		final JButton button=new JButton(caption);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				window.setVisible(false);
				window.dispose();
			}
		});
		return button;
	}

	public static JButton newButton(final String string,final Runnable runnable)
	{
		final JButton button=new JButton(string);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				runnable.run();
			}
		});

		return button;
	}

	public static JButton newButton(final String caption,final ActionListener listener)
	{
		final JButton button=new JButton(caption);
		button.addActionListener(listener);
		return button;
	}
}