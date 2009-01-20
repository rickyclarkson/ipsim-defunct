package ipsim.network.conformance;
/*
import fpeas.function.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import ipsim.util.*;
import ipsim.util.Collections;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.util.*;

class PercentUniqueIPAddresses extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Map<IPAddress, Integer> count=Collections.hashMap();

		int total=0;

		for (final Card card : NetworkUtility.getAllCards(network))
		{
			final CardDrivers cardWithDrivers=card.withDrivers;

			if (cardWithDrivers==null)
				total--;
			else
			{
                            if (0==cardWithDrivers.ipAddress.get().rawValue())
					total--;
				else
				{
					if (null!=count.get(cardWithDrivers.ipAddress.get()))
						count.put(cardWithDrivers.ipAddress.get(), count.get(cardWithDrivers.ipAddress.get())+1);
					else
						count.put(cardWithDrivers.ipAddress.get(), 1);
				}
			}

			total++;
		}

		final int percent=((Integer)0).equals(total) ? 100 : count(count.values(), equalT(1))*100/total;
		final Stream<PacketSource> empty=new Stream<PacketSource>();

		return new CheckResult(percent, Stream.one(100-percent+"% of the IP addresses are identical"), empty);
	}
        }*/