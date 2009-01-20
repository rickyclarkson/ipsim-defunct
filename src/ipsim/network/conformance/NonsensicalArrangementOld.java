package ipsim.network.conformance;
/*
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import fpeas.maybe.*;
import fpeas.sideeffect.*;
import ipsim.lang.*;
import static ipsim.lang.FunctionOOO.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.network.ip.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

class NonsensicalArrangement
{
	static <T> Function<T, Maybe<String>> noErrors()
	{
		return constant(MaybeUtility.<String>nothing());
	}

	public static Function<Network, CheckResult> computerWithoutNetworkCard()
	{
		final FunctionOOO<Network, Computer, Maybe<String>> hasCard=new FunctionOOO<Network, Computer, Maybe<String>>()
		{
			@Override
			@NotNull
			public Maybe<String> invoke(Network network, @NotNull final Computer computer)
			{
				return computer.getCards(network).isEmpty() ? MaybeUtility.just("A computer without a network card") : MaybeUtility.<String>nothing();
			}
		};

		final Function<Computer, Maybe<String>> noErrors=noErrors();
		return customCheck(getAllComputers, hasCard, noErrors, USUAL);
	}

	public static Function<Network, CheckResult> cardWithoutCable()
	{
		final FunctionOOO<Network, Card, Maybe<String>> hasCable=new FunctionOOO<Network, Card, Maybe<String>>()
		{
			@Override
			@NotNull
			public Maybe<String> invoke(Network network, @NotNull final Card card)
			{
				return card.getCable(network)!=null ? MaybeUtility.<String>nothing() : MaybeUtility.just("Card without a cable");
			}
		};

		final Function<Card, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllCards, hasCable, noErrors, USUAL);
	}

	public static <T extends PacketSource> Function<Network, CheckResult> customCheck(final Function<Network, Stream<T>> getCollection, final FunctionOOO<Network, T, Maybe<String>> warning, final Function<T, Maybe<String>> error, final int deductionsIfFound)
	{
		return new Function<Network, CheckResult>()
		{
			@NotNull
			public CheckResult run(@NotNull final Network network)
			{
				final List<PacketSource> withWarnings=arrayList(), withErrors=arrayList();

				final List<String> warningSummary=arrayList(), errorSummary=arrayList();

				getCollection.run(network).foreach(new SideEffect<T>()
				{
					public void run(final T t)
					{
						MaybeUtility.run(warning.invoke(network, t), new SideEffect<String>()
						{
							public void run(final String s)
							{
								withWarnings.add(t);
								warningSummary.add(s);
							}
						});

						MaybeUtility.run(error.run(t), new SideEffect<String>()
						{
							public void run(final String s)
							{
								withErrors.add(t);
								errorSummary.add(s);
							}
						});
					}
				});

				final boolean found=!(0==withWarnings.size()+withErrors.size());

				final int deductions=found ? deductionsIfFound : NONE;

				warningSummary.addAll(errorSummary);
				return new CheckResult(deductions, Stream.fromIterable(warningSummary), Stream.fromIterable(withWarnings));
			}
		};
	}

	public static Function<Network, CheckResult> cardWithZeroIP()
	{
		final FunctionOOO<Network, Card, Maybe<String>> zeroIPTest=ignoreFirst(new Function<Card, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Card card)
			{
				final CardDrivers cardWithDrivers=card.withDrivers;

				if (cardWithDrivers==null)
					return MaybeUtility.nothing();

				return IPAddressUtility.zero().equals(cardWithDrivers.ipAddress.get()) ? MaybeUtility.just("A card with a 0.0.0.0 IP address") : MaybeUtility.<String>nothing();
			}
		});

		final Function<Card, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllCards, zeroIPTest, noErrors, USUAL);
	}

	public static Function<Network, CheckResult> cardWithoutDeviceDrivers()
	{
		final FunctionOOO<Network, Card, Maybe<String>> deviceDriverTest=ignoreFirst(new Function<Card, Maybe<String>>()
		{
			@NotNull
			public Maybe<String> run(@NotNull final Card card)
			{
				return card.withDrivers!=null ? MaybeUtility.<String>nothing() : MaybeUtility.just("Card with no device drivers");
			}
		});

		final Function<Card, Maybe<String>> noErrors=noErrors();
		return customCheck(getAllCards, deviceDriverTest, noErrors, USUAL);
	}

	public static Function<Network, CheckResult> cableWithEndsDisconnected()
	{
		return new Function<Network, CheckResult>()
		{
			@NotNull
			public CheckResult run(@NotNull final Network network)
			{
				final FunctionOOO<Network, Cable, Maybe<String>> testCableEnds=ignoreFirst(new Function<Cable, Maybe<String>>()
				{
					@NotNull
					public Maybe<String> run(@NotNull final Cable cable)
					{
						return cable.getEnds().size()==2 ? MaybeUtility.<String>nothing() : MaybeUtility.just("A cable that has not got both ends connected to components");
					}
				});

				final Function<Cable, Maybe<String>> noErrors=noErrors();

				return customCheck(getAllCables(), testCableEnds, noErrors, USUAL).run(network);
			}
		};
	}

	public static Function<Network, CheckResult> hubWithNoCables()
	{
		final FunctionOOO<Network, Hub, Maybe<String>> testHub=new FunctionOOO<Network, Hub, Maybe<String>>()
		{
			@Override
			@NotNull
			public Maybe<String> invoke(Network network, @NotNull final Hub hub)
			{
				return hub.getCables(network).isEmpty() ? MaybeUtility.just("A hub that has no cables") : MaybeUtility.<String>nothing();
			}
		};

		final Function<Hub, Maybe<String>> noErrors=noErrors();

		return customCheck(getAllHubs, testHub, noErrors, USUAL);
	}
}*/