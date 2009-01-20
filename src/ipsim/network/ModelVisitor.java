package ipsim.network;

import ipsim.network.connectivity.hub.*;

public interface ModelVisitor
{
	void visit(Hub hub);
}