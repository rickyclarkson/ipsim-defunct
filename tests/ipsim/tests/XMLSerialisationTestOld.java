package ipsim.tests;
/*
import com.rickyclarkson.testsuite.*;
import fpeas.predicate.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.persistence.*;
import static ipsim.util.Collections.*;
import fpeas.lazy.Lazy;
import java.io.*;

public class XMLSerialisationTest implements Lazy<Boolean>
{
	public Boolean invoke()
	{
		final Network network=new Network();
		network.loadFromFile(new File("datafiles/unconnected/1.14.ipsim"));
		final String saved=network.saveToString();
		return saved.length()!=0;
	}

	@Override
	public String toString()
	{
		return "XMLSerialisationTest";
	}

	public static Lazy<Boolean> testComputerIPForwarding=new Lazy<Boolean>()
	{
		@Override
		public String toString()
		{
			return "testComputerIPForwarding";
		}

		public Boolean invoke()
		{
			final Network network=new Network();
			final NetworkContext context=network.contexts.append(new NetworkContext());

			final Computer computer=new Computer(PositionOrParent.position(new Point(20, 20)));
			context.visibleComponents.prependIfNotPresent(computer);
			context.visibleComponents.prependIfNotPresent(new Card(false, PositionOrParent.parent(computer)));

			computer.ipForwardingEnabled=true;
			StringWriter writer=new StringWriter();
			XMLSerialiser serialiser=new XMLSerialiser(writer);
			serialiser.writeObject(network, "network", networkDelegate(network));
			
			try
			{
				XMLDeserialiser deserialiser=new XMLDeserialiser(writer.getBuffer().toString());
				deserialiser.readObject(networkDelegate(network), Caster.asFunction(Network.class));

				return any(network.getAll(), new Predicate<PacketSource>()
				{
					public boolean invoke(PacketSource source)
					{
						return source.accept(new PacketSourceVisitor<Boolean>()
						{
							public Boolean visit(final Card card)
							{
								return false;
							}

							public Boolean visit(final Computer computer)
							{
								return computer.ipForwardingEnabled;
							}

							public Boolean visit(final Cable cable)
							{
								return false;
							}

							public Boolean visit(final Hub hub)
							{
								return false;
							}
						});
					}
				});
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	};
}
*/