package ipsim.gui.components;

import javax.swing.*;

public interface RoutingTableDialog
{
	Runnable populateElements();

	JDialog getJDialog();

	void editButtonClicked();

	void deleteButtonClicked();
}