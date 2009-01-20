package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.ConformanceTestsUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.computer.*;
import org.jetbrains.annotations.*;

final class PacketForwardingEnabledOnNonGateway extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final Function<Computer, Maybe<String>> noErrors=noErrors();

		final FunctionOOO<Network,Computer,Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				return !isARouter().invoke(network,computer)&& computer.ipForwardingEnabled ? MaybeUtility.just("Computer that is not a gateway but has packet forwarding enabled") : MaybeUtility.<String>nothing();
			}
		});

		return NonsensicalArrangement.customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/