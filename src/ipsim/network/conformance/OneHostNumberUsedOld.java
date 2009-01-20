package ipsim.network.conformance;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;

import java.util.*;

class OneHostNumberUsed extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final List<PacketSource> warnings=arrayList();

		for (final Card card: NetworkUtility.getAllCards(network))
		{
			@Nullable
			final CardDrivers cardWithDrivers=card.withDrivers;

			if (cardWithDrivers==null)
				continue;

			final IPAddress ipAddress=cardWithDrivers.ipAddress.get();
			final NetMask netMask=cardWithDrivers.netMask.get();

			final int x=ipAddress.rawValue()&~netMask.rawValue();
			final int y=~netMask.rawValue();
			if (x==y)
				warnings.add(card);
		}

		if (warnings.isEmpty())
			return CheckResultUtility.fine();

		return new CheckResult(TypicalScores.USUAL, one("All-1s host number"), Stream.fromIterable(warnings));
	}
        }*/