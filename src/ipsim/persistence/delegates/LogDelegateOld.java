package ipsim.persistence.delegates;
/*
import ipsim.*;
import static ipsim.Caster.*;
import ipsim.network.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import static ipsim.persistence.delegates.DefaultCommandDelegate.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class LogDelegate
{
	public static SerialisationDelegate<Network.Log,Network.Log> logDelegate(final Network network)
	{
		return new SerialisationDelegate<Network.Log,Network.Log>()
		{
			public void writeXML(final XMLSerialiser serialiser, final Network.Log log)
			{
				int a=0;

				for (final String entry : log.entries)
				{
					serialiser.writeObject(new DefaultCommand(entry), "entry "+a, defaultCommandDelegate);
					a++;
				}
			}

			public Network.Log readXML(final XMLDeserialiser deserialiser, final Node node, Network.Log log)
			{
				log.clear();

				for (final String name : getObjectNames(node))
					if (name.startsWith("entry "))
					{
						final String element=Caster.asNotNull(deserialiser.readObject(node, name, DefaultCommandDelegate.defaultCommandDelegate, asFunction(DefaultCommand.class))).value;
						log.entries.append(element);
					}

				return log;
			}

			public Network.Log construct()
			{
				return network.log;
			}

			@NotNull
			public String getIdentifier()
			{
				return "ipsim.persistence.delegates.LogDelegate";
			}
		};
	}
        }*/