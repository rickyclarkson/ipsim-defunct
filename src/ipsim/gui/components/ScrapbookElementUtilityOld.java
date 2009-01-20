package ipsim.gui.components;
/*
import anylayout.*;
import static anylayout.extras.ConstraintUtility.*;
import anylayout.extras.*;
import static ipsim.lang.Runnables.*;
import ipsim.swing.*;
import static ipsim.swing.LabelledTextFieldUtility.*;
import static ipsim.swing.NetBlockTextFieldUtility.*;
import static ipsim.util.Collections.*;

import javax.swing.*;
import java.util.*;

public final class ScrapbookElementUtility
{
	public static ScrapbookElement createElement()
	{
		final NetBlockTextField subnetNumberTextField=createNetBlockTextField();
		final LabelledTextField subnetNumber=createLabelledTextField("<html>Subnet<br>Number</html>", subnetNumberTextField.getComponent());

		final List<IPAddressTextField> ipAddressFields=arrayList();

		final JPanel panel=new JPanel();

		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(panel);
		AnyLayout.useAnyLayout(panel,0.5f,0.5f,constraints.getSizeCalculator(),typicalDefaultConstraint(throwRuntimeException));

		constraints.add(subnetNumber.getPanel(),5,5,45,50,false,false);

		final SubnetMaskTextField netMaskTextField=new SubnetMaskTextField();
		final LabelledTextField netMask=createLabelledTextField("Netmask", netMaskTextField);

		constraints.add(netMask.getPanel(),5,55,70,40,false,false);

		for (int a=1;a<4;a++)
		{
			final IPAddressTextField ipAddressTextField=IPAddressTextFieldUtility.newInstance();

			final LabelledTextField ipAddress=LabelledTextFieldUtility.createLabelledTextField2("IP Address "+a,ipAddressTextField.textField);
			constraints.add(ipAddress.getPanel(),50,33*a-33+3,50,30,false,false);
			ipAddressFields.add(ipAddressTextField);
		}

		return new ScrapbookElement()
		{
			public List<IPAddressTextField> getIPAddressTextFields()
			{
				return ipAddressFields;
			}

			public NetBlockTextField getSubnetTextField()
			{
				return subnetNumberTextField;
			}

			public JPanel getPanel()
			{
				return panel;
			}

			public SubnetMaskTextField getNetMaskTextField()
			{
				return netMaskTextField;
			}
		};
	}
}*/