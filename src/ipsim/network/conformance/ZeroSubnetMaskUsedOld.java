package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.*;
import org.jetbrains.annotations.*;

class ZeroSubnetMaskUsed extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Function<NetworkContext,CheckResult> func=new Function<NetworkContext,CheckResult>()
		{
			@NotNull
			public CheckResult run(@NotNull final NetworkContext context)
			{
				final Problem problem=context.problem.value;

				if (problem==null)
					return CheckResultUtility.fine();

				final int rawProblemNumber=problem.netBlock().networkNumber().rawValue();

				final int problemMaskPrefix=problem.netBlock().netMask().prefixLength();

				if (problemMaskPrefix==-1)
                                    throw new RuntimeException("A problem has an invalid netmask, should be impossible - netmask is "+problem.netBlock().netMask());

				return NonsensicalArrangement.customCheck(NetworkUtility.getAllCards,new FunctionOOO<Network,Card, Maybe<String>>()
				{
					@Override
					@NotNull
					public Maybe<String> invoke(Network notwork,@NotNull final Card card)
					{
						final CardDrivers cardWithDrivers=card.withDrivers;

						if (cardWithDrivers==null)
							return MaybeUtility.nothing();

						final int rawNetworkNumber=cardWithDrivers.ipAddress.get().rawValue()&cardWithDrivers.netMask.get().rawValue();
						final int cardNetMaskPrefix=cardWithDrivers.netMask.get().prefixLength();

						if (cardNetMaskPrefix==-1)
							return MaybeUtility.nothing();

						final boolean equalNumbers=rawNetworkNumber==rawProblemNumber;

						if (equalNumbers&&problemMaskPrefix<cardNetMaskPrefix)
							return MaybeUtility.just("A subnet that uses an all-0s subnet number");

						if (problemMaskPrefix>=cardNetMaskPrefix)
							return MaybeUtility.just("A subnet mask that is equal to or shorter than the problem's netmask");

						if (equalNumbers)
							return MaybeUtility.just("No subnetting attempted");

						return MaybeUtility.nothing();
					}
				},NonsensicalArrangement.<Card>noErrors(),USUAL).run(network);
			}
		};

		return CheckProblemUtility.check(network,func);
	}
        }*/