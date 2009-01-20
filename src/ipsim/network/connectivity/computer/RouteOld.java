package ipsim.network.connectivity.computer;
/*
import ipsim.lang.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.network.IPAddress;
import ipsim.network.NetBlock;

public final class Route implements Stringable
{
	public final NetBlock block;
	public final IPAddress gateway;

	public Route(final NetBlock block, final IPAddress gateway)
	{
		this.block=block;
		this.gateway=gateway;
	}

	@Override
	public String toString()
	{
            final boolean bool=0==block.networkNumber().rawValue() || 0==block.netMask().rawValue();
		final String string=bool ? "default" : block.toString();

		return "Destination: "+string+" Gateway: "+(0==gateway.rawValue() ? "default" : gateway.toString());
	}
        }*/