package ipsim.network.connectivity.computer;
/*
import fpeas.function.*;
import static ipsim.lang.Objects.*;
import ipsim.network.connectivity.ethernet.*;
import org.jetbrains.annotations.*;
import ipsim.network.*;

import static java.lang.System.*;

public final class ArpEntry
{
	@Nullable
	public final MacAddress macAddress;
	public final long timeToDie;
	public static final Function<ArpEntry, String> asString=toStringRef();

	@Override
	public String toString()
	{
		final String messagePart=dead() ? "expired" : "expires in "+timeToLive()+" seconds";

		return macAddress==null ? "incomplete ARP entry - "+messagePart : new Function<MacAddress, String>()
		{
			@NotNull
			public String run(@NotNull final MacAddress macAddress)
			{
				final StringBuffer buffer=new StringBuffer(Integer.toHexString(macAddress.rawValue));
				while (buffer.length()<12)
					buffer.insert(0, '0');

				for (int a=2;a<buffer.length();a+=3)
					buffer.insert(a, '-');

				return buffer+"; "+messagePart;
			}
		}.run(macAddress);
	}

	public ArpEntry(@Nullable final MacAddress macAddress, final int timeToLive)
	{
		this.macAddress=macAddress;
		timeToDie=currentTimeMillis()+1000L*timeToLive;
	}

	public boolean dead()
	{
		return System.currentTimeMillis()>timeToDie;
	}

	public long timeToLive()
	{
		return (timeToDie-currentTimeMillis())/1000;
	}
        }*/