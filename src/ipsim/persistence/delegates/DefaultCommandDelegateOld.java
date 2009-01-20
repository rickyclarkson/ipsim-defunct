package ipsim.persistence.delegates;
/*
import static ipsim.Caster.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

public final class DefaultCommandDelegate
{
	public static final class DefaultCommand
	{
		public String value;

		public DefaultCommand(String value)
		{
			this.value=value;
		}
	}

	public static final SerialisationDelegate<DefaultCommand,DefaultCommand> defaultCommandDelegate=new SerialisationDelegate<DefaultCommand,DefaultCommand>()
	{
		public void writeXML(final XMLSerialiser serialiser, final DefaultCommand object)
		{
			serialiser.writeAttribute("value", object.value);
		}

		@NotNull
		public DefaultCommand readXML(final XMLDeserialiser deserialiser, final Node node, final DefaultCommand command)
		{
			@Nullable
			String oldVersion=readAttribute(node,"description");

			command.value=oldVersion==null ? asNotNull(readAttribute(node, "value")) : oldVersion;
			return command;
		}

		public DefaultCommand construct()
		{
			return new DefaultCommand("");
		}

		@NotNull
		public String getIdentifier()
		{
			return "ipsim.persistence.delegates.DefaultCommandDelegate";
		}
	};
        }*/