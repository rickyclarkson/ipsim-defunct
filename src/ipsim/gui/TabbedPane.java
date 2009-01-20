package ipsim.gui;

import fpeas.sideeffect.*;
import ipsim.*;
import static ipsim.awt.ComponentUtility.*;
import static ipsim.lang.Classes.*;
import static ipsim.lang.Predicates.*;
import ipsim.network.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class TabbedPane
{
	public final JTabbedPane wrapped=new JTabbedPane();

	public int getSelectedIndex()
	{
		return wrapped.getSelectedIndex();
	}

	public NetworkView getSelectedComponent()
	{
		return NetworkView.class.cast(find((Container)wrapped.getSelectedComponent(), covariant(isAssignableFrom(NetworkView.class))));
	}

	public int indexOfComponent(final NetworkView networkView)
	{
		for (int a=0;a<wrapped.getTabCount();a++)
		{
			NetworkView another=NetworkView.class.cast(find((Container)wrapped.getComponent(a),covariant(isAssignableFrom(NetworkView.class))));

			if (another.equals(networkView))
				return a;
		}

		throw null;
	}

	public void setSelectedComponent(final NetworkView view)
	{
		wrapped.setSelectedIndex(indexOfComponent(view));
	}

	public int getTabCount()
	{
		return wrapped.getTabCount();
	}

	public void addTab(final String title, final NetworkView view)
	{
		for (final NetworkContext context: Global.network.contexts)
			if (context.problem.listeners.isEmpty())
				context.problem.listeners.add(new SideEffect<Problem>()
				{
					public void run(final Problem problem)
					{
						wrapped.getChangeListeners()[0].stateChanged(null);
					}
				});

		wrapped.addTab(title,new JScrollPane(view));
	}

	public void removeAll()
	{
		wrapped.removeAll();
	}

	public void addChangeListener(final ChangeListener changeListener)
	{
		wrapped.addChangeListener(changeListener);
	}
}