package ipsim.swing;
/*
import fpeas.either.*;
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetBlock$.*;
import ipsim.network.NetBlock;
import javax.swing.text.*;
import ipsim.network.NetBlock$;

public final class NetBlockValidator implements DocumentValidator
{
	private NetBlock block;

	public NetBlockValidator(final NetBlock block)
	{
		this.block=block;
	}

	public boolean isValid(final Document document)
	{
		final String string;

		try
		{
			string=document.getText(0,document.getLength());
		}
		catch (final BadLocationException exception)
		{
			throw new RuntimeException(exception);
		}

		OptionNetBlock option=NetBlockUtility.valueOf(string);
                
		if (!option.isEmpty())
                    block=option.get();

		return !option.isEmpty();
	}

	public NetBlock getNetBlock()
	{
		return block;
	}
        }*/