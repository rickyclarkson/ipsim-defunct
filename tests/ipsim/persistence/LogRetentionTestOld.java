package ipsim.persistence;
/*
import com.rickyclarkson.testsuite.*;
import ipsim.network.*;
import fpeas.lazy.Lazy;

public class LogRetentionTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		Network network=new Network();
		network.log.add("Sample Data");
		final String xml=NetworkUtility.saveToString(network);
		network=new Network(null);

		NetworkUtility.loadFromString(network, xml);

		return "Sample Data".equals(network.log.entries.get().get(0));
	}

	@Override
	public String toString()
	{
		return "Log retention test";
	}
}
*/