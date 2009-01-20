package ipsim.gui;

import static anylayout.AnyLayout.*;
import static anylayout.extras.ConstraintUtility.*;
import anylayout.extras.*;
import fpeas.either.*;
import fpeas.function.*;
import ipsim.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.awt.*;
import static ipsim.lang.Runnables.*;
import ipsim.network.*;
import static ipsim.network.Problem.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetMask$.*;
import ipsim.swing.*;
import static ipsim.swing.Buttons.*;
import static ipsim.swing.Dialogs.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.event.*;
import static ipsim.lang.RunnablesInteropFailure.throwRuntimeException;

public final class ProblemDialog
{
	public static JDialog createProblemDialog(final JFrame frame, final NetworkContext context)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose("Edit Problem",frame);
		dialog.setSize(400,220);
		ComponentUtility.centreOnParent(dialog, frame);
		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(dialog.getContentPane());
		useAnyLayout(dialog.getContentPane(),0.5f,0.5f,constraints.getSizeCalculator(),typicalDefaultConstraint(throwRuntimeException));

		constraints.add(new JLabel("Network Number"),10,10,35,10,false,false);

		final IPAddressTextField ipAddressTextField=IPAddressTextFieldUtility.newInstance();

		@Nullable
                final Problem problem=context.problem();

		final IPAddress networkNumber=problem==null ? new IPAddress(0) : problem.netBlock().networkNumber();

		final NetMask subnetMask=problem==null ? netMask(0).get() : problem.netBlock().netMask();

		final int numberOfSubnets=problem==null ? ProblemConstants.MIN_SUBNETS() : problem.numberOfSubnets();

		ipAddressTextField.setIPAddress(networkNumber);

		constraints.add(ipAddressTextField.textField,50,10,30,10,false,false);

		constraints.add(new JLabel("Network Mask",SwingConstants.RIGHT),10,30,35,10,false,false);

		final SubnetMaskTextField subnetMaskTextField=new SubnetMaskTextField();

		subnetMaskTextField.setNetMask(subnetMask);
		constraints.add(subnetMaskTextField,50,30,35,10,false,false);
		constraints.add(new JLabel("Number of Subnets"),10,50,35,10,false,false);

		final JTextField numberOfSubnetsTextField=new JTextField(2);

		numberOfSubnetsTextField.setText(String.valueOf(numberOfSubnets));
		constraints.add(numberOfSubnetsTextField,50,50,30,15,false,false);

		constraints.add(Buttons.newButton("OK", new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				final int subnets=Integer.parseInt(numberOfSubnetsTextField.getText());
                                final Problem problem=new Problem(new NetBlock(ipAddressTextField.getIPAddress(),subnetMaskTextField.getNetMask()),subnets);

				context.problem.set(problem);
				
				dialog.setVisible(false);
				dialog.dispose();
			}
		}),15,85,20,15,false,false);

		constraints.add(closeButton("Close",dialog),75,85,20,15,false,false);

		return dialog;
	}
}