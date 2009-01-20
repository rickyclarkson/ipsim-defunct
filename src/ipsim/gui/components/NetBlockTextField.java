package ipsim.gui.components;

import fpeas.lazy.*;
import ipsim.network.ethernet.*;

import javax.swing.*;
import ipsim.network.NetBlock;

public interface NetBlockTextField
{
	Lazy<NetBlock> netBlock();
	boolean isValid();

	JTextField getComponent();
}