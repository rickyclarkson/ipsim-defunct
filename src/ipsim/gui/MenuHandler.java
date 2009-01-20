package ipsim.gui;

import anylayout.*;
import anylayout.extras.*;
import fpeas.either.*;
import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import fpeas.sideeffect.*;
import static ipsim.Caster.*;
import ipsim.*;
import static ipsim.network.NetworkContext.*;
import ipsim.awt.Point;
import static ipsim.gui.Global.*;
import ipsim.gui.components.initialdialog.*;
import ipsim.gui.event.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import ipsim.network.breakage.*;
import ipsim.network.conformance.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.network.connectivity.hub.ProgressMonitor;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetBlockUtility.*;
import ipsim.network.ip.*;
import static ipsim.network.IPAddress.*;
import ipsim.swing.*;
import ipsim.util.Arrays;
import ipsim.util.*;
import ipsim.webinterface.*;
import ipsim.webinterface.WebInterface.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import static java.lang.Integer.*;
import java.util.*;

public class MenuHandler
{
	public static SideEffect<Random> networkNewRef=new SideEffect<Random>()
	{
		public void run(Random random)
		{
			networkNew(random);
		}
	};

 	public static Runnable fileOpenRef(final Random random)
            {
                return new Runnable()
                    {
                        public void run()
                        {
                            fileOpen(random);
                        }
                    };
            }

	public static Runnable fileSaveRef=new Runnable()
	{
		public void run()
		{
			fileSave();
		}
	};
	public static Runnable zoomInRef=new Runnable()
	{
		public void run()
		{
			zoomIn();
		}
	};
	public static Runnable zoomOutRef=new Runnable()
	{
		public void run()
		{
			zoomOut();
		}
	};
	public static Runnable zoomToFitRef=new Runnable()
	{
		public void run()
		{
			zoomToFit();
		}
	};
	public static Runnable testConnectivity=new Runnable()
	{
		public void run()
		{
			ConnectivityTestDialogUtility.createConnectivityTestDialog();
		}
	};
	public static Runnable zoomOneToOneRef=new Runnable()
	{
		public void run()
		{
			zoomOneToOne();
		}
	};
	public static Runnable eventLogViewRef=new Runnable()
	{
		public void run()
		{
			eventLogView();
		}
	};
	public static Runnable eventLogClearRef=new Runnable()
	{
		public void run()
		{
			eventLogClear();
		}
	};
	public static SideEffect<Random> fileCloseRef=new SideEffect<Random>()
	{
		public void run(Random random)
		{
			fileClose(random);
		}
	};
	public static Runnable fileSaveAsRef=new Runnable()
	{
		public void run()
		{
			fileSaveAs();
		}
	};
	public static Runnable fileExitRef=new Runnable()
	{
		public void run()
		{
			fileExit();
		}
	};
	public static final Runnable joinWithISPRef=new Runnable()
	{
		public void run()
		{
			joinWithISP(network,networkView());
		}
	};

	/**
	 * Called when the File-&gt;New menu item is selected.
	 */
	public static void networkNew(Random random)
	{
		final NetworkContext context=new NetworkContext();
		network.contexts.append(context);

		NetworkView view=new NetworkView();

		tabbedPane.addTab("Network "+(tabbedPane.getTabCount()+1), view);
		tabbedPane.setSelectedComponent(view);

		editProblemButton.setText("Edit Problem");

		InitialDialogUtility.createInitialDialog(random,frame).dialog.setVisible(true);

		frame.repaint();
	}

	/**
	 * Called when the File-&gt;Open menu item is selected.
	 */
	public static void fileOpen(Random random)
	{
		if (network.modified && !networkModifiedDialog())
			return;

		final int result=fileChooser.showOpenDialog(frame);

		if (result==JFileChooser.APPROVE_OPTION)
		{
			final File filename=fileChooser.getSelectedFile();
			loadFromFile(network, filename);

			tabbedPane.removeAll();
			int a=1;

			for (final NetworkContext context : network.contexts.get())
				tabbedPane.addTab("Network "+a++, new NetworkView());

			Global.filename.set(filename);
			frame.repaint();
		}
	}

	public static void fileClose(Random random)
	{
		tabbedPane.removeAll();
		network.contexts.clear();
		network.clearAll();
		network.modified=false;
		network.zoomLevel.set(1.0);
		network.log.clear();
		Global.filename.set(null);
		networkNew(random);
	}

