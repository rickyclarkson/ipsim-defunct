package ipsim.network.connectivity.card;

/*import fpeas.function.*;
import ipsim.network.IPAddress;
import ipsim.network.NetMask;
import ipsim.property.*;
import static ipsim.property.PropertyUtility.*;
import org.jetbrains.annotations.*;

public final class CardDrivers
{
	public int ethNumber;
	public final Property<IPAddress> ipAddress;
	public final Card card;
	public final Property<NetMask> netMask;

	public static Function<CardDrivers, Integer> ethNumberRef=new Function<CardDrivers, Integer>()
	{
		@NotNull
		public Integer run(@NotNull final CardDrivers cardDrivers)
		{
			return cardDrivers.ethNumber;
		}
	};

	public CardDrivers(final IPAddress ipAddress, final NetMask netMask, final int ethNumber, final Card card)
	{
		this.ipAddress=newProperty(ipAddress);

		this.netMask=PropertyUtility.newProperty(netMask);

		this.ethNumber=ethNumber;
		this.card=card;
	}
        }*/