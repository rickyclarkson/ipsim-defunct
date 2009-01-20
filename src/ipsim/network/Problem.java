package ipsim.network;

import ipsim.network.ethernet.*;
import ipsim.network.ip.*;

import java.util.*;

public class Problem
{
	public final NetBlock netBlock;

	public final int numberOfSubnets;
	public static final Problem NO_PROBLEM=null;

	public Problem(final NetBlock startingNetBlock, final int startingNumberOfSubnets)
	{
		netBlock=startingNetBlock;
		numberOfSubnets=startingNumberOfSubnets;
	}

	@Override
	public String toString()
	{
		final int length=NetMaskUtility.getPrefixLength(netBlock.netMask);

		if (length==-1)
			throw null;

		return "Network number: "+IPAddressUtility.toString(netBlock.networkNumber.rawValue)+'/'+length+" Number of subnets: "+numberOfSubnets;
	}

	public static final int MIN_SUBNETS=2, MAX_SUBNETS=32;

	public static Problem arbitraryProblem(Random random)
	{
		return new Problem(NetBlock.arbitraryNetBlock(random), random.nextInt(MAX_SUBNETS-MIN_SUBNETS+1)+MIN_SUBNETS);
	}
}