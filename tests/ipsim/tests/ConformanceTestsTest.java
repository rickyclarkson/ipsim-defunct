package ipsim.tests;

import ipsim.network.Network;
import ipsim.network.conformance.ConformanceTests;
import ipsim.network.conformance.ConformanceTests.ResultsAndSummaryAndPercent;
import static ipsim.util.Collections.linkedHashMap;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import fpeas.lazy.Lazy;

public class ConformanceTestsTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Map<String, Integer> all=allTogether();

		boolean passed=true;

		for (final Entry<String, Integer> entry : all.entrySet())
		{
			final Network network=new Network();

			final ResultsAndSummaryAndPercent conformanceTest=conformanceTest(network, entry.getKey());
			final int result=conformanceTest.percent();

			if (!entry.getValue().equals(result))
			{
				System.out.println(entry.getKey()+" gives "+result+" instead of "+entry.getValue());
				System.out.println(conformanceTest.summary());
				passed=false;
			}
		}

		return passed;
	}

	private static Map<String, Integer> allTogether()
	{
		Map<String, Integer> fullyConnected=linkedHashMap();

		for (final String name : new String[]{"1.63","1.64"})
			fullyConnected.put(name, 12);

		for (final String name : new String[]{"broadcast1"})
			fullyConnected.put(name, 23);

		for (final String name : new String[]{"1.61"})
			fullyConnected.put(name, 39);

		for (final String name : new String[]{"101","arpforeign2"})
			fullyConnected.put(name, 50);

		for (final String name : new String[]{"1.5","1.6","1.7"})
			fullyConnected.put(name, 59);

		for (final String name : new String[]{"routingloop1","6"})
			fullyConnected.put(name, 81);

		for (final String name : new String[]{"arpforeign","1.4","1.52","1.58","5","1","arpfromip","simplest"})
			fullyConnected.put(name, 90);

		for (final String name : new String[]{"1-2-1","1-2","1.15","1.17","3","1.62","4"})
			fullyConnected.put(name, 100);

		final Map<String, Integer> unconnected=linkedHashMap();

		for (final String name1 : new String[]{"1.57","1.59"})
			unconnected.put(name1, 0);

		for (final String name12 : new String[]{"1.43","1.48","1.44"})
			unconnected.put(name12,12);

		for (final String name13 : new String[]{"1.26","1.27","1.50"})
			unconnected.put(name13,13);

		for (final String name14 : new String[]{"1.56","1.38","1.52","1.39","1.41"})
			unconnected.put(name14,15);

                unconnected.put("1.60",16);
		for (final String name15 : new String[]{"1.34","1.51","101.2"})
			unconnected.put(name15,18);

		for (final String name16 : new String[]{"1.10","1.32","1.14"})
			unconnected.put(name16,20);

		for (final String name17 : new String[]{"1.33"})
			unconnected.put(name17,30);

                unconnected.put("1.55",32);
		for (final String name18 : new String[]{"1.24","1.40","1.42","1.16"})
			unconnected.put(name18,33);

		unconnected.put("1.35", 34);

		for (final String name19 : new String[]{"1.53","1.54"})
			unconnected.put(name19,36);

		unconnected.put("1.25", 41);

		for (final String name110 : new String[]{"arpitself","1.8"})
			unconnected.put(name110,45);

		unconnected.put("1.18", 48);

                unconnected.put("1.36",55);

		for (final String name111 : new String[]{"1","1.28","pingertest1","traceroute1"})
			unconnected.put(name111,59);

		for (final String name112 : new String[]{"1.12","1.47","1.46"})
			unconnected.put(name112,66);

		for (final String name113 : new String[]{"1.20","1.22","1.49"})
			unconnected.put(name113,73);

		for (final String name114 : new String[]{"1.9","broadcast","broadcastaddress"})
			unconnected.put(name114,81);

		for (final String name115 : new String[]{"1.13","1.19","hubdisabled"})
			unconnected.put(name115,90);

		for (final String name116 : new String[]{"1.21","1.23"})
			unconnected.put(name116,100);

		final Map<String, Integer> all=linkedHashMap();

		for (final Entry<String, Integer> entry : unconnected.entrySet())
			all.put("datafiles/unconnected/"+entry.getKey()+".ipsim", entry.getValue());

		for (final Entry<String, Integer> entry : fullyConnected.entrySet())
			all.put("datafiles/fullyconnected/"+entry.getKey()+".ipsim", entry.getValue());

		return all;
	}

	private static ResultsAndSummaryAndPercent conformanceTest(final Network network, final String filename)
	{
            network.loadFromFile(new File(filename));

            return new ConformanceTests(network).allChecks();
	}

	@Override
	public String toString()
	{
		return "ConformanceTestsTest";
	}

	public static void main(String[] args)
	{
		for (String filename: allTogether().keySet())
		{
			Network network=new Network();
			System.out.println(filename);
			System.out.println(conformanceTest(network,filename).summary());
			System.out.println();
		}
	}
}
