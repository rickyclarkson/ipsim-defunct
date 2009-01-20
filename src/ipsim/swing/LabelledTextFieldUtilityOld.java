package ipsim.swing;
/*
import static anylayout.AnyLayout.*;
import anylayout.*;
import anylayout.extras.*;
import static anylayout.extras.ConstraintUtility.*;
import fpeas.function.*;
import static ipsim.lang.Runnables.*;

import javax.swing.*;
import java.awt.*;

public final class LabelledTextFieldUtility
{
	private LabelledTextFieldUtility()
	{
	}

	public static LabelledTextField createLabelledTextField(final String text,final JTextField field)
	{
		final JPanel panel=new JPanel();
		panel.setOpaque(false);

		final JLabel label=new JLabel(text);
		label.setLabelFor(field);
		panel.add(label);
		panel.add(field);

		return new LabelledTextField()
		{
			public Component getPanel()
			{
				return panel;
			}

		};
	}

	public static LabelledTextField createLabelledTextField2(final String text,final JTextField field)
	{
		final JPanel panel=new JPanel();
		panel.setOpaque(false);

		final JLabel label=new JLabel(text);

		useAnyLayout(panel,0.5f,0.5f,new SizeCalculator()
		{
			public int getHeight()
			{
				return Math.max(field.getPreferredSize().height,label.getPreferredSize().height);
			}

			public int getWidth()
			{
				return field.getPreferredSize().width+label.getPreferredSize().width+5;
			}

		},typicalDefaultConstraint(throwRuntimeException));

		final Function<LayoutContext,Integer> constant=FunctionUtility.constant(0);
		panel.add(label,ConstraintUtility.topLeft(constant,constant));
		panel.add(field,RelativeConstraints.rightOf(label,5));

		return new LabelledTextField()
		{
			public Component getPanel()
			{
				return panel;
			}

		};
	}
        }*/