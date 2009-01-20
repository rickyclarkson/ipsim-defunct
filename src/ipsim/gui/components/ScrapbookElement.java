package ipsim.gui.components;

import ipsim.swing.*;

import javax.swing.*;
import ipsim.gui.*;

public interface ScrapbookElement
{
	NetBlockTextField getSubnetTextField();

	Iterable<IPAddressTextField> getIPAddressTextFields();
	JPanel getPanel();
	SubnetMaskTextField getNetMaskTextField();
}