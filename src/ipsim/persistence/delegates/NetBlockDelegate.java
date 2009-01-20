package ipsim.persistence.delegates;
/*
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;
import ipsim.network.NetBlock;
import ipsim.network.NetMask;
import ipsim.network.IPAddress;
import ipsim.network.IPAddressUtility;
import ipsim.network.NetMaskUtility;

public final class NetBlockDelegate
{
	public static final SerialisationDelegate<NetBlock,NetBlock> netBlockDelegate=new SerialisationDelegate<NetBlock,NetBlock>()
	{
		public void writeXML(final XMLSerialiser serialiser, final NetBlock object)
		{
                    serialiser.writeAttribute("netmask", object.netMask().toString());

                    serialiser.writeAttribute("networkNumber", object.networkNumber().toString());
		}

		public NetBlock readXML(final XMLDeserialiser deserialiser, final Node node, final NetBlock serialisable)
		{
                    IPAddress netNum=IPAddressUtility.valueOf(readAttribute(node, "networkNumber")).get();
                    final NetMask netMask=NetMaskUtility.valueOf(readAttribute(node, "netmask")).get();
                    netNum=new IPAddress(netNum.$amp(netMask));
                    return new NetBlock(netNum, netMask);
		}

		public NetBlock construct()
		{
			return null;
		}

		@NotNull
		public String getIdentifier()
		{
			return "ipsim.persistence.delegates.NetBlockDelegate";
		}
	};
        }*/