package ipsim.persistence.delegates;
/*
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import static ipsim.persistence.delegates.DelegateHelper.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class EthernetCardDelegate
{
	public static SerialisationDelegate<Card,Stream<PacketSource>> cardDelegate(final Network network)
	{
		return new SerialisationDelegate<Card,Stream<PacketSource>>()
		{
			public void writeXML(final XMLSerialiser serialiser, final Card card)
			{
				@Nullable
				final CardDrivers drivers=card.withDrivers;

				if (drivers!=null)
				{
					serialiser.writeAttribute("ethNumber", String.valueOf(drivers.ethNumber));
					serialiser.writeAttribute("ipAddress", card.withDrivers.ipAddress.get().toString());
                                        serialiser.writeAttribute("netMask", card.withDrivers.netMask.get().toString());
				}

				DelegateHelper.writePositions(network,serialiser, card);
			}

			public Stream<PacketSource> readXML(final XMLDeserialiser deserialiser, final Node node, Stream<PacketSource> andOthers)
			{
				Card card=andOthers.car().asCard();

				andOthers=andOthers.merge(readPositions(network,deserialiser, node,card));
				if (!andOthers.car().equals(card))
					throw null;

				@Nullable
				final String ethNumber=readAttribute(node, "ethNumber");

				if (ethNumber!=null)
				{
					final int ethNumber1=Integer.parseInt(ethNumber);

					//old versions stored ethNumber of -1 for uninstalled card.
					if (ethNumber1!=-1)
						card.installDeviceDrivers(network).ethNumber=ethNumber1;
				}

				if (card.withDrivers!=null)
				{
					final String ipAddress=readAttribute(node, "ipAddress");

					card.withDrivers.ipAddress.set(IPAddressUtility.valueOf(ipAddress).get());

					String netMask=readAttribute(node, "netMask");
                                        String binaryMask=Integer.toBinaryString(IPAddressUtility.valueOf(netMask).get().rawValue());
                                        
                                        while (binaryMask.contains("01"))
                                            binaryMask=binaryMask.replaceAll("01","00");

					card.withDrivers.netMask.set(NetMaskUtility.netMask((int)Long.parseLong(binaryMask,2)).get());
				}

				return andOthers.ifNotEmpty();
			}

			public Stream<PacketSource> construct()
			{
				return Stream.<PacketSource>one(new Card(false, PositionOrParent.origin));
			}

			@NotNull
			public String getIdentifier()
			{
				return "ipsim.persistence.delegates.EthernetCardDelegate";
			}
		};
	}
        }*/