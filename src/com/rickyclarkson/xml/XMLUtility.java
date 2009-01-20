package com.rickyclarkson.xml;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.jetbrains.annotations.*;

import javax.xml.parsers.*;
import java.io.*;

public final class XMLUtility
{
	public static Document xmlToDom(@NotNull final String input) throws IOException
	{
		final DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		factory.setValidating(false);

		final DocumentBuilder builder;

		try
		{
			builder=factory.newDocumentBuilder();

			final ErrorHandler errorHandler=new ErrorHandler()
			{
				public void warning(final SAXParseException exception)
				{
				}
				public void error(final SAXParseException exception)
				{
					warning(exception);
				}

				public void fatalError(final SAXParseException exception)
				{
					warning(exception);
				}
			};

			builder.setErrorHandler(errorHandler);
		}
		catch (final ParserConfigurationException exception)
		{
			throw new RuntimeException(exception);
		}

		final Document document;

		try
		{
			document=builder.parse(new InputSource(new StringReader(input)));
		}
		catch (final SAXException exception)
		{
			throw new IOException("Malformed XML document");
		}

		return document;
	}
}
