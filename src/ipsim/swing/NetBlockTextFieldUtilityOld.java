package ipsim.swing;
/*
import static fpeas.either.EitherUtility.*;
import fpeas.lazy.*;
import ipsim.gui.components.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetBlock$.*;
import ipsim.textmetrics.*;

import javax.swing.*;
import java.awt.*;

import ipsim.network.NetBlock;
import ipsim.network.NetBlock$;

public final class NetBlockTextFieldUtility
{
	private NetBlockTextFieldUtility()
	{
	}

	public static NetBlockTextField createNetBlockTextField()
	{
		final JTextField textField=new JTextField()
		{
			private static final long serialVersionUID=-8215166609438040479L;

			@Override
			public Dimension getPreferredSize()
			{
				return new Dimension
						(
								TextMetrics.getWidth(getFont(), "999.999.999.999/99")+13,
								(int)super.getPreferredSize().getHeight()
						);
			}
		};

		final NetBlockValidator validator=new NetBlockValidator(NetBlock$.MODULE$.zero());

		final ValidatingDocumentListener listener=new ValidatingDocumentListener(textField, UIManager.getColor("TextField.background"), Color.pink, validator);

		textField.getDocument().addDocumentListener(listener);

		return instance(validator, textField);
	}

	public static NetBlockTextField instance(final NetBlockValidator validator, final JTextField textField)
	{
		return new NetBlockTextField()
		{
			public final Lazy<NetBlock> netBlock=new Lazy<NetBlock>()
			{
				public NetBlock invoke()
				{
					return validator.getNetBlock();
				}

			};

			public Lazy<NetBlock> netBlock()
			{
				return netBlock;
			}

			public boolean isValid()
			{
				return validator.isValid(textField.getDocument());
			}

			public JTextField getComponent()
			{
				return textField;
			}
		};
	}
        }*/