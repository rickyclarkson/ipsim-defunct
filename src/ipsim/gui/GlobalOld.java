package ipsim.gui;
/*
import static ipsim.lang.Objects.lift;
import fpeas.lazy.*;
import ipsim.*;
import static ipsim.io.Files.*;
import ipsim.network.*;
import ipsim.property.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Random;

public final class Global
{
    public static final Random random=new Random();
	public static final JFrame frame=MainFrameUtility.createMainFrame(random);
	public static final TabbedPane tabbedPane=new TabbedPane();

	static
	{
		tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (tabbedPane.wrapped.getSelectedIndex()>=0)
				{
					final Problem problem=networkContext().problem.value;
					statusBar.setText(problem==null ? "No problem set" : problem.toString());
				}
			}
		});
	}

	public static final JLabel statusBar=new JLabel();
	public static final JButton editProblemButton=new JButton("Edit Problem");

	public static final Network network=new Network(null);

	public static final JFileChooser fileChooser=new JFileChooser(){{ setFileFilter(new FileNameExtensionFilter("IPSim files","ipsim")); }};

	public static final Property2<File> filename=new Property2<File>()
	{
		File value=null;

		public File get()
		{
			return value;
		}

		public void set(File value)
		{
			this.value=value;
			frame.setTitle(lift(value,"Untitled", getNameRef));
		}
	};

	public static final Lazy<NetworkContext> networkContextRef=new Lazy<NetworkContext>()
	{
		public NetworkContext invoke()
		{
			return networkContext();
		}
	};

	static
	{
		editProblemButton.setFocusable(false);
	}

	public static NetworkContext networkContext()
	{
		return network.contexts.get().get(tabbedPane.getSelectedIndex());
	}

	public static NetworkView networkView()
	{
		return NetworkView.class.cast(tabbedPane.getSelectedComponent());
	}
}
*/