	public static void fileSave()
	{
		final File file=filename.get();

		if (file==null)
			fileSaveAs();
		else
			try
			{
				NetworkUtility.saveToFile(network, file);
			}
			catch (IOException e)
			{
				errors(frame, e.getMessage());
			}
	}

	/**
	 * Called when the File-&gt;Save As menu item is selected. Displays a save dialog then saves the file to the specified filename.
	 */
	public static void fileSaveAs()
	{
		for (;;)
		{
			final int result=fileChooser.showSaveDialog(frame);

			if (result==JFileChooser.APPROVE_OPTION)
			{
				final File filename=fileChooser.getSelectedFile();

				if (filename.exists())
				{
					final int optResult=CustomJOptionPane.showYesNoCancelDialog(frame, "This file exists.  Overwrite?", "Overwrite?");

					if (optResult==JOptionPane.NO_OPTION)
						continue;

					if (optResult==JOptionPane.CANCEL_OPTION)
						return;
				}

				try
				{
					NetworkUtility.saveToFile(network, filename);
				}
				catch (final IOException exception)
				{
					NetworkContext.errors(frame, exception.getMessage());

					return;
				}

				if (filename.getName().startsWith("@"))
					fileChooser.setSelectedFile(null);
				else
					Global.filename.set(filename);
			}

			return;
		}
	}

	/**
	 * Called when the File-&gt;Exit menu item is selected.
	 */
	public static void fileExit()
	{
		if (network.modified && !networkModifiedDialog())
			return;

		frame.setVisible(false);
		frame.dispose();

		System.exit(0);
	}

	public static void zoomOut()
	{
		network.zoomLevel.set(network.zoomLevel.get()/1.1);
		networkView().repaint();
	}

	public static void zoomIn()
	{
		network.zoomLevel.set(network.zoomLevel.get()*1.1);
		networkView().repaint();
	}

	public static void zoomToFit()
	{
		final NetworkView view=tabbedPane.getSelectedComponent();

		final Dimension optimumSize=NetworkViewUtility.getUnzoomedPreferredSize(network, view);
		final Dimension actualSize=view.getVisibleRect().getSize();
		final double zoomFactorX=(double)optimumSize.width/(double)actualSize.width;
		final double zoomFactorY=(double)optimumSize.height/(double)actualSize.height;
		network.zoomLevel.set(0.9/Math.max(zoomFactorX, zoomFactorY));
		networkView().repaint();
	}

	public static void zoomOneToOne()
	{
		network.zoomLevel.set(1.0);
		networkView().repaint();
	}

	public static void eventLogView()
	{
		network.log.add("Viewed the event log.");
		EventLogDialogUtility.createEventLogDialog().setVisible(true);
	}

	public static void eventLogClear()
	{
		if (!network.userPermissions.allowClearingLog())
		{
			NetworkContext.errors(frame, "Cannot clear the event log during an assessment.");

			return;
		}

		network.log.clear();
	}

	public static Runnable eventLogSave()
	{
		return new Runnable()
		{
			public void run()
			{
				final int result=fileChooser.showSaveDialog(frame);

				if (result==JFileChooser.APPROVE_OPTION)
				{
					final File filename=fileChooser.getSelectedFile();

					Writer fileWriter=null;

					try
					{
						fileWriter=new FileWriter(filename);

						fileWriter.write(LogUtility.asString(network.log));
						fileWriter.close();
					}
					catch (final IOException exception)
					{
						throw new RuntimeException(exception);
					}
					finally
					{
						try
						{
							if (fileWriter!=null)
								fileWriter.close();
						}
						catch (final IOException exception)
						{
							exception.printStackTrace();
						}
					}
				}
			}
		};
	}

	public static void practiceTroubleshootingTest()
	{
		errors(frame, "Practice Troubleshooting Test not yet supported");
		//WebInterface.getTSExample();
	}

	public static SideEffect<Random> practiceTest()
	{
		return new SideEffect<Random>()
		{
			public void run(final Random random)
			{
				final String[] choices=new String[]{"Easy","Medium","Hard"};
				final CustomJOptionPaneResult result=CustomJOptionPane.showLabelsAndConfirmation(frame, "Select a difficulty level", "Select a difficulty level", choices, 1, "Duplicate Test Conditions");

				final String choice=result.getChoice();

				if (choice==null)
				{
					networkNew(random);
					return;
				}

				if (result.confirmationTicked())
				{
					editProblemButton.setText("Upload Solution");
					network.userPermissions=UserPermissions.PRACTICE_TEST_SIMULATING_ACTUAL_TEST;
				}
				else
					network.userPermissions=UserPermissions.PRACTICE_TEST;

				networkContext().problem.set(ProblemDifficulty.valueOf(result.getChoice().toUpperCase(),random));

				JOptionPane.showMessageDialog(frame, asNotNull(networkContext().problem.value).toString());
			}
		};
	}

