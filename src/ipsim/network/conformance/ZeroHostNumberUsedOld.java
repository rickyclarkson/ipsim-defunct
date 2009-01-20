package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import org.jetbrains.annotations.*;

class ZeroHostNumberUsed extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		return NonsensicalArrangement.customCheck(getAllCards,new FunctionOOO<Network,Card, Maybe<String>>()
		{
			@Override
			@NotNull
			public Maybe<String> invoke(Network notwork,@NotNull final Card card)
			{
				@Nullable
				final CardDrivers cardWithDrivers=card.withDrivers;

				if (cardWithDrivers==null)
					return nothing();

				final IPAddress address=cardWithDrivers.ipAddress.get();
				if (0==address.rawValue())
					return nothing();

				if (0==(address.rawValue()&~cardWithDrivers.netMask.get().rawValue()))
					return MaybeUtility.just("Card with an all-0s host part of its IP address");

				return nothing();
			}
		},NonsensicalArrangement.<Card>noErrors(),USUAL).run(network);
	}
        }*/