package ipsim.network.connectivity.computer;
/*
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.connectivity.ip.*;
import ipsim.util.Collections;
import org.jetbrains.annotations.*;
import fpeas.function.Function;

import java.util.*;

public class ArpTable
{
	private final Map<IPAddress, ArpEntry> map=Collections.hashMap();

	public void put(final IPAddress ipAddress, final MacAddress macAddress, final Network network)
	{
		map.put(ipAddress, new ArpEntry(macAddress, network.arpCacheTimeout));
	}

	@Nullable
	public MacAddress getMacAddress(final IPAddress gatewayIP)
	{
		final ArpEntry result=map.get(gatewayIP);

		return result==null || result.dead() ? null : result.macAddress;
	}

	public String asString()
	{
            return Collections.asString(map.entrySet(), new Function<IPAddress,String>(){public String run(IPAddress address){return address.toString();}}, ArpEntry.asString, ": ", "\n");
	}

	public void clear()
	{
		map.clear();
	}

	public void putIncomplete(final IPAddress sourceIPAddress, final Network network)
	{
		map.put(sourceIPAddress, new ArpEntry(null, network.arpCacheTimeout));
	}

	public boolean hasEntryFor(final IPAddress ipAddress)
	{
		return map.containsKey(ipAddress) && !map.get(ipAddress).dead();
	}
        }*/