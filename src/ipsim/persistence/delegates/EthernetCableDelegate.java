package ipsim.persistence.delegates;
/*
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class EthernetCableDelegate
{
	public static SerialisationDelegate<Cable,Stream<PacketSource>> cableDelegate(final Network network)
	{
		return new SerialisationDelegate<Cable,Stream<PacketSource>>()
		{
			public void writeXML(final XMLSerialiser serialiser, final Cable cable)
			{
				DelegateHelper.writePositions(network,serialiser, cable);
				serialiser.writeAttribute("cableType", cable.getCableType().toString());
			}

			public Stream<PacketSource> readXML(final XMLDeserialiser deserialiser, final Node node, Stream<PacketSource> andOthers)
			{
				Cable cable=andOthers.car().asCable();
				andOthers=andOthers.merge(DelegateHelper.readPositions(network,deserialiser, node,cable));

				String cableType=readAttribute(node, "cableType");

				if (cableType==null)
					cableType=CableType.STRAIGHT_THROUGH.toString();

				if (cableType.equals(CableType.CROSSOVER.toString()))
					cable.setCableType(CableType.CROSSOVER);
				else
					if (cableType.equals(CableType.STRAIGHT_THROUGH.toString()))
						cable.setCableType(CableType.STRAIGHT_THROUGH);
					else
						if (cableType.equals(CableType.BROKEN.toString()))
							cable.setCableType(CableType.BROKEN);
						else
							throw null;

				return andOthers.ifNotEmpty();
			}

			public Stream<PacketSource> construct()
			{
				return Stream.<PacketSource>one(new Cable(origin,origin));
			}

			@NotNull
			public String getIdentifier()
			{
				return "ipsim.persistence.delegates.EthernetCableDelegate";
			}
		};
	}
        }*/