	public static Runnable helpContents()
	{
		return new Runnable()
		{
			public void run()
			{
				HelpFrameUtility.createHelpFrame().setVisible(true);
			}
		};
	}

	public static Runnable helpAbout()
	{
		return new Runnable()
		{
			public void run()
			{
				CustomJOptionPane.showNonModalMessageDialog(frame, "About IPSim", "IPSim is a network simulator, for teaching and assessment of skills in static subnetting.<p><p>Version "+Globals.appVersion+"<p>Build date: "+InitialDialogUtility.version+"<p>Design by Andrew Young and Ricky Clarkson<p>Programming by Ricky Clarkson (contributions from Andrew Young)<p><p>(c) University of Salford 2002-2006");
			}
		};
	}

	public static boolean networkModifiedDialog()
	{
		final String message;

		if (network.userPermissions==UserPermissions.ACTUAL_TEST)
			message="<html>CLOSING THE NETWORK WITHOUT UPLOADING WILL CAUSE YOU TO LOSE YOUR WORK.<br>CLICK CANCEL, THEN UPLOAD SOLUTION.<br>IF YOU ARE UNSURE, RAISE YOUR HAND.<br><br>This network has been modified.  Save changes?";
		else
			message="This network has been modified.  Save changes?";

		final int result=CustomJOptionPane.showYesNoCancelDialog(frame, message, "Confirm Lose Data?");

		if (result==JOptionPane.YES_OPTION)
		{
			fileSave();

			if (network.modified)
				return false;
		}

		return !(result==JOptionPane.CANCEL_OPTION);
	}

