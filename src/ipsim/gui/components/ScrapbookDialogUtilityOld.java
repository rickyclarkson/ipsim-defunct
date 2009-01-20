package ipsim.gui.components;
/*
import anylayout.*;
import static anylayout.extras.ConstraintUtility.*;
import anylayout.extras.*;
import fpeas.pair.*;
import static fpeas.pair.PairUtility.*;
import ipsim.gui.*;
import static ipsim.lang.Runnables.*;
import ipsim.network.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.swing.*;
import static ipsim.swing.NetBlockTextFieldUtility.*;
import static ipsim.util.Collections.*;
import static ipsim.util.Printf.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import ipsim.lang.RunnablesInteropFailure;

public final class ScrapbookDialogUtility
{
	public static JPanel createScrapbook(final JFrame frame,final Network network)
	{
		final JPanel panel=new JPanel();

		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(panel);
		AnyLayout.useAnyLayout(panel, 0.5f, 0.5f, constraints.getSizeCalculator(), typicalDefaultConstraint(RunnablesInteropFailure.throwRuntimeException));

		final NetBlockTextField networkNumberField=createNetBlockTextField();

		JLabel label=new JLabel("Network Number");
		constraints.add(label, 2, 2, 28, 5, false, false);
		constraints.add(networkNumberField.getComponent(), 2, 8, 28, 10, false, false);

		final SubnetMaskTextField netMaskField=new SubnetMaskTextField();

		label=new JLabel("Netmask");
		constraints.add(label, 2, 15, 28, 5, false, false);
		constraints.add(netMaskField, 2, 20, 28, 20, false, false);

		final List<ScrapbookElement> elements=arrayList();

		final JButton clearNumbers=new JButton("Clear Numbers");
		constraints.add(clearNumbers, 2, 80, 25, 10, false, false);

		clearNumbers.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
                            if (JOptionPane.YES_OPTION==CustomJOptionPane.showYesNoCancelDialog("Do you really want to clear all the numbers?", "Clear Numbers?", frame))
					return;

				networkNumberField.getComponent().setText("");
				netMaskField.setText("");

				for (final ScrapbookElement element : elements)
				{
					final JTextField textField=element.getSubnetTextField().getComponent();

					textField.setText("");
					element.getNetMaskTextField().setText("");

					for (final IPAddressTextField field : element.getIPAddressTextFields())
                                            field.component().setText("");
				}
			}
		});

		final JButton checkNumbers=new JButton("Check Numbers");

		constraints.add(checkNumbers, 2, 70, 25, 10, false, false);

		checkNumbers.addActionListener(new ActionListener()
		{
			public Pair<String, String> checkNumbers()
			{
				final StringBuilder description=new StringBuilder();
				int checked=0;
				int errors=0;

				final Pair<String, String> errorMessage=pair("one or more fields have invalid data.", "One or more fields have invalid data.");

				if (!(0==networkNumberField.getComponent().getText().length()))
				{
					if (!networkNumberField.isValid())
						return errorMessage;

					final NetBlock block=networkNumberField.netBlock().invoke();

					if (!(0==netMaskField.getText().length()))
						if (netMaskField.isValidText())
						{
                                                    if (!block.netMask().equals(netMaskField.netMask()))
                                                        description.append(sprintf("The netmask %s does not correspond with the network %s\n", netMaskField.netMask().toString(), block.asStringContainingSlash()));
						}
						else
							return errorMessage;

					checked++;
					for (final ScrapbookElement element : elements)
					{
						final NetBlockTextField subnetTextField=element.getSubnetTextField();

						if (0==subnetTextField.getComponent().getText().length())
							continue;

						if (!subnetTextField.isValid())
							return errorMessage;

						final NetBlock subnet=subnetTextField.netBlock().invoke();

						final IPAddress netNum=new IPAddress(subnet.networkNumber().rawValue()&subnet.netMask().rawValue());

						checked++;
						final int length1=block.netMask().prefixLength();
						final int length2=subnet.netMask().prefixLength();

						if (length1==-1 || length2==-1)
							description.append("One of the subnet masks is invalid\n");
						else
							if (!block.contains(netNum) || length1>=length2)
							{
								errors++;
								description.append("The subnet ");
								description.append(subnetTextField.getComponent().getText());

								description.append(" is not a subnet of the network ");

								description.append(networkNumberField.getComponent().getText());
								description.append('\n');
							}

						description.append(checkScrapbookIPs(block, element.getIPAddressTextFields()));
					}
				}

				for (final ScrapbookElement element : elements)
				{
					final NetBlockTextField subnetTextField=element.getSubnetTextField();

					if (0==subnetTextField.getComponent().getText().length())
						continue;

					if (!subnetTextField.isValid())
						return errorMessage;

					final NetBlock subnet=subnetTextField.netBlock().invoke();

					description.append(checkScrapbookIPs(subnet, element.getIPAddressTextFields()));

					checked++;

					if (!(0==element.getNetMaskTextField().getText().length()))
					{
						if (!element.getNetMaskTextField().isValidText())
							return errorMessage;

						if (element.getNetMaskTextField().netMask().equals(subnet.netMask()))
						{
							errors++;
							description.append(sprintf("The netmask %s does not correspond with the subnet %s\n", element.getNetMaskTextField().netMask().toString(), subnet.asStringContainingSlash()));
						}
					}
				}

				for (final ScrapbookElement element1 : elements)
				{
					if (0==element1.getSubnetTextField().getComponent().getText().length())
						continue;

					final NetBlock netblock1=element1.getSubnetTextField().netBlock().invoke();

					for (final ScrapbookElement element2 : elements)
					{
						if (0==element2.getSubnetTextField().getComponent().getText().length())
							continue;

						final NetBlock netBlock2=element2.getSubnetTextField().netBlock().invoke();

						if (element1.equals(element2))
							continue;

						checked++;

						if (element1.getSubnetTextField().netBlock().invoke().contains(element2.getSubnetTextField().netBlock().invoke().networkNumber()))
						{
							errors++;

							description.append(sprintf("The network %s contains the network %s, therefore they are not disjoint and cannot be used as separate networks.\n", netblock1.asStringContainingSlash(), netBlock2.asStringContainingSlash()));
						}
					}
				}

				if (!(0==netMaskField.getText().length()) && !netMaskField.isValidText())
					return errorMessage;

				if (0==description.length())
					description.append("All the numbers are ok");

				return pair("checked "+checked+" numbers, "+errors+" errors found", description.toString());
			}

			public String checkScrapbookIPs(final NetBlock netBlock, final Iterable<IPAddressTextField> fields)
			{
				final StringBuilder answer=new StringBuilder();

				for (final IPAddressTextField ipAddressTextField : fields)
				{
                                    if (0==ipAddressTextField.component().getText().length())
						continue;

					final IPAddress ipAddress=ipAddressTextField.ipAddress();

					final String withSlash=netBlock.asStringContainingSlash();

					if (!netBlock.contains(ipAddress))
						answer.append(sprintf("The IP address %s is not in the network %s\n", ipAddress.toString(), withSlash));

					final int rawIP=ipAddress.rawValue();

					if (rawIP==netBlock.networkNumber().rawValue())
						answer.append(sprintf("The IP address %s has all 0s as the host number, and cannot be used as an IP address on the network %s\n", ipAddress.toString(), withSlash));

					final int allOnes=NetMask$.MODULE$.fromPrefixLength(32).get().rawValue();

					if (rawIP==(rawIP|netBlock.netMask().rawValue()^allOnes))
						answer.append(sprintf("The IP address %s has all 1s as the host number, and is the broadcast address for the network %s\n", ipAddress.toString(), withSlash));
				}

				return answer.toString();
			}

			public void actionPerformed(final ActionEvent event)
			{
				final Pair<String, String> results=checkNumbers();
				network.log.add("Scrapbook - "+results.first());
				UserMessages.message(results.second());
			}
		});

		for (int a=0;a<5;a++)
		{
			final ScrapbookElement element=ScrapbookElementUtility.createElement();
			elements.add(element);

			element.getPanel().setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			element.getPanel().setOpaque(false);

			constraints.add(element.getPanel(), 30, 20*a, 70, 20, true, true);
		}

		final List<Component> list=arrayList();
		list.add(networkNumberField.getComponent());
		list.add(netMaskField);
		list.add(checkNumbers);
		list.add(clearNumbers);

		for (final ScrapbookElement element : elements)
		{
			// element.getPanel().setFocusCycleRoot(false);
			list.add(element.getSubnetTextField().getComponent());
			list.add(element.getNetMaskTextField());

			for (final IPAddressTextField field : element.getIPAddressTextFields())
				list.add(field.textField);
		}

		final FocusTraversalPolicy policy=new FocusTraversalPolicy()
		{
			@Override
			public Component getComponentAfter(final Container aContainer, final Component aComponent)
			{
				final int index=list.indexOf(aComponent);

				return list.get((index+1)%list.size());
			}

			@Override
			public Component getComponentBefore(final Container aContainer, final Component aComponent)
			{
				int index=list.indexOf(aComponent);

				index=0==index ? list.size()-1 : index;

				return list.get(index);
			}

			@Override
			public Component getFirstComponent(final Container aContainer)
			{
				return networkNumberField.getComponent();
			}

			@Override
			public Component getLastComponent(final Container aContainer)
			{
				return list.get(list.size()-1);
			}

			@Override
			public Component getDefaultComponent(final Container aContainer)
			{
				return networkNumberField.getComponent();
			}
		};

		panel.setFocusTraversalPolicy(policy);

		for (final ScrapbookElement element : elements)
		{
			final JPanel elementPanel=element.getPanel();
			elementPanel.setFocusTraversalPolicy(policy);
			elementPanel.setFocusTraversalPolicyProvider(true);
		}

		panel.setFocusTraversalPolicyProvider(true);

		return panel;
	}
}*/