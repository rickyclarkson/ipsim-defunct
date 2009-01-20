package ipsim.persistence.delegates;
/*
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class PointDelegate
{
	public static final SerialisationDelegate<Point,Point> pointDelegate=new SerialisationDelegate<Point,Point>()
	{
		public void writeXML(final XMLSerialiser serialiser, final Point point)
		{
			serialiser.writeAttribute("x", String.valueOf(point.x()));
			serialiser.writeAttribute("y", String.valueOf(point.y()));
		}

		public Point readXML(final XMLDeserialiser deserialiser, final Node node, Point point)
		{
			return new Point(Double.parseDouble(readAttribute(node, "x")),Double.parseDouble(readAttribute(node, "y")));
		}

		public Point construct()
		{
			return new Point((double)0, (double)0);
		}

		@NotNull
		public String getIdentifier()
		{
			return "ipsim.persistence.delegates.PointDelegate";
		}
	};
}
*/