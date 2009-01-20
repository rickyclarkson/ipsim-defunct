package ipsim.gui;
/*
import anylayout.*;
import anylayout.extras.*;
import static anylayout.extras.SizeCalculatorUtility.*;
import fpeas.function.*;
import ipsim.awt.*;
import static ipsim.gui.Global.*;
import ipsim.lang.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.hub.ProgressMonitor;
import static ipsim.swing.Buttons.*;
import static ipsim.swing.Dialogs.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import static javax.swing.SwingUtilities.*;
import java.awt.*;

/**
 * Dialog that shows the results of a connectivity test (menu item Operations-&gt;Test Connectivity).
 * /
public final class ConnectivityTestDialogUtility
{
	public static void createConnectivityTestDialog()
	{
            final JFrame realFrame=Global.frame();
            final JDialog dialog=createDialogWithEscapeKeyToClose("Connectivity Test",realFrame);

		dialog.setSize(700, 400);
		AnyLayout.useAnyLayout(dialog.getContentPane(), 0.5f, 0.5f, absoluteSize(700, 400), null);
		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(dialog.getContentPane());

		ComponentUtility.centreOnParent(dialog, realFrame);

		final JLabel resultsLabel=new JLabel();
		constraints.add(resultsLabel, 5, 5, 90, 5, false, false);

		constraints.add(new JLabel("Connectivity Problems:"), 5, 15, 90, 5, false, false);

		final JTextArea problemList=new JTextArea();
		constraints.add(new JScrollPane(problemList), 5, 25, 90, 60, true, true);

		constraints.add(closeButton("Close", dialog), 80, 90, 15, 6, true, true);

		final Cursor original=realFrame.getCursor();

		realFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		final ProgressMonitor monitor=new ProgressMonitor(realFrame, "Testing connectivity                  ", "Testing connectivity                    ", 0, 100);

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
                                    final ConnectivityResults results=ConnectivityTest.testConnectivity(network(),ProgressMonitorUtility.setNote(monitor),ProgressMonitorUtility.setProgress(monitor));
					problemList.setText(StringUtility.join(results.getOutputs(), "\n"));

					resultsLabel.setText(results.getPercentConnected()+"% connected.");

					network().log.add("Tested the connectivity, "+resultsLabel.getText());

					problemList.setText(Collections.append(results.getOutputs(), new Function<String, String>()
					{
						@NotNull
						public String run(@NotNull final String item)
						{
							return item+'\n';
						}
					}));

					invokeLater(new Runnable()
					{
						public void run()
						{
							monitor.setProgress(100);
							monitor.close();
							dialog.setVisible(true);
						}
					});
				}
				catch (final RuntimeException exception)
				{
					SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							throw exception;
						}
					});
				}
				finally
				{
					realFrame.setCursor(original);
				}

				return null;
			}
		}.execute();
	}
}
*/