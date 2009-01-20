package ipsim.network.ethernet;

import fpeas.either.*;
import static fpeas.either.EitherUtility.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ip.*;
import ipsim.network.IPAddress;

final class NetBlockUtilityOld
{ /*
	public static NetBlock getZero()
	{
		return new NetBlock(IPAddressUtility.zero, NetMaskUtility.zero);
	}

	public static String asStringContainingSlash(final NetBlock netBlock)
	{
		return netBlock.networkNumber.toString()+'/'+NetMaskUtility.getPrefixLength(netBlock.netMask);
	}

	public static NetBlock createNetBlockOrThrowRuntimeException(final String net)
	{
		return unsafeLeft(createNetBlock(net));
	}

	public static final class ParseFailure
	{
		private final String reason;

		public ParseFailure(final String reason)
		{
			this.reason=reason;
		}

		@Override
		public String toString()
		{
			return reason;
		}
	}

	public static Either<NetBlock,ParseFailure> createNetBlock(final String string)
	{
		final String regexp="([0-9]{1,3}\\.){3}[0-9]{1,3}/[0-9]{1,2}";

		if (!string.matches(regexp))
			return right(new ParseFailure("Regex match failed on "+string));

		final String[] parts=string.split("/");

		final IPAddress networkNumber=IPAddressUtility.valueOfUnchecked(parts[0]);

		final int tempMask=Integer.parseInt(parts[1]);

		if (tempMask>32)
			return right(new ParseFailure("Netmask bigger than 32"));

		final NetMask netMask=NetMaskUtility.createNetMaskFromPrefixLength(tempMask);

		final int y=networkNumber.rawValue&~netMask.rawValue;
		if (!(0==y))
			return right(new ParseFailure("Host part not blank in "+string));

		return left(new NetBlock(networkNumber, netMask));
	}

	public static IPAddress getBroadcastAddress(final NetBlock block)
	{
		final int rawValue=~block.netMask.rawValue|block.networkNumber.rawValue;
		return new IPAddress(rawValue);
	}

	public static String asCustomString(final NetBlock block)
	{
		final int length=NetMaskUtility.getPrefixLength(block.netMask);

		return IPAddressUtility.toString(block.networkNumber.rawValue)+'/'+(length==-1 ? NetMask.asString(block.netMask.rawValue) : length);
                } */
}