package ipsim.swing;

import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import static ipsim.awt.ActionListeners.*;
import static ipsim.awt.ComponentUtility.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.event.*;

public class Dialogs
{
	public static Maybe<JDialog> createDialogWithEscapeKeyToClose(final Maybe<JFrame> parent, final String title)
	{
		return MaybeUtility.constIfNothing(parent,MaybeUtility.<JDialog>nothing(),new Function<JFrame, Maybe<JDialog>>()
		{
			@NotNull
			public Maybe<JDialog> run(@NotNull final JFrame jFrame)
			{
				return just(createDialogWithEscapeKeyToClose(jFrame,title));
			}
		});
	}

	public static JDialog createDialogWithEscapeKeyToClose(final JFrame parent, final String title)
	{
		final JDialog result=new JDialog(parent,title);

		final ActionListener dispose=fromSideEffect(setVisibleRef(false),result);

		result.getRootPane().registerKeyboardAction(dispose,KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
		return result;
	}
}
