package ipsim.network.ethernet;
/*
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import static ipsim.network.connectivity.card.Card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.util.*;
import static java.util.Collections.*;

public final class ComputerUtility
{
	/**
	 * Flawed because it only returns one card if there are more than one.
	 * /
	@Nullable
	public static CardDrivers getCardFor(Network network, final Computer computer, final Route route)
	{
		final Stream<CardDrivers> result=cardsWithDrivers(network, computer).only(new Predicate<CardDrivers>()
		{
			public boolean invoke(final CardDrivers card)
			{
				final NetBlock netBlock=CardUtility.getNetBlock(card);

				return !(0==netBlock.networkNumber().rawValue()) && netBlock.networkContains(route.gateway);
			}
		});

		return result.isEmpty() ? null : result.car();
	}

	public static Stream<CardDrivers> getSortedCards(Network network, final Computer computer)
	{
		final List<CardDrivers> list=new ArrayList<CardDrivers>();

		computer.getCards(network).only(hasDrivers).map(withDriversRef).foreach(add(list));
		sort(list, Comparators.fromFunction(CardDrivers.ethNumberRef));

		return Stream.fromIterable(list);
	}

	public static String ipAddressesToString(Network network, final Computer computer)
	{
		final Stream<CardDrivers> set=cardsWithDrivers(network, computer);

		final Collection<Stringable> strings=new HashSet<Stringable>();

		final boolean[] foundOneNonZero={false};

		set.foreach(new SideEffect<CardDrivers>()
		{
			public void run(final CardDrivers card)
			{
				final NetBlock netBlock=CardUtility.getNetBlock(card);
				if (!(0==netBlock.networkNumber().rawValue()) || !(0==netBlock.netMask().rawValue()))
					foundOneNonZero[0]=true;

				final String string=card.ipAddress.get().toString()+'/'+card.netMask.get().asCustomString();

				strings.add(new Stringable()
				{
					@Override
					public String toString()
					{
						return string;
					}
				});
			}
		});


		if (!foundOneNonZero[0])
			return "";

		return asString(strings);
	}

	public static boolean isLocallyReachable(Network network, final Computer computer, final IPAddress ipAddress)
	{
		return !computer.getCards(network).only(new Predicate<Card>()
		{
			public boolean invoke(final Card card)
			{
				@Nullable
				final NetBlock netBlock=CardUtility.getNetBlock(card);

				if (netBlock==null)
					return false;

				return netBlock.networkContains(ipAddress);
			}
		}).isEmpty();
	}

	public static Collection<IPAddress> getIPAddresses(Network network, final Computer computer)
	{
		final Collection<IPAddress> set=new HashSet<IPAddress>();

		cardsWithDrivers(network, computer).foreach(new SideEffect<CardDrivers>()
		{
			public void run(final CardDrivers cardDrivers)
			{
				set.add(cardDrivers.ipAddress.get());
			}
		});

		return set;
	}

	public static CardDrivers getEth(Network network, final Computer computer, final int cardNo)
	{
		return getSortedCards(network, computer).only(new Predicate<CardDrivers>()
		{
			public boolean invoke(final CardDrivers cardDrivers)
			{
				return cardNo==cardDrivers.ethNumber;
			}
		}).car();
	}

	@Nullable
	public static IPAddress getTheFirstIPAddressYouCanFind(Network network, final Computer computer)
	{
		final Stream<CardDrivers> cards=cardsWithDrivers(network, computer);
		return cards.isEmpty() ? null : cards.car().ipAddress.get();
	}

	public static Stream<CardDrivers> cardsWithDrivers(Network network, final Computer computer)
	{
		return computer.getCards(network).only(hasDrivers).map(withDriversRef);
	}
}*/