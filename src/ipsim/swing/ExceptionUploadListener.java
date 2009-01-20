package ipsim.swing;

import ipsim.network.*;
import ipsim.web.Web;

import javax.swing.*;
import java.awt.event.*;
import ipsim.lang.Throwables;

public final class ExceptionUploadListener implements ActionListener
{
	private final Throwable exception;
	private final Network network;
	private final JFrame frame;

	public ExceptionUploadListener(final JFrame frame,final Throwable exception,Network network)
	{
		this.exception=exception;
		this.network=network;
		this.frame=frame;
	}

	public void actionPerformed(final ActionEvent event)
	{
		Web.putException(frame,Throwables.asString(exception), network.saveToString());
	}
}