package ipsim.persistence.delegates;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import fpeas.maybe.*;
import fpeas.lazy.Lazy;
import ipsim.*;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.lang.*;
import static ipsim.lang.Objects.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.computer.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import static ipsim.persistence.delegates.RoutingTableDelegate.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;
import fpeas.lazy.Lazy;
import java.io.*;

public class ComputerDelegate
{
	public static final Lazy<Boolean> testSerialisationOfForwarding=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Computer computer=new Computer(position(new Point(100, 100)));
			computer.ipForwardingEnabled=true;

			final StringWriter stringWriter=new StringWriter();

			final Network network=new Network(null);

			computer.setPositionData(0, position(new Point(100, 100)));

			final XMLSerialiser serialiser=new XMLSerialiser(stringWriter);

			serialiser.writeObject(computer, "computer", computerDelegate(network));

			try
			{
				final Function<Stream<PacketSource>, Stream<PacketSource>> identity=identity();
				return lift(new XMLDeserialiser(stringWriter.getBuffer().toString()).readObject(computerDelegate(network), asStreamOfType(PacketSource.class)),null,identity).car().asComputer().ipForwardingEnabled;
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public String toString()
		{
			return "testSerialisationOfForwarding";
		}
	};

	public static SerialisationDelegate<Computer,Stream<PacketSource>> computerDelegate(final Network network)
	{
		return new SerialisationDelegate<Computer,Stream<PacketSource>>()
		{
			public void writeXML(final XMLSerialiser serialiser, final Computer computer)
			{
				serialiser.writeAttribute("ipForwardingEnabled", String.valueOf(computer.ipForwardingEnabled));
				final int computerId=computer.computerID;

				serialiser.writeAttribute("computerId", String.valueOf(computerId));
				serialiser.writeAttribute("isISP",String.valueOf(computer.isISP));

				DelegateHelper.writePositions(network,serialiser, computer);

				serialiser.writeObject(computer.routingTable, "routingTable", routingTableDelegate(network));
			}

			public Stream<PacketSource> readXML(final XMLDeserialiser deserialiser, final Node node, Stream<PacketSource> andOthers)
			{
				Computer computer=andOthers.car().asComputer();

				computer.ipForwardingEnabled=Boolean.valueOf(Caster.asNotNull(readAttribute(node, "ipForwardingEnabled")));
				computer.computerID=lift(readAttribute(node,"computerID"),network.generateComputerIDRef, Integers.parseIntLazy).invoke();
				computer.isISP=lift(readAttribute(node,"isISP"),false,Booleans.toString);

				andOthers=andOthers.merge(DelegateHelper.readPositions(network,deserialiser, node,computer));

				final RoutingTable routingTable=deserialiser.readObject(node, "routingTable", routingTableDelegate(network), new Function<Object, RoutingTable>()
				{
					@NotNull
					public RoutingTable run(@NotNull final Object o)
					{
						return (RoutingTable)o;
					}
				});

				final RoutingTable originalRoutingTable=computer.routingTable;

				for (final Route route : routingTable.routes())
					originalRoutingTable.add(network, MaybeUtility.<Computer>nothing(), route);

				return andOthers.ifNotEmpty();
			}

			public Stream<PacketSource> construct()
			{
				return Stream.<PacketSource>one(new Computer(PositionOrParent.origin));
			}

			@NotNull
			public String getIdentifier()
			{
				return "ipsim.persistence.delegates.ComputerDelegate";
			}
		};
	}
}
*/