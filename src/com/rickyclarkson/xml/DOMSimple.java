package com.rickyclarkson.xml;

import static fpeas.maybe.MaybeUtility.just;
import static fpeas.maybe.MaybeUtility.nothing;
import fpeas.maybe.*;
import org.w3c.dom.*;

import java.util.*;

public final class DOMSimple
{
	public static Node[] getChildNodes(final Node root,final String name)
	{
		final List<Node> nodes=new ArrayList<Node>();

		final NodeList children=root.getChildNodes();

		for (int a=0;a<children.getLength();a++)
		{
			final Node node=children.item(a);

			if (node.getNodeName().equals(name))
				nodes.add(node);
		}

		final Node[] answer=new Node[nodes.size()];

		nodes.toArray(answer);

		return answer;
	}

	public static Maybe<Element> getChildElementNode(final Node root,final String name)
	{
		final NodeList children=root.getChildNodes();

		for (int a=0;a<children.getLength();a++)
		{
			final Node node=children.item(a);

			if (node instanceof Element && node.getNodeName().equals(name))
                            return just((Element)node);
		}

		return nothing();
	}

	public static String getAttribute(final Node node,final String name)
	{
		if (!node.hasAttributes())
			throw new IllegalArgumentException();

		final Node item=node.getAttributes().getNamedItem(name);

		return item.getNodeValue();
	}
}
