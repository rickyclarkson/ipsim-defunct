package ipsim.awt;

import fpeas.predicate.*;
import fpeas.sideeffect.*;

import javax.swing.*;
import java.awt.*;

public final class ComponentUtility
{
	/**
	 * Used to centre the dialogs.
	 */
	public static void centreOnParent(final JDialog dialog,final JFrame frame)
	{
		dialog.setLocation(frame.getX()+(frame.getWidth()-dialog.getWidth())/2, frame.getY()+(frame.getHeight()-dialog.getHeight())/2);
	}

	public static SideEffect<Component> setVisibleRef(final boolean visible)
	{
		return new SideEffect<Component>()
		{
			public void run(final Component component)
			{
				component.setVisible(visible);
			}
		};
	}

	public static Component find(final Container container, final Predicate<Component> valid)
	{
		if (valid.invoke(container))
			return container;

		for (final Component component: container.getComponents())
		{
			if (valid.invoke(component))
				return component;

			if (component instanceof Container)
			{
				Component found=find((Container)component,valid);
				if (found!=null)
					return found;
			}
		}

		return null;
	}
}