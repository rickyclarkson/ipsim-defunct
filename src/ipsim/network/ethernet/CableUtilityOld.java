package ipsim.network.ethernet;
/*
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import org.jetbrains.annotations.*;

public final class CableUtility
{
	@Nullable
	public static PacketSource getOtherEnd(final Cable cable, final PacketSource source)
	{
		@Nullable
		final PacketSource parent0=cable.positionData(0).parent;

		@Nullable
		final PacketSource parent1=cable.positionData(1).parent;

		if (parent0!=null && parent1!=null)
		{
			if (!source.equals(parent0) && !source.equals(parent1))
				throw new IllegalArgumentException(source+" is not attached to "+cable);

			return parent0.equals(source) ? parent1 : parent0;
		}

		return null;
	}
        }*/