package ipsim.persistence.delegates;
/*
import fpeas.function.*;
import ipsim.*;
import ipsim.network.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetMask.*;
import ipsim.network.ip.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class ProblemDelegate
{
	public static final SerialisationDelegate<Problem,Problem> problemDelegate=new SerialisationDelegate<Problem,Problem>()
	{
		public void writeXML(final XMLSerialiser serialiser, final Problem problem)
		{
                    serialiser.writeAttribute("networkNumber", problem.netBlock().networkNumber().toString());
                    serialiser.writeAttribute("subnetMask", problem.netBlock().netMask().toString());
                    serialiser.writeAttribute("numberOfSubnets", String.valueOf(problem.numberOfSubnets()));
		}

		public Problem readXML(final XMLDeserialiser deserialiser, final Node node, final Problem object)
		{
			final Function<String, IPAddress> parse=new Function<String, IPAddress>()
			{
				@NotNull
				public IPAddress run(@NotNull final String ipAddress)
				{
                                    return IPAddressUtility.valueOf(ipAddress).get();
				}
			};

			final Function<String, NetMask> parseNetMask=new Function<String, NetMask>()
			{
				@NotNull
				public NetMask run(@NotNull final String netMask)
				{
                                    return NetMaskUtility.valueOf(netMask).get();
				}
			};

			final IPAddress address=parse.run(Caster.asNotNull(readAttribute(node, "networkNumber")));
			final NetMask mask=parseNetMask.run(Caster.asNotNull(readAttribute(node, "subnetMask")));

			final NetBlock block=new NetBlock(address, mask);

			return new Problem(block, Integer.parseInt(readAttribute(node, "numberOfSubnets")));
		}

		public Problem construct()
		{
			return null;
		}

		@NotNull
		public String getIdentifier()
		{
			return "ipsim.persistence.delegates.ProblemDelegate";
		}
	};
        }*/