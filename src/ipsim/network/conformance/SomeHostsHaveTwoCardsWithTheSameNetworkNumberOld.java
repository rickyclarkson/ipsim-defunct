package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.util.*;

class SomeHostsHaveTwoCardsWithTheSameNetworkNumber extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		return NonsensicalArrangement.customCheck(getAllComputers,new FunctionOOO<Network, Computer, Maybe<String>>()
		{
			@Override
			@NotNull
			public Maybe<String> invoke(Network notwork,@NotNull final Computer computer)
			{
				final Collection<IPAddress> netNumbers=hashSet();

				for (final CardDrivers card: ComputerUtility.cardsWithDrivers(network,computer))
				{
                                    final IPAddress netNum=CardUtility.getNetBlock(card).networkNumber();

					if (netNumbers.contains(netNum))
						return MaybeUtility.just("Computer that has multiple cards with the same subnet number");

					netNumbers.add(netNum);
				}

				return MaybeUtility.nothing();
			}
		},NonsensicalArrangement.<Computer>noErrors(),USUAL).run(network);
	}
        }*/