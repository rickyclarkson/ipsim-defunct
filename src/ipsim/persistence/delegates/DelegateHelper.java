package ipsim.persistence.delegates;
/*
import fpeas.sideeffect.*;
import fpeas.function.*;
import ipsim.*;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.gui.components.*;
import ipsim.lang.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import static ipsim.network.PositionUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import static ipsim.persistence.delegates.ComputerDelegate.*;
import static ipsim.persistence.delegates.EthernetCableDelegate.*;
import static ipsim.persistence.delegates.EthernetCardDelegate.*;
import static ipsim.persistence.delegates.HubDelegate.*;
import static ipsim.persistence.delegates.PointDelegate.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class DelegateHelper
{
	public static void writePositions(final Network network,final XMLSerialiser serialiser,final PacketSource component)
	{
		final int[] a={0};

		children(network.getAll(),component).foreach(new SideEffect<PacketSource>()
		{
			public void run(final PacketSource packetSource)
			{
				writePacketSource(serialiser, packetSource, "child "+a[0], network);
				a[0]++;
			}
		});

		for (int b=0;b<component.numPositions();b++)
		{
			@Nullable
			final PacketSource parent=component.positionData(b).parent;

			if (parent!=null)
				writePacketSource(serialiser,parent,"parent "+b,network);
			else
				serialiser.writeObject(component.positionData(b).point,"point "+b, pointDelegate);
		}
	}

	public static Stream<PacketSource> readPositions(final Network network, final XMLDeserialiser deserialiser, final Node node,PacketSource packetSource)
	{
		Stream<PacketSource> andOthers=new Stream<PacketSource>();
		for (final String nodeName: getObjectNames(node))
		{
			if (nodeName.startsWith("parent "))
			{
				final int a=Integer.parseInt(nodeName.substring("parent ".length()));

				final Stream<PacketSource> parent=readFromDeserialiser(deserialiser, node, nodeName, network).ifNotEmpty();

				andOthers=andOthers.merge(parent);
				packetSource.setPositionData(a, asNotNull(parent(parent.car())));
			}

			if (nodeName.startsWith("point "))
			{
				final String string=nodeName.substring("point ".length());

				final int a=Integer.parseInt(string);

				try
				{
					packetSource.setPositionData(a,PositionOrParent.position(asNotNull(deserialiser.readObject(node,nodeName, pointDelegate, new Function<Object, Point>()
					{
						@NotNull
						public Point run(@NotNull final Object o)
						{
							return (Point)o;
						}
					}))));
				}
				catch (ClassCastException e)
				{
					throw e;
				}
			}

			if (nodeName.startsWith("child "))
				andOthers=andOthers.merge(readFromDeserialiser(deserialiser,node,nodeName,network));
		}

		return andOthers;
	}

	public static Stream<PacketSource> readFromDeserialiser(final XMLDeserialiser deserialiser, final Node node, final String name,final Network network)
	{
		final String type=typeOfChild(node,name);

		final Function<Object,Stream<PacketSource>> asStream=Caster.asStreamOfType(PacketSource.class);

		if (hubDelegate(network).getIdentifier().equals(type))
			return deserialiser.readObject(node,name, hubDelegate(network), asStream).ifNotEmpty();

		if (computerDelegate(network).getIdentifier().equals(type))
			return deserialiser.readObject(node,name, computerDelegate(network), asStream).ifNotEmpty();

		if (cardDelegate(network).getIdentifier().equals(type))
			return deserialiser.readObject(node,name, cardDelegate(network), asStream).ifNotEmpty();

		if (cableDelegate(network).getIdentifier().equals(type))
			return deserialiser.readObject(node,name, cableDelegate(network), asStream).ifNotEmpty();

		throw null;
	}

	public static void writePacketSource(final XMLSerialiser serialiser, final PacketSource packetSource, final String name, final Network network)
	{
		final PacketSourceVisitor2 v=new PacketSourceVisitor2();

		v.cardEffect=new SideEffect<Card>()
		{
			public void run(final Card card)
			{
				serialiser.writeObject(card,name,cardDelegate(network));
			}
		};

		v.computerEffect=new SideEffect<Computer>()
		{
			public void run(final Computer computer)
			{
				serialiser.writeObject(computer, name, computerDelegate(network));
			}
		};

		v.cableEffect=new SideEffect<Cable>()
		{
			public void run(final Cable cable)
			{
				serialiser.writeObject(cable, name, cableDelegate(network));
			}
		};

		v.hubEffect=new SideEffect<Hub>()
		{
			public void run(final Hub hub)
			{
				serialiser.writeObject(hub, name, hubDelegate(network));
			}
		};

		packetSource.accept(v);
	}
}*/