package ipsim.gui;

import javax.swing.*;

public class UserMessages
{
	public static void message(final String message)
	{
		JOptionPane.showMessageDialog(Global.frame, message);
	}
}
