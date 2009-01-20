package com.rickyclarkson.javax.swing;
/*
import fpeas.either.*;
import static ipsim.gui.HelpFrame$.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public final class ScrollableEditorPaneUtility
{
	public static Either<JEditorPane, IOException> createScrollableEditorPane(final JScrollPane scrollPane)
	{
		final JEditorPane editorPane;
		try
		{
			editorPane=new JEditorPane(helpRoot);
		}
		catch (IOException exception)
		{
			return EitherUtility.right(exception);
		}

		final Toolkit toolkit=Toolkit.getDefaultToolkit();
		final AWTEventListener keyHandler=new AWTEventListener()
		{
			public void eventDispatched(final AWTEvent event)
			{
				if (!(event instanceof KeyEvent))
					return;

				if (!event.getSource().equals(editorPane))
					return;

				final KeyEvent keyEvent=(KeyEvent)event;

				if (!(keyEvent.getID()==KeyEvent.KEY_PRESSED))
					return;

				switch (keyEvent.getKeyCode())
				{
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_UP:
						break;

					default:
						return;
				}

				keyEvent.consume();

				final JViewport viewport=scrollPane.getViewport();

				final Rectangle rectangle=viewport.getViewRect().getBounds();

				final int unitIncrement=editorPane.getScrollableUnitIncrement(rectangle, SwingConstants.VERTICAL, 1);

				if (keyEvent.getKeyCode()==KeyEvent.VK_DOWN)
					rectangle.translate(0, unitIncrement);
				else
					rectangle.translate(0, -unitIncrement);

				editorPane.scrollRectToVisible(rectangle);
			}
		};

		toolkit.addAWTEventListener(keyHandler, AWTEvent.KEY_EVENT_MASK);

		return EitherUtility.left(editorPane);
	}
        }*/