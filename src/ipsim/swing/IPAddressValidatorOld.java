package ipsim.swing;
/*
 import ipsim.network.connectivity.ip.*;
 import ipsim.network.ip.*;
 import ipsim.network.IPAddress;
import javax.swing.text.*;
import ipsim.web.Web;

public final class IPAddressValidator implements DocumentValidator
{
	private IPAddress address;

	public IPAddressValidator(final IPAddress address)
	{
		this.address=address;
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

                OptionIPAddress tmp=valueOf(string);
                if (!tmp.isEmpty())
                    address=tmp.get();

                return !tmp.isEmpty();
	}

	public void setIPAddress(final IPAddress ipAddress)
	{
		address=ipAddress;
	}

	public IPAddress getAddress()
	{
		return address;
	}
        }*/