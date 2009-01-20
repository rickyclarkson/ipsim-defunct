package ipsim.persistence;

import com.rickyclarkson.xml.*;
import static com.rickyclarkson.xml.DOMSimple.*;
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.util.Collections;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

import java.io.*;
import java.util.*;

public class XMLDeserialiser
{
	private final Map<Integer,Object> objectsRead=Collections.hashMap();
	private final Document input;

	public XMLDeserialiser(@NotNull final String input) throws IOException
	{
		this.input=XMLUtility.xmlToDom(input);
	}

	public <Write,Read> Maybe<Read> readObject(final SerialisationDelegate<Write,Read> delegate, final Function<Object, Read> caster)
	{
		return readObject(DOMSimple.getChildElementNode(input,"object"),delegate,caster);
	}

	@Nullable
	public static String readAttribute(final Node node,final String name)
	{
		return readAttributeImpl(node,name);
	}

	public static Collection<String> getObjectNames(final Node node)
	{
		final Node[] nodes=DOMSimple.getChildNodes(node,"object");

		final List<String> list=Collections.arrayList();

		for (final Node element: nodes)
		{
			final String name=getAttribute(element,"name");

			list.add(name);
		}

		return list;
	}

	@Nullable
	public <Write,Read> Read readObject(final Node node,final String name,final SerialisationDelegate<Write,Read> delegate, final Function<Object,Read> caster)
	{
		final Node[] nodes=DOMSimple.getChildNodes(node,"object");

		for (final Node node2 : nodes)
			if (getAttribute(node2, "name").equals(name))
				return readObject(node2,delegate,caster);

		return null;
	}

	public static String typeOfChild(final Node node,final String name)
	{
		for (final Node node2: DOMSimple.getChildNodes(node,"object"))
			if (getAttribute(node2, "name").equals(name))
				return getAttribute(node2,"serialiser");

		throw null;
	}

	private <Write,Read> Maybe<Read> readObject(final Maybe<Element> maybeNode,final SerialisationDelegate<Write,Read> delegate,final Function<Object,Read> caster)
	{
		return MaybeUtility.bind2(maybeNode,new Function<Element, Read>()
		{
			@NotNull
			public Read run(@NotNull final Element element)
			{
				return readObject(element,delegate,caster);
			}
		});
	}
	
	private <Write,Read> Read readObject(final Node node, final SerialisationDelegate<Write,Read> delegate, final Function<Object,Read> caster)
	{
            final String idString=getAttribute(node,"id");

		final Function<String,Read> function=new Function<String,Read>()
		{
			@NotNull
			public Read run(@NotNull final String idString2)
			{
				final int id=Integer.parseInt(idString2);

				final Object result=objectsRead.get(id);

				if (result!=null)
				try
				{
					return caster.run(result);
				}
				catch (ClassCastException e)
				{
					System.out.println("caster="+caster.getClass()+", result="+result);
					throw e;
				}

				Read object=delegate.construct();

				objectsRead.put(id,object);
				object=delegate.readXML(XMLDeserialiser.this,node,object);
				if (object instanceof Maybe)
					throw null;
				objectsRead.put(id,object);

				return object;
			}
		};

		return function.run(idString);
	}

	@Nullable
	private static String readAttributeImpl(final Node node,final String name)
	{
		final Node[] nodes=DOMSimple.getChildNodes(node,"attribute");

		for (final Node element: nodes)
		{
			final String value=getAttribute(element, "value");

			final String attrName=getAttribute(element, "name");
			if (attrName!=null && attrName.equals(name))
				return value;
		}

		return null;
	}
}