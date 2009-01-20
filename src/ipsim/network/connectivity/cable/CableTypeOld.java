package ipsim.network.connectivity.cable;
/*
import static ipsim.lang.Randoms.*;
import ipsim.lang.*;
import static ipsim.network.PacketSourceUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.PacketSource;

import java.util.*;

public enum CableType implements Stringable
{
	CROSSOVER
	{
		@Override
		public String toString()
		{
			return "Crossover";
		}

		@Override
		public CableType another(final Random random)
		{
			return randomOneOf(random,BROKEN, STRAIGHT_THROUGH);
		}

		@Override
		public boolean canTransferPackets(final PacketSource oneEnd, final PacketSource other)
		{
			return isSpecialCard(oneEnd) || isSpecialCard(other) || isHub.invoke(oneEnd) && isHub.invoke(other) || isCard(oneEnd) && isCard(other);
		}
	},BROKEN
	{
		@Override
		public boolean canTransferPackets(final PacketSource oneEnd, final PacketSource other)
		{
			return false;
		}

		@Override
		public String toString()
		{
			return "Broken";
		}

		@Override
		public CableType another(final Random random)
		{
			return randomOneOf(random,CROSSOVER, STRAIGHT_THROUGH);
		}
	},STRAIGHT_THROUGH
	{
		@Override
		public String toString()
		{
			return "Straight through";
		}

		@Override
		public CableType another(Random random)
		{
			return randomOneOf(random,CROSSOVER, BROKEN);
		}

		@Override
		public boolean canTransferPackets(final PacketSource oneEnd, final PacketSource other)
		{
			return isSpecialCard(oneEnd) || isSpecialCard(other) || isCard(oneEnd) && isHub.invoke(other) || isCard(other) && isHub.invoke(oneEnd);
		}
	};

	public abstract CableType another(Random random);

	public abstract boolean canTransferPackets(final PacketSource oneEnd, final PacketSource other);
        }*/