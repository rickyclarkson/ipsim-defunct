package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import ipsim.network.*;
import static ipsim.network.conformance.CheckResultUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.ethernet.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;

import java.util.*;

class PercentOfIPsMatchingProblem extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Function<Maybe<Problem>,CheckResult> func=new Function<Maybe<Problem>,CheckResult>()
		{
			@NotNull
			public CheckResult run(@NotNull final Maybe<Problem> maybeProblem)
			{
				if (!MaybeUtility.isJust(maybeProblem))
					return fine();

				Problem problem=asJust(maybeProblem);

				final NetBlock block=new NetBlock(problem.netBlock().networkNumber(), problem.netBlock().netMask());

				final Stream<CardDrivers> cards=NetworkUtility.getAllCardsWithDrivers(network);

				int totalCorrect=0;
				int total=0;

				final List<PacketSource> withWarnings=arrayList();

				for (@NotNull final CardDrivers card: cards)
				{
                                    if (card.ipAddress.get().rawValue()==0)
						continue;

					if (block.networkContains(card.ipAddress.get()))
						totalCorrect++;
					else
						withWarnings.add(card.card);

					total++;
				}

				final int percent=0==total ? 0 : totalCorrect*100/total;

				return new CheckResult(percent, one("IP address that doesn't match the problem given"),Stream.fromIterable(withWarnings));
			}
		};

		return pessimisticMerge(map(network.getProblems(),func));
	}
        }*/