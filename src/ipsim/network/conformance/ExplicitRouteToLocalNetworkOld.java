package ipsim.network.conformance;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import fpeas.predicate.*;
import ipsim.lang.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.NonsensicalArrangement.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import ipsim.network.ethernet.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

class ExplicitRouteToLocalNetwork extends Function<Network, CheckResult>
{
	@NotNull
	public CheckResult run(@NotNull final Network network)
	{
		final FunctionOOO<Network, Computer, Maybe<String>> warning=FunctionOOO.ignoreFirst(new Function<Computer, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Computer computer)
			{
				return Collections.any(computer.getCards(network),new Predicate<Card>()
				{
					public boolean invoke(final Card card)
					{
						for (final Route route: getExplicitRoutes(computer.routingTable))
							if (route.block.equals(CardUtility.getNetBlock(card)))
								return true;

						return false;
					}

				}) ? MaybeUtility.just("Computer with an explicit route that points to one of its local networks") : MaybeUtility.<String>nothing();
			}
		});

		final Function<Computer, Maybe<String>> noErrors=noErrors();

		return NonsensicalArrangement.customCheck(getAllComputers,warning,noErrors,USUAL).run(network);
	}
        }*/