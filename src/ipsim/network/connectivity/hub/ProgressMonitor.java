package ipsim.network.connectivity.hub;
/*
import sun.awt.*;

import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

@SuppressWarnings("sun")
final class ProgressMonitor implements Accessible
{
	private ProgressMonitor root=null;
	private JDialog dialog=null;
	private JOptionPane pane=null;
	private JProgressBar myBar=null;
	private JLabel noteLabel=null;
	private final Component parentComponent;
	private String note;
	private Object[] cancelOption=null;
	private final Object message;
	private final long T0;
	private int millisToDecideToPopup=500;
	private int millisToPopup=2000;
	private int min;
	private int max;
	private int lastDisp=0;
	private int reportDelta;

	public ProgressMonitor(final Component parentComponent, final Object message, final String note, final int min, final int max)
	{
		this(parentComponent, message, note, min, max, null);
	}


	private ProgressMonitor(final Component parentComponent, final Object message, final String note, final int min, final int max, final ProgressMonitor group)
	{
		this.min=min;
		this.max=max;
		this.parentComponent=parentComponent;

		cancelOption=new Object[1];
		cancelOption[0]=UIManager.getString("OptionPane.cancelButtonText");

		reportDelta=(max-min)/100;
		if (reportDelta<1) reportDelta=1;
		this.message=message;
		this.note=note;
		if (group!=null)
		{
			root=group.root!=null ? group.root : group;
			T0=root.T0;
			dialog=root.dialog;
		}
		else
		{
			T0=System.currentTimeMillis();
		}
	}

	private class ProgressOptionPane extends JOptionPane
	{
		ProgressOptionPane(final Object messageList)
		{
			super(messageList, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, cancelOption, null);
		}


		@Override
		public int getMaxCharactersPerLineCount()
		{
			return 100;
		}


		// Equivalent to JOptionPane.createDialog,
		// but create a modeless dialog.
		// This is necessary because the Solaris implementation doesn't
		// support Dialog.setModal yet.
		@Override
		public JDialog createDialog(final Component parentComponent, final String title)
		{
			final JDialog dialog;

			final Window window=getWindowForComponent(parentComponent);
			if (window instanceof Frame)
			{
				dialog=new JDialog((Frame)window, title, false);
			}
			else
			{
				dialog=new JDialog((Dialog)window, title, false);
			}
			if (window instanceof SharedOwnerFrame)
			{
				final WindowListener ownerShutdownListener=
						getSharedOwnerFrameShutdownListener();
				dialog.addWindowListener(ownerShutdownListener);
			}
			final Container contentPane=dialog.getContentPane();

			contentPane.setLayout(new BorderLayout());
			contentPane.add(this, BorderLayout.CENTER);
			dialog.pack();
			dialog.setLocationRelativeTo(parentComponent);
			dialog.addWindowListener(new WindowAdapter()
			{
				public boolean gotFocus=false;

				@Override
				public void windowClosing(final WindowEvent we)
				{
					setValue(cancelOption[0]);
				}

				@Override
				public void windowActivated(final WindowEvent we)
				{
					// Once window gets focus, set initial focus
					if (!gotFocus)
					{
						selectInitialValue();
						gotFocus=true;
					}
				}
			});

			addPropertyChangeListener(new PropertyChangeListener()
			{
				public void propertyChange(final PropertyChangeEvent event)
				{
					if (dialog.isVisible() &&
							event.getSource().equals(ProgressOptionPane.this) &&
							(event.getPropertyName().equals(VALUE_PROPERTY) ||
									event.getPropertyName().equals(INPUT_VALUE_PROPERTY)))
					{
						dialog.setVisible(false);
						dialog.dispose();
					}
				}
			});

			return dialog;
		}

		/////////////////
		// Accessibility support for ProgressOptionPane
		////////////////

		/**
		 * Gets the AccessibleContext for the ProgressOptionPane
		 *
		 * @return the AccessibleContext for the ProgressOptionPane
		 * @since 1.5
		 * /
		@Override
		public AccessibleContext getAccessibleContext()
		{
			return ProgressMonitor.this.getAccessibleContext();
		}
	}


	/**
	 * Indicate the progress of the operation being monitored.
	 * If the specified value is >= the maximum, the progress
	 * monitor is closed.
	 *
	 * @param nv an int specifying the current value, between the
	 *           maximum and minimum specified for this component
	 * @see #setMinimum
	 * @see #setMaximum
	 * @see #close
	 * /
    @SuppressWarnings("deprecation")
	public void setProgress(final int nv)
	{
		if (nv>=max)
		{
			close();
		}
		else
			if (nv>=lastDisp+reportDelta)
			{
				lastDisp=nv;
				if (myBar!=null)
				{
					myBar.setValue(nv);
				}
				else
				{
					final long T=System.currentTimeMillis();
					final long dT=(int)(T-T0);
					if (dT>=millisToDecideToPopup)
					{
						final int predictedCompletionTime;
						if (nv>min)
						{
							predictedCompletionTime=(int)((long)dT*
									(max-min)/
									(nv-min));
						}
						else
						{
							predictedCompletionTime=millisToPopup;
						}
						if (predictedCompletionTime>=millisToPopup)
						{
							myBar=new JProgressBar();
							myBar.setMinimum(min);
							myBar.setMaximum(max);
							myBar.setValue(nv);
							if (note!=null) noteLabel=new JLabel(note);
							pane=new ProgressOptionPane(new Object[]{message,
									noteLabel,
									myBar});
							dialog=pane.createDialog(parentComponent,
									UIManager.getString(
											"ProgressMonitor.progressText"));
							dialog.setSize(dialog.getWidth()+100, dialog.getHeight());
							dialog.setLocation(dialog.getLocation().x-50, dialog.getLocation().y);
							dialog.show();
						}
					}
				}
			}
	}


	/**
	 * Indicate that the operation is complete.  This happens automatically
	 * when the value set by setProgress is >= max, but it may be called
	 * earlier if the operation ends early.
	 * /
	public void close()
	{
		if (dialog!=null)
		{
			dialog.setVisible(false);
			dialog.dispose();
			dialog=null;
			pane=null;
			myBar=null;
		}
	}

	/**
	 * Specifies the amount of time to wait before deciding whether or
	 * not to popup a progress monitor.
	 *
	 * @param millisToDecideToPopup an int specifying the time to wait,
	 *                              in milliseconds
	 * @see #getMillisToDecideToPopup
	 * /
	public void setMillisToDecideToPopup(final int millisToDecideToPopup)
	{
		this.millisToDecideToPopup=millisToDecideToPopup;
	}

	/**
	 * Specifies the amount of time it will take for the popup to appear.
	 * (If the predicted time remaining is less than this time, the popup
	 * won't be displayed.)
	 *
	 * @param millisToPopup an int specifying the time in milliseconds
	 * @see #getMillisToPopup
	 * /
	public void setMillisToPopup(final int millisToPopup)
	{
		this.millisToPopup=millisToPopup;
	}

	/**
	 * Specifies the additional note that is displayed along with the
	 * progress message. Used, for example, to show which file the
	 * is currently being copied during a multiple-file copy.
	 *
	 * @param note a String specifying the note to display
	 * @see #getNote
	 * /
	public void setNote(final String note)
	{
		this.note=note;
		if (noteLabel!=null)
		{
			noteLabel.setText(note);
		}
	}

	public AccessibleContext getAccessibleContext()
	{
		return null;
	}

	static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException
	{
		while (true)
		{
			if (parentComponent==null)
				return getRootFrame();
			if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
				return (Window)parentComponent;
			parentComponent=parentComponent.getParent();
		}
	}
	public static Frame getRootFrame() throws HeadlessException
	{
		Frame sharedFrame=(Frame)AppContext.getAppContext().get(JOptionPane.class);

		if (sharedFrame==null)
		{
			sharedFrame=getSharedOwnerFrame();
			AppContext.getAppContext().put(JOptionPane.class, sharedFrame);
		}
		return sharedFrame;
	}

	static Frame getSharedOwnerFrame() throws HeadlessException
	{
		Frame sharedOwnerFrame=
				(Frame)AppContext.getAppContext().get(new StringBuffer("SwingUtilities.sharedOwnerFrame"));
		if (sharedOwnerFrame==null)
		{
			sharedOwnerFrame=new SharedOwnerFrame();
			AppContext.getAppContext().put(new StringBuffer("SwingUtilities.sharedOwnerFrame"), sharedOwnerFrame);
		}
		return sharedOwnerFrame;
	}

	private static class SharedOwnerFrame extends Frame implements WindowListener
	{
		@Override
		public void addNotify()
		{
			super.addNotify();
			installListeners();
		}

		/**
		 * Install window listeners on owned windows to watch for displayability changes
		 * /
		void installListeners()
		{
			final Window[] windows=getOwnedWindows();
			for (final Window window : windows)
			{
				if (window!=null)
				{
					window.removeWindowListener(this);
					window.addWindowListener(this);
				}
			}
		}

		/**
		 * Watches for displayability changes and disposes shared instance if there are no
		 * displayable children left.
		 * /
		public void windowClosed(final WindowEvent e)
		{
			synchronized (getTreeLock())
			{
				final Window[] windows=getOwnedWindows();
				for (final Window window : windows)
				{
					if (window!=null)
					{
						if (window.isDisplayable())
						{
							return;
						}
						window.removeWindowListener(this);
					}
				}
				dispose();
			}
		}

		public void windowOpened(final WindowEvent e)
		{
		}

		public void windowClosing(final WindowEvent e)
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

		@Override
		public void show()
		{
			// This frame can never be shown
		}

		@Override
		public void dispose()
		{
			try
			{
				getToolkit().getSystemEventQueue();
				super.dispose();
			}
			catch (Exception e)
			{
				// untrusted code not allowed to dispose
			}
		}
	}

	static WindowListener getSharedOwnerFrameShutdownListener() throws HeadlessException
	{
		final Frame sharedOwnerFrame=getSharedOwnerFrame();
		return (WindowListener)sharedOwnerFrame;
	}
}
*/