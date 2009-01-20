package ipsim.network.conformance;
/*
import fpeas.function.*;
import ipsim.*;
import ipsim.network.*;
import static ipsim.network.conformance.CheckResultUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;

class PercentOfRequiredSubnets extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Function<NetworkContext,CheckResult> func=new Function<NetworkContext,CheckResult>()
		{
			@NotNull
			public CheckResult run(@NotNull final NetworkContext context)
			{
				@Nullable
				final Problem problem=context.problem.value;

				if (problem==null)
					return fine();

				final int ideal=problem.numberOfSubnets();
				final int actual=NetworkUtility.getNumberOfSubnets(network);

				final Stream<PacketSource> empty=new Stream<PacketSource>();

				if (actual>ideal)
					return new CheckResult(USUAL, one("More subnets in the solution than are in the problem"), empty);

				final int percent;
				if (actual==ideal)
					percent=100;
				else
					percent=100*(ideal-(ideal-actual))/ideal;

				if (!(100==percent))
					return new CheckResult(percent, one("Less subnets in the solution than the problem requires"), empty);

				return new CheckResult(NONE, one("The correct number of subnets for the problem"), empty);
			}
		};

		return CheckProblemUtility.check(network,func);
	}
        }*/