package ipsim.persistence;

import org.w3c.dom.*;
import org.jetbrains.annotations.*;

public interface SerialisationDelegate<Write,Read>
{
	void writeXML(XMLSerialiser serialiser,Write object);

	Read readXML(XMLDeserialiser deserialiser,Node node,Read object);

	Read construct();

	@NotNull
	String getIdentifier();
}