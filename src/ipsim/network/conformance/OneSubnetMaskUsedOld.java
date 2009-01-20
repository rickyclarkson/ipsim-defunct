package ipsim.network.conformance;
/*
import fpeas.function.*;
import ipsim.*;
import ipsim.network.*;
import static ipsim.network.conformance.CheckResultUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.ethernet.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;

class OneSubnetMaskUsed extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Function<NetworkContext, CheckResult> func=new Function<NetworkContext, CheckResult>()
		{
			@NotNull
			public CheckResult run(@NotNull final NetworkContext context)
			{
				final Problem problem=context.problem.value;

				if (problem==null)
					return fine();

				final int rawProblemNumber=problem.netBlock().networkNumber().rawValue();

				final int problemPrefix=problem.netBlock().netMask().prefixLength();

				for (final CardDrivers card : NetworkUtility.getAllCardsWithDrivers(network))
				{
                                    final int rawNetworkNumber=card.ipAddress.get().rawValue()&card.netMask.get().rawValue();

                                    final int cardPrefix=card.netMask.get().prefixLength();

					if (cardPrefix==-1)
						continue;

					final Stream<PacketSource> empty=new Stream<PacketSource>();
					if (!(cardPrefix==problemPrefix) && rawNetworkNumber-rawProblemNumber==(1<<cardPrefix-problemPrefix)-1)
						return new CheckResult(USUAL, one("A subnet that uses an all-1s subnet number"), empty);
				}

				return fine();
			}
		};

		return CheckProblemUtility.check(network, func);
	}
        }*/