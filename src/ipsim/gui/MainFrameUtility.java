package ipsim.gui;

/*import anylayout.*;
import anylayout.extras.*;
import fpeas.sideeffect.*;
import static ipsim.NetworkContext.*;
import static ipsim.gui.Global.*;
import static ipsim.gui.MenuHandler.*;
import static ipsim.gui.StandardToolBarUtility.*;
import static ipsim.gui.UserPermissions.*;
import static ipsim.gui.components.ContextMenuUtility.*;
import ipsim.gui.components.*;
import ipsim.lang.Runnables;
import ipsim.network.*;
import ipsim.network.connectivity.hub.ProgressMonitor;
import static ipsim.swing.Buttons.*;
import ipsim.swing.*;
import ipsim.webinterface.*;

import javax.swing.*;
import java.awt.*;
import static java.awt.Cursor.*;
import java.awt.event.*;
import java.io.*;
import static java.lang.System.*;
import java.util.Random;

public class MainFrameUtility
{
	public static JFrame createMainFrame(Random random)
	{
		final JFrame frame=new JFrame();
		initialise(random,frame);
		return frame;
	}

	public static void initialise(Random random,final JFrame frame)
	{
		setProperty("swing.aatext", "true");
		setProperty("apple.laf.useScreenMenuBar", "true");

		frame.setTitle("IPSim");

		final Container rootPanel=new JPanel(new BorderLayout());

		frame.setSize(800, 600);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);

		final ImageIcon icon=new ImageIcon(MainFrameUtility.class.getResource("/images/icon.png"));

		frame.setIconImage(icon.getImage());

		setupMenus(random,frame);
		setupCloseHandling(frame);

		final Container standardToolBar=createStandardToolBar();

		rootPanel.add(standardToolBar, BorderLayout.NORTH);

		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				final Container componentsBar=ComponentToolBarUtility.newBar();

				rootPanel.add(componentsBar, BorderLayout.WEST);

				final JPanel helpPanel=new JPanel();
				HelpFrameUtility.createPane.run(helpPanel);
				final JPanel scrapbook=ScrapbookDialogUtility.createScrapbook(frame,network);
				scrapbook.setOpaque(false);
				//context.tabbedPane.addTab("Scrapbook", scrapbook);

				final JPanel eventLogPanel=new JPanel();
				AnyLayout.useAnyLayout(eventLogPanel, 0.5f, 0.5f, SizeCalculatorUtility.absoluteSize(800, 600), ConstraintUtility.typicalDefaultConstraint(Runnables.throwRuntimeException));
				final PercentConstraints constraints=PercentConstraintsUtility.newInstance(eventLogPanel);
				EventLogDialogUtility.mutatePane(network,constraints);
				//context.tabbedPane.addTab("Event Log", eventLogPanel);
				//context.tabbedPane.addTab("Help", helpPanel);
				rootPanel.add(tabbedPane.wrapped, BorderLayout.CENTER);

				final Container panel=new JPanel(new BorderLayout());

				final JButton editProblemButton=new JButton("Edit Problem");
				editProblemButton.setFocusable(false);

				editProblemButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent e)
					{
						if (network.userPermissions==ACTUAL_TEST)
						{
							final String userName=network.emailAddress;

							final StringWriter writer=new StringWriter();

							NetworkUtility.saveToWriter(network, writer);

							try
							{
								String putSUSolution=WebInterface.putSUSolution(frame,network.testNumber, userName, writer.toString());

								if ((int)'1'==(int)putSUSolution.charAt(0))
									putSUSolution="Upload completed.  You may also want to use 'Save As' to save a copy on a removable disc";

								CustomJOptionPane.showNonModalMessageDialog(frame, "Solution Uploaded", putSUSolution);
							}
							catch (final IOException exception)
							{
								errors(frame,exception.getMessage());
							}
						}
						else
							if (network.userPermissions==PRACTICE_TEST_SIMULATING_ACTUAL_TEST)
								errors(frame,"In the real test, you will upload your solution to a central server, from where it will be marked.  You can upload as many times as you like; only the latest version will be marked.");
							else
								ProblemDialog.createProblemDialog(frame,Global.networkContext()).setVisible(true);
					}
				});

				panel.add(editProblemButton, BorderLayout.WEST);

				statusBar.setHorizontalAlignment(SwingConstants.CENTER);

				panel.add(statusBar, BorderLayout.CENTER);

				final SideEffect<JFrame> sideEffect=new SideEffect<JFrame>()
				{
					public void run(final JFrame realFrame)
					{
						final Cursor original=realFrame.getCursor();

						if (!network.userPermissions.allowFullTests().first())
						{
							ConnectivityTestDialogUtility.createConnectivityTestDialog();
							return;
						}

						realFrame.setCursor(getPredefinedCursor(WAIT_CURSOR));

						final ProgressMonitor monitor=new ProgressMonitor(realFrame, "Running tests                    ", "Running tests...                   ", 0, 100);

						monitor.setMillisToPopup(0);
						monitor.setMillisToDecideToPopup(0);
						monitor.setProgress(1);

						new SwingWorker<Void, Object>()
						{
							@Override
							public Void doInBackground()
							{
								try
								{
									Thread.currentThread().setUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());

									try
									{
										MenuHandler.operationsCheckSolution().run(monitor);
									}
									finally
									{
										realFrame.setCursor(original);
									}
								}
								catch (final RuntimeException exception)
								{
									javax.swing.SwingUtilities.invokeLater(new Runnable()
									{
										public void run()
										{
											throw exception;
										}
									});
								}

								return null;
							}
						}.execute();
					}
				};

				final Runnable runnable=new Runnable()
				{
					public void run()
					{
						sideEffect.run(frame);
					}
				};

				panel.add(newButton("Check Solution", runnable), BorderLayout.EAST);

				rootPanel.add(panel, BorderLayout.SOUTH);

				frame.setContentPane(rootPanel);

				frame.repaint();
			}
		});
	}

	/**
	 * Defines the actions taken when the main window is closed by the user.
	 * /
	public static void setupCloseHandling(final JFrame frame)
	{

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		frame.addWindowListener(new WindowListener()
		{
			public void windowOpened(final WindowEvent e)
			{
			}

			public void windowClosing(final WindowEvent e)
			{
				MenuHandler.fileExit();
			}

			public void windowClosed(final WindowEvent e)
			{
			}

			public void windowIconified(final WindowEvent e)
			{
			}

			public void windowDeiconified(final WindowEvent e)
			{
			}

			public void windowActivated(final WindowEvent e)
			{
			}

			public void windowDeactivated(final WindowEvent e)
			{
			}
		});
	}

	public static void setupMenus(Random random,final JFrame frame)
	{
		final JMenuBar bar=new JMenuBar();

		bar.add(setupFileMenu());
		bar.add(setupZoomMenu());
		bar.add(setupEventLogMenu());
		bar.add(setupToolsMenu(random));
		bar.add(setupHelpMenu());

		frame.setJMenuBar(bar);
	}

	public static Component setupHelpMenu()
	{
		final JMenu helpMenu=new JMenu("Help");
		helpMenu.setMnemonic('H');

		final JMenuItem contents=item("Contents", 'C', MenuHandler.helpContents());
		contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

		helpMenu.add(contents);
		helpMenu.add(item("About", 'A', MenuHandler.helpAbout()));

		return helpMenu;
	}

	public static Component setupToolsMenu(Random random)
	{
		final JMenu toolsMenu=new JMenu("Tools");
		toolsMenu.setMnemonic('T');
		toolsMenu.add(item("Test Connectivity", 'C', KeyEvent.VK_C, testConnectivity));
		toolsMenu.add(item("Test Conformance", 'F', KeyEvent.VK_F, MenuHandler.testConformance()));
		toolsMenu.add(item("Clear all ARP Tables", 'A', MenuHandler.clearAllArpTables()));
		toolsMenu.add(item("Break Network", 'B', MenuHandler.breakNetwork().applyLater(random)));
		toolsMenu.add(item("Set ARP Cache Timeout", 'A', MenuHandler.setArpCacheTimeout()));
		return toolsMenu;
	}

	public static Component setupZoomMenu()
	{
		final JMenu zoomMenu=new JMenu("Zoom");
		zoomMenu.setMnemonic('Z');

		zoomMenu.add(item("Zoom Out", 'O', KeyEvent.VK_MINUS, zoomOutRef));
		zoomMenu.add(item("Zoom In", 'I', KeyEvent.VK_PLUS, zoomInRef));
		zoomMenu.add(item("Zoom 1:1", '1', KeyEvent.VK_1, zoomOneToOneRef));
		zoomMenu.add(item("Zoom Auto", 'A', zoomToFitRef));

		return zoomMenu;
	}

	public static Component setupEventLogMenu()
	{
		final JMenu eventLogMenu=new JMenu("Event Log");
		eventLogMenu.setMnemonic('L');

		eventLogMenu.add(item("View", 'V', eventLogViewRef));
		eventLogMenu.add(item("Save as Text", 'S', MenuHandler.eventLogSave()));

		final JMenuItem clearLogItem=item("Clear", 'C', eventLogClearRef);

		eventLogMenu.add(clearLogItem);

		return eventLogMenu;
	}

	public static Component setupFileMenu()
	{
		final JMenu fileMenu=new JMenu("Network");
		fileMenu.setMnemonic('N');

		fileMenu.add(item("New", 'N', KeyEvent.VK_N, MenuHandler.networkNewRef.applyLater(random)));
		fileMenu.add(item("Open...", 'O', KeyEvent.VK_O, MenuHandler.fileOpenRef(random)));
		fileMenu.add(item("Save", 'S', KeyEvent.VK_S, MenuHandler.fileSaveRef));
		fileMenu.add(item("Close",'C',KeyEvent.VK_C, fileCloseRef.applyLater(random)));
		fileMenu.add(item("Save As...", 'A', MenuHandler.fileSaveAsRef));
		fileMenu.add(item("Join Networks with ISP",'J',MenuHandler.joinWithISPRef));
		fileMenu.add(item("Exit", 'X', fileExitRef));

		return fileMenu;
	}
}
*/