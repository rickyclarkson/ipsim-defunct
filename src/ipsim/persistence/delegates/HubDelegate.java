package ipsim.persistence.delegates;
/*
import fpeas.predicate.*;
import static ipsim.Caster.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.hub.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class HubDelegate
{
	public static SerialisationDelegate<Hub,Stream<PacketSource>> hubDelegate(final Network network)
	{
		return new SerialisationDelegate<Hub,Stream<PacketSource>>()
		{
			public void writeXML(final XMLSerialiser serialiser, final Hub hub)
			{
				serialiser.writeAttribute("isPowerOn", String.valueOf(hub.isPowerOn()));

				DelegateHelper.writePositions(network, serialiser, hub);
			}

			public Stream<PacketSource> readXML(final XMLDeserialiser deserialiser, final Node node, Stream<PacketSource> hubAndOthers)
			{
				final Hub hub=hubAndOthers.car().asHub();

				@Nullable
				final String power=readAttribute(node, "isPowerOn");

				final Predicate<String> equalT=equalT(power);
				final Boolean equal=equalT.invoke("true");
				hub.setPower(equal);

				final Stream<PacketSource> otherStream=DelegateHelper.readPositions(network, deserialiser, node,hub);

				return hubAndOthers.merge(otherStream).ifNotEmpty();
			}

			public Stream<PacketSource> construct()
			{
				return Stream.<PacketSource>one(new Hub(PositionOrParent.position(new Point(200, 200))));
			}

			@NotNull
			public String getIdentifier()
			{
				return "ipsim.persistence.delegates.HubDelegate";
			}
		};
	}
        }*/