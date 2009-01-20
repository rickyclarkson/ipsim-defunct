package ipsim.network.ethernet;
/*
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.ip.*;
import org.jetbrains.annotations.*;
import ipsim.network.IPAddress;
import ipsim.network.NetBlock;
import ipsim.network.*;

public final class CardUtility
{
	public static IPAddress getBroadcastAddress(final CardDrivers card)
	{
            return getNetBlock(card).broadcastAddress();
	}

	public static boolean isOnSameSubnet(@NotNull final CardDrivers card,@NotNull final IPAddress sourceIPAddress)
	{
		return getNetBlock(card).networkContains(sourceIPAddress);
	}

	@Nullable
	public static NetBlock getNetBlock(final Card card)
	{
		if (card.withDrivers==null)
			return null;

		final CardDrivers cardWithDrivers=card.withDrivers;

		int rawIP=cardWithDrivers.ipAddress.get().rawValue();
		final NetMask netMask=cardWithDrivers.netMask.get();

		rawIP&=netMask.rawValue();

		return new NetBlock(new IPAddress(rawIP), netMask);
	}

	public static NetBlock getNetBlock(final CardDrivers cardWithDrivers)
	{
		final NetMask netMask=cardWithDrivers.netMask.get();
		return new NetBlock(new IPAddress(cardWithDrivers.ipAddress.get().rawValue() & netMask.rawValue()), netMask);
	}
        }*/