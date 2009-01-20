package ipsim.network;

public interface ModelObject
{
	void accept(ModelVisitor visitor);
}