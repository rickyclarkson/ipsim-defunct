package ipsim.network;
/*
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import static ipsim.network.Problem.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.NetBlock$.*;
import static ipsim.network.NetMask.*;
import ipsim.util.Collections;

import java.util.*;

public final class ProblemUtility
{
	public static Maybe<Problem> createProblem(final NetBlock startingNetBlock,final int startingNumberOfSubnets)
	{
		if (startingNumberOfSubnets<MIN_SUBNETS||startingNumberOfSubnets>MAX_SUBNETS)
			return nothing();

		if (isValidNetworkNumber(startingNetBlock.networkNumber))
		{
			if (!NetMaskUtility.isValid(startingNetBlock.netMask))
				return nothing();
		}
		else
			return nothing();

		final Problem problem=new Problem(startingNetBlock, startingNumberOfSubnets);

		return just(problem);
	}

	private static final List<NetBlock> reservedNetBlocks=Collections.asList(createNetBlockOrThrowRuntimeException("10.0.0.0/8"),createNetBlockOrThrowRuntimeException("192.168.0.0/16"),createNetBlockOrThrowRuntimeException("172.16.0.0/12"));

	public static boolean isReservedNetworkNumber(final NetBlock block)
	{
		for (final NetBlock temp: reservedNetBlocks)
		{
			if (block.networkNumber.bitwiseAnd(block.netMask)==temp.networkNumber.bitwiseAnd(block.netMask))
				return true;

			if (block.networkNumber.bitwiseAnd(temp.netMask)==temp.networkNumber.bitwiseAnd(temp.netMask))
				return true;
		}

		return false;
	}

	public static boolean isValidNetworkNumber(final IPAddress networkNumber)
	{
		final int networkNumberBits=networkNumber.rawValue;
		final int firstByte=networkNumberBits>>>24;

		switch (firstByte)
		{
			case 0:
				return false;

			case 127:
				return false;

			default:
				return firstByte<224;
		}
	}

	public static IPAddress generateNetworkNumber(final Random random,final int prefixLength)
	{
		for (int a=0;a<1000;a++)
		{
			int networkNumber=random.nextInt(Integer.MAX_VALUE);

			networkNumber<<=32-prefixLength; // remove host
			// part

			final IPAddress returnValue=new IPAddress(networkNumber);

			if (!isReservedNetworkNumber(new NetBlock(returnValue, createNetMaskFromPrefixLength(prefixLength))))
				return returnValue;
		}

		throw new RuntimeException("Cannot generate a publically-accessible "+"network number with a prefix length of "+prefixLength);
	}
        }*/