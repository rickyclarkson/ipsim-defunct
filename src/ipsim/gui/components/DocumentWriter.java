package ipsim.gui.components;

import javax.swing.text.*;
import java.io.*;

public final class DocumentWriter
{
	public static Writer documentWriter(final Document document)
	{
		return new Writer()
		{
			@Override
			public void write(final char[] cbuf, final int off, final int len)
			{
				try
				{
					document.insertString(document.getLength(), new String(cbuf, off, len),null);
				}
				catch (final BadLocationException exception)
				{
					throw new RuntimeException(exception);
				}
			}

			@Override
			public void flush()
			{
			}

			@Override
			public void close()
			{
			}
		};
	}
}