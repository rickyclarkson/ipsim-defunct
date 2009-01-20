package ipsim.swing;
/*
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import ipsim.textmetrics.*;
import ipsim.network.NetMask;
import ipsim.network.IPAddress$;
import ipsim.network.IPAddress;

import javax.swing.*;
import java.awt.*;

public final class SubnetMaskTextField extends JTextField
{
	private static final long serialVersionUID=-1975041645986840656L;

	private final transient IPAddressValidator validator;

	public SubnetMaskTextField()
	{
            validator=new IPAddressValidator(IPAddressUtility.zero());

		final ValidatingDocumentListener listener=new ValidatingDocumentListener(this, getBackground(), Color.pink, validator);

		getDocument().addDocumentListener(listener);
	}

	public NetMask getNetMask()
	{
            return netMask(validator.getAddress().rawValue()).get();
	}

	public void setNetMask(final NetMask address)
	{
            validator.setIPAddress(new IPAddress(address.rawValue()));

            if (0==address.rawValue())
			setText("");
		else
                    setText(address.toString());
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(TextMetrics.getWidth(getFont(),"999.999.999.999/99"),(int)super.getPreferredSize().getHeight());
	}

	public boolean isValidText()
	{
		return validator.isValid(getDocument());
	}
        }*/