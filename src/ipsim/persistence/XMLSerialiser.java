package ipsim.persistence;

import fpeas.function.*;
import ipsim.property.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.io.*;

public final class XMLSerialiser
{
	private final Writer writer;

	private final StreamProperty<Object> alreadyStored=new StreamProperty<Object>(new Stream<Object>());

	private void write(final String string)
	{
		try
		{
			writer.write(string);
		}
		catch (IOException e)
		{
			throw null;
		}
	}

	public XMLSerialiser(final Writer writer)
	{
		this.writer=writer;

		write("<!DOCTYPE object [\n");
		write("<!ELEMENT object (object|attribute)*>\n");

		write("<!ATTLIST object\n");
		write("name CDATA #IMPLIED\n");
		write("id CDATA #REQUIRED\n");
		write("serialiser CDATA #REQUIRED\n");
		write(">\n");

		write("<!ELEMENT attribute EMPTY>\n");

		write("<!ATTLIST attribute\n");
		write("name CDATA #REQUIRED\n");
		write("value CDATA #REQUIRED\n");
		write(">\n");

		write("]>\n");
	}

	public <Write,Read> void writeObject(@NotNull final Write object,@NotNull final String name,final SerialisationDelegate<Write,Read> serialisable)
	{
		Integer id=alreadyStored.indexOf(object);

		if (id==null)
			id=alreadyStored.size();

		write("<object name=\""+name+"\" serialiser=\""+serialisable.getIdentifier()+"\" id=\""+id+"\">");

		if (id.equals(alreadyStored.size()))
		{
			alreadyStored.append(object);
			if (id.equals(alreadyStored.size()))
				throw null;

			serialisable.writeXML(this, object);
		}

		write("</object>");
	}

	public void writeAttribute(final String name,final String value)
	{
		write("<attribute name=\""+xmlEncode(name)+"\" value=\""+xmlEncode(value)+"\"/>");
	}

	private static String xmlEncode(final String value)
	{
		return value.replaceAll("\"","&quot;");
	}

	public void close()
	{
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			throw null;
		}
	}

	public <T> Function<T, Runnable> writeObjectRef(final String name, final SerialisationDelegate<T, T> delegate)
	{
		return new Function<T, Runnable>()
		{
			@NotNull
			public Runnable run(@NotNull final T t)
			{
				return new Runnable()
				{
					public void run()
					{
						writeObject(t,name,delegate);
					}
				};
			}
		};
	}
}