package ipsim.persistence;

import org.w3c.dom.*;

public interface SerialisationDelegate<Write,Read>
{
	void writeXML(XMLSerialiser serialiser,Write object);

	Read readXML(XMLDeserialiser deserialiser,Node node,Read object);

	Read construct();

	String getIdentifier();
}