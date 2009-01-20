package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.PacketSourceUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.hub.*;
import static ipsim.network.ethernet.CableUtility.*;
import org.jetbrains.annotations.*;

class SomeHubsHaveNoStandaloneHost extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network,Hub, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Hub, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Hub hub)
			{
				boolean foundOne=false;

				for (final Cable cable: hub.getCables(network))
				{
					final PacketSource otherEnd=getOtherEnd(cable, hub);

					if (otherEnd==null)
						continue;

					@Nullable final Card card=asCard(otherEnd);

					if (card==null)
						continue;

					@Nullable final PacketSource computer=card.positionData(0).parent;

					if (computer!=null && 1==PositionUtility.children(network.getAll(),computer).size())
						foundOne=true;
				}

				return foundOne ? MaybeUtility.<String>nothing() : MaybeUtility.just("Hub with no standalone (non-gateway) computer");
			}
		});

		final Function<Hub, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllHubs,warning,noErrors,USUAL).run(network);
	}
        }*/