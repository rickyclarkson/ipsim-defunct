package ipsim.swing;

import anylayout.*;
import anylayout.extras.*;
import static anylayout.extras.ConstraintBuilder.*;
import static anylayout.extras.ConstraintUtility.*;
import static anylayout.extras.LayoutContextUtility.*;
import static anylayout.extras.RelativeConstraints.*;
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import fpeas.predicate.*;
import static ipsim.Caster.*;
import static ipsim.swing.Dialogs.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import static javax.swing.SwingConstants.*;
import static javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CustomJOptionPane
{
	private static final int PADDING=10;
	private static final Function<LayoutContext, Integer> paddingFunction=constant(PADDING);

	public static int showYesNoCancelDialog(final JFrame frame, final String message, final String title)
	{
		final Maybe<JDialog> dialog=createDialogWithEscapeKeyToClose(fromNullable(frame), title);

		return MaybeUtility.constIfNothing(dialog, null, new Function<JDialog, Integer>()
		{
			@NotNull
			public Integer run(@NotNull final JDialog jDialog)
			{
				return impl(frame, jDialog, message);
			}
		});
	}

	public static int impl(final JFrame frame, final JDialog dialog, final String message)
	{
		dialog.setModal(true);

		final JLabel messageLabel=new JLabel(message, getIcon("OptionPane.questionIcon"), LEFT);

		final JButton yesButton=new JButton("Yes");
		final JButton noButton=new JButton("No");
		final JButton cancelButton=new JButton("Cancel");

		final JButton[] buttons={yesButton,noButton,cancelButton};

		yesButton.setMnemonic(KeyEvent.VK_Y);
		noButton.setMnemonic(KeyEvent.VK_N);
		cancelButton.setMnemonic(KeyEvent.VK_C);

		final KeyListener keyListener=new KeyListener()
		{
			public void keyTyped(final KeyEvent e)
			{
				final Predicate<Character> equal=equalT(e.getKeyChar());
				if (equal.invoke('y'))
					yesButton.doClick(100);
				if (equal.invoke('n'))
					noButton.doClick(100);
				if (equal.invoke('c'))
					cancelButton.doClick(100);
			}

			public void keyPressed(final KeyEvent e)
			{
				if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
					cancelButton.doClick(100);
			}

			public void keyReleased(final KeyEvent e)
			{
			}
		};

		for (final JButton button : buttons)
			button.addKeyListener(keyListener);

		final Constraint messageConstraint=topLeft(paddingFunction, paddingFunction);
		final Constraint yesConstraint=bottomLeft(paddingFunction, paddingFunction);
		final Constraint cancelConstraint=bottomRight(paddingFunction, paddingFunction);
		final Constraint noConstraint=halfwayBetween(yesButton, cancelButton);

		final SizeCalculator sizeCalculator=new SizeCalculator()
		{
			public int getWidth()
			{
				final int yesWidth=yesButton.getPreferredSize().width;
				final int noWidth=noButton.getPreferredSize().width;
				final Dimension cancelPreferredSize=cancelButton.getPreferredSize();

				return Math.max(PADDING+messageLabel.getPreferredSize().width+PADDING, PADDING+yesWidth+PADDING+noWidth+PADDING+cancelPreferredSize.width+PADDING);
			}

			public int getHeight()
			{
				return PADDING*3+messageLabel.getPreferredSize().height+cancelButton.getPreferredSize().height;
			}
		};

		AnyLayout.useAnyLayout(dialog.getContentPane(), 0.5f, 0.5f, sizeCalculator, null);

		dialog.add(messageLabel, messageConstraint);
		dialog.add(yesButton, yesConstraint);
		dialog.add(noButton, noConstraint);
		dialog.add(cancelButton, cancelConstraint);

		final int[] returnValue={JOptionPane.CANCEL_OPTION};

		final Map<JButton, Integer> returnValues=new HashMap<JButton, Integer>();

		returnValues.put(yesButton, JOptionPane.YES_OPTION);
		returnValues.put(noButton, JOptionPane.NO_OPTION);
		returnValues.put(cancelButton, JOptionPane.CANCEL_OPTION);

		for (final JButton button : buttons)
			button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent e)
				{
					if (!returnValues.containsKey(button))
						throw new RuntimeException();

					returnValue[0]=returnValues.get(button);

					dialog.setVisible(false);
				}
			});

		dialog.pack();
		dialog.setLocationRelativeTo(frame);

		dialog.setVisible(true);

		return returnValue[0];
	}

	public static CustomJOptionPaneResult showLabelsAndConfirmation(final JFrame frame, final String title, final String message, final String[] choices, final int theDefault, final String toConfirm)
	{
		final JDialog dialog=createDialogWithEscapeKeyToClose(frame, title);

		return impl2(frame, dialog, message, choices, theDefault, toConfirm);
	}

	public static CustomJOptionPaneResult impl2(final JFrame frame, final JDialog dialog, final String message, final String[] choices, final int theDefault, final String toConfirm)
	{
		final List<JButton> buttons=new ArrayList<JButton>();

		final String[] choice={null};

		for (final String aChoice : choices)
			buttons.add(new JButton(aChoice));

		final JCheckBox checkBox=new JCheckBox();

		final JLabel messageLabel=new JLabel(message, UIManager.getIcon("OptionPane.questionIcon"), SwingConstants.LEFT);
		final JLabel confirmationLabel=new JLabel(toConfirm);

		final SizeCalculator calculator=new SizeCalculator()
		{
			public int getWidth()
			{
				final Dimension messageSize=messageLabel.getPreferredSize();

				final Dimension max=getPreferredSize(buttons);

				final Dimension aPreferredSize=confirmationLabel.getPreferredSize();
				final int width=Math.max(Math.max(messageSize.width, max.width), aPreferredSize.width);

				return width+PADDING*2;
			}

			public int getHeight()
			{
				final Dimension messageSize=messageLabel.getPreferredSize();

				final Dimension max=getPreferredSize(buttons);

				return max.height+PADDING*4+messageSize.height+confirmationLabel.getPreferredSize().height;
			}
		};

		dialog.setModal(true);

		AnyLayout.useAnyLayout(dialog.getContentPane(), 0.5f, 0.5f, calculator, null);

		final Constraint messageConstraint=ConstraintUtility.topCentre(PADDING);

		final Constraint confirmationConstraint=buildConstraint().setLeft(paddingFunction).setTop(add(getFarOffset(messageLabel), paddingFunction)).setWidth(preferredSize).setHeight(preferredSize);

		final Constraint firstButtonConstraint=ConstraintBuilder.buildConstraint().setLeft(paddingFunction).setTop(new Function<LayoutContext, Integer>()
		{
			@NotNull
			public Integer run(@NotNull final LayoutContext context)
			{
				return context.getLayoutInfo(confirmationLabel).getFarOffset()+PADDING;
			}
		}).setWidth(preferredSize).setHeight(preferredSize);

		final Constraint checkBoxConstraint=RelativeConstraints.rightOf(confirmationLabel, PADDING);

		dialog.add(messageLabel, messageConstraint);

		dialog.add(buttons.get(0), firstButtonConstraint);

		JButton previousButton=buttons.get(0);

		for (final JButton button : buttons.subList(1, buttons.size()))
		{
			dialog.add(button, RelativeConstraints.rightOf(previousButton, PADDING));
			previousButton=button;
		}

		buttons.get(theDefault).requestFocusInWindow();

		dialog.add(confirmationLabel, confirmationConstraint);

		dialog.add(checkBox, checkBoxConstraint);

		dialog.pack();
		JDialogUtility.setLocationRelativeTo(dialog, frame);

		for (final JButton button : buttons)
			button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent event)
				{
					choice[0]=button.getText();
					dialog.setVisible(false);
				}
			});
		
		dialog.setVisible(true);

		return new CustomJOptionPaneResult()
		{
			public boolean confirmationTicked()
			{
				return checkBox.isSelected();
			}

			public String getChoice()
			{
				return choice[0];
			}
		};
	}

	static Dimension getPreferredSize(final List<JButton> buttons)
	{
		int maxWidth=buttons.get(0).getPreferredSize().width;
		int maxHeight=buttons.get(0).getPreferredSize().height;

		for (final JButton button : buttons.subList(1, buttons.size()))
		{
			maxWidth+=PADDING+button.getPreferredSize().width;
			maxHeight=Math.max(maxHeight, button.getPreferredSize().height);
		}

		return new Dimension(maxWidth, maxHeight);
	}

	public static void showNonModalMessageDialog(final JFrame parent, final String title, final String message)
	{
		showNonModalMessageDialog(parent, title, new JLabel("<html>"+message+"</html>", getIcon("OptionPane.questionIcon"), LEFT));
	}

	public static void showNonModalMessageDialog(final JFrame parent, final String title, final Component component)
	{
		final JDialog dialog=createDialogWithEscapeKeyToClose(parent, title);

		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		final Container container=dialog.getContentPane();

		final JButton okButton=new JButton("OK");

		AnyLayout.useAnyLayout(container, 0.5f, 0.5f, new SizeCalculator()
		{
			public int getHeight()
			{
				return PADDING*3+component.getPreferredSize().height+okButton.getPreferredSize().height;
			}

			public int getWidth()
			{
				return component.getPreferredSize().width+PADDING*2;
			}
		}, null);

		container.add(component, ConstraintUtility.topCentre(PADDING));
		container.add(okButton, ConstraintUtility.bottomCentre(paddingFunction));

		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				dialog.dispose();
			}
		});

		dialog.pack();
		JDialogUtility.setLocationRelativeTo(dialog, parent);

		dialog.setVisible(true);
	}
}