	public static void loadAssessmentProblem(final Random random)
	{
		final String testNumber=JOptionPane.showInputDialog(frame, "Enter the test number given to you by your tutor");
		if (testNumber==null || testNumber.length()==0)
			return;

		network.testNumber=testNumber;

		MaybeUtility.run(WebInterface.getProblem(frame, testNumber), new SideEffect<String>()
		{
			public void run(final String string)
			{
				final String[] strings=string.split("\n");

				final String netSizesString=strings[1].split("=")[1];
				final String[] netSizes=netSizesString.split(",");

				final String chosenSize=netSizes[(int)(Math.random()*(double)netSizes.length)];

				final String subnetOptionString=strings[2].split("=")[1];
				final String[] subnetOptions=subnetOptionString.split(",");

				final String chosenSubnetOption=subnetOptions[(int)(Math.random()*(double)subnetOptions.length)];

				IPAddress generateNetworkNumber;

				int giveUp=0;

				do
				{
					giveUp++;
					generateNetworkNumber=ProblemUtility.generateNetworkNumber(random,parseInt(chosenSize));
				}
				while (giveUp<100 && 0==(generateNetworkNumber.rawValue()&0xFF00));

				final NetMask mask=NetMaskUtility.fromPrefixLength(parseInt(chosenSize)).get();

				final Problem problem=new Problem(new NetBlock(generateNetworkNumber, mask),parseInt(chosenSubnetOption));

				networkContext().problem.set(problem);
				network.userPermissions=UserPermissions.ACTUAL_TEST;
				JOptionPane.showMessageDialog(frame, problem.toString());

				boolean set=false;

				do
				{
					final String userName=JOptionPane.showInputDialog(frame, "Please enter your University email address, e.g., "+"N.Other@student.salford.ac.uk");

					if (userName!=null && !(0==userName.length()))
					{
						network.emailAddress=userName;
						try
						{
							WebInterface.putSUProblem(userName, problem.toString());
						}
						catch (final IOException exception)
						{
							JOptionPane.showMessageDialog(frame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
						set=true;
					}
				}
				while (!set);
			}
		});

	}

	public static Runnable testConformance()
	{
		return new Runnable()
		{
			public void run()
			{
				if (!network.userPermissions.allowFullTests().first())
				{
					NetworkContext.errors(frame, network.userPermissions.allowFullTests().second());
					return;
				}

				final ConformanceTests.ResultsAndSummaryAndPercent results=ConformanceTests.allChecks(network);

				final String ifExists=results.percent+"% Conformance\n\n"+results.summary;
				final String ifNothing="No problem is set, so some checks cannot be performed\n\n"+results.percent+"%\n\n"+results.summary;

				final Object message=networkContext().problem.value==null ? ifNothing : ifExists;
				final int msgType=results.percent==100 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;
				JOptionPane.showMessageDialog(frame, message, "Conformance Test", msgType);
			}
		};
	}

	public static SideEffect<ProgressMonitor> operationsCheckSolution()
	{
		return new SideEffect<ProgressMonitor>()
		{
			public void run(final ProgressMonitor monitor)
			{
				final ConnectivityResults connectivityResults=ConnectivityTest.testConnectivity(network, new SideEffect<String>()
				{
					public void run(final String s)
					{
						monitor.setNote(s);
					}
				}, new SideEffect<Integer>()
				{
					public void run(final Integer integer)
					{
						monitor.setProgress(integer);
					}
				});

				final int connectivity=connectivityResults.getPercentConnected();

				final JLabel connectivityLabel=new JLabel("The network is "+connectivity+"% connected\n\n");
				final AbstractButton connectivityButton=new JButton("More info...");
				connectivityLabel.setLabelFor(connectivityButton);

				connectivityButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent e)
					{
						final String[] outputs=new String[connectivityResults.getOutputs().size()];
						connectivityResults.getOutputs().toArray(outputs);

						CustomJOptionPane.showNonModalMessageDialog(frame, "Connectivity Results", connectivity==100 ? new JLabel("All pings succeeded.") : new JScrollPane(new JTextArea(Arrays.toString("\n", outputs),10,80)));
					}
				});

				monitor.setNote("Conformance Tests");

				final ConformanceTests.ResultsAndSummaryAndPercent results=ConformanceTests.allChecks(network);

				monitor.setProgress(100);

				final JLabel conformanceLabel=new JLabel(networkContext().problem.value==null ? "Conformance: N/A (No problem set)\n\n" : "Conformance: "+results.percent+"%\n\n");

				final AbstractButton conformanceButton=new JButton("More Info...");

				conformanceLabel.setLabelFor(conformanceButton);
				conformanceButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent e)
					{
						CustomJOptionPane.showNonModalMessageDialog(frame, "Results", results.percent==100 ? "The network conforms with the requirements." : results.summary);
					}
				});

				final int overall=(results.percent+connectivity)/2;

				monitor.close();

				final int padding=20;

				final Container panel=new JPanel();

				AnyLayout.useAnyLayout(panel, 0.5f, 0.5f, new SizeCalculator()
				{
					public int getHeight()
					{
						final int labels=connectivityLabel.getPreferredSize().height+conformanceLabel.getPreferredSize().height;
						final int buttons=connectivityButton.getPreferredSize().height+conformanceButton.getPreferredSize().height;

						return padding*2+Math.max(labels, buttons);
					}

					public int getWidth()
					{
						final int top=connectivityLabel.getPreferredSize().width+connectivityButton.getPreferredSize().width;
						final int bottom=conformanceLabel.getPreferredSize().width+conformanceButton.getPreferredSize().width;
						return padding*3+Math.max(top, bottom);
					}
				}, null);

				panel.add(connectivityLabel, ConstraintUtility.topLeft(FunctionUtility.constant(padding), FunctionUtility.constant(padding)));
				panel.add(connectivityButton, ConstraintUtility.topRight(FunctionUtility.constant(padding), FunctionUtility.constant(padding)));
				panel.add(conformanceLabel, RelativeConstraints.below(connectivityLabel, padding));
				panel.add(conformanceButton, RelativeConstraints.levelWith(conformanceLabel, connectivityButton));

				CustomJOptionPane.showNonModalMessageDialog(frame, "Results", panel);

				network.log.add("Checked solution: "+overall+"% score");
			}
		};
	}

	public static Runnable downloadConfiguration()
	{
		return new Runnable()
		{
			public void run()
			{
				String name;

				do
				{
					name=JOptionPane.showInputDialog(frame, "Enter the name of the configuration");
					if (name==null)
						return;
				}
				while (name.length()==0);

				final String realName=name;
				final NamedConfigurationOrFailure namedConfig=WebInterface.getNamedConfiguration(realName);

				if (namedConfig.reason!=null)
				{
					errors(frame, namedConfig.reason);
					return;
				}

				networkView().ignorePaints.let(true, NetworkUtility.loadFromStringRef(network, namedConfig.config.configuration));

				frame.setTitle("IPSim - "+namedConfig.config.name);
			}
		};
	}

	public static final Runnable freeform=new Runnable()
	{
		public void run()
		{
			network.userPermissions=UserPermissions.FREEFORM;
		}
	};

	public static Runnable clearAllArpTables()
	{
		return new Runnable()
		{
			public void run()
			{
				network.log.add("Cleared all ARP tables.");
				final Iterable<Computer> computers=NetworkUtility.getAllComputers(network);
				for (final Computer computer : computers)
					computer.arpTable.clear();
			}
		};
	}

	public static SideEffect<Random> breakNetwork()
	{
		return new SideEffect<Random>()
		{
			public void run(Random random)
			{
				BreakNetwork.breakNetwork(random,frame, network, networkView());
			}
		};
	}

	public static void actualTroubleshootingTest()
	{
		NetworkContext.errors(frame, "The Actual Troubleshooting Test is not yet implemented");
	}

	public static Runnable setArpCacheTimeout()
	{
		return new Runnable()
		{
			public void run()
			{
				try
				{
					final int time=parseInt(JOptionPane.showInputDialog(tabbedPane.getSelectedComponent(), "How long do you want the ARP cache to live for, in seconds?"));
					if (time>0)
						network.arpCacheTimeout=time;
					else
						UserMessages.message("The ARP cache timeout must be greater than 0 seconds");
				}
				catch (NumberFormatException e)
				{
				}
			}
		};
	}

	public static void joinWithISP(Network network,NetworkView view)
	{
		int numNetworks=network.contexts.get().size();
		final Stream<NetworkContext> contexts=network.contexts.get();

		for (final NetworkContext context : contexts)
			if (context.problem.value==null)
			{
				UserMessages.message("Each network must have a netblock set for the ISP to work");
				return;
			}

		network.ispContext.visibleComponents.set(new Stream<PacketSource>());

		@NotNull
		Hub hub=network.ispContext.visibleComponents.prependIfNotPresent(new Hub(position(new Point(400, 400))));
		Props.setProperty(network,view,hub, hub.power, true);

		@NotNull
		Computer centralRouter=network.ispContext.visibleComponents.prependIfNotPresent(ComputerFactory.newComputerWithID(network.generateComputerID(), 1, 1));

		centralRouter.ipForwardingEnabled=true;
		Card centralCard=network.ispContext.visibleComponents.prependIfNotPresent(CardFactory.newCardConnectedTo(centralRouter, 1, 1));
		CardDrivers centralDrivers=centralCard.installDeviceDrivers(network);
		centralDrivers.ipAddress.set(IPAddressUtility.valueOf("20.0.0.1").get());
		centralDrivers.netMask.set(NetMaskUtility.valueOf("255.0.0.0").get());

		network.ispContext.visibleComponents.prependIfNotPresent(new Cable(asNotNull(parent(centralCard)), asNotNull(parent(hub))));

		int index=2;

		for (@NotNull final NetworkContext context : contexts)
		{
			@NotNull
			final Computer isp=context.visibleComponents.prepend(new Computer(position(new Point(50, 50))));
			isp.isISP=true;
			isp.ipForwardingEnabled=true;

			final Card invisibleCard=network.ispContext.visibleComponents.prependIfNotPresent(new Card(false, asNotNull(parent(isp))));
			CardDrivers drivers=invisibleCard.installDeviceDrivers(network);
			drivers.ipAddress.set(IPAddressUtility.valueOf("20.0.0."+index).get());
			drivers.netMask.set(NetMaskUtility.valueOf("255.0.0.0").get());

			isp.routingTable.add(network, just(isp), new Route(zero(), centralDrivers.ipAddress.get()));
			centralRouter.routingTable.add(network, just(centralRouter), new Route(asNotNull(context.problem.value).netBlock(), drivers.ipAddress.get()));

			index++;

			network.ispContext.visibleComponents.prependIfNotPresent(new Cable(asNotNull(parent(invisibleCard)), asNotNull(parent(hub))));

			Point point=new Point(100, 100);
			final Card card=new Card(true, position(new Point((int)point.x(), (int)point.y())));
			card.setPositionData(0, asNotNull(parent(isp)));

			card.installDeviceDrivers(network);
			context.visibleComponents.prependIfNotPresent(card);
			context.visibleComponents.prependIfNotPresent(isp);
		}

		if (numNetworks!=network.contexts.get().size())
			throw null;
	}
}
