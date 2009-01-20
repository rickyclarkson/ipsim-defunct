package ipsim.network.connectivity;

import ipsim.lang.*;

import java.util.*;

public interface ConnectivityResults extends Stringable
{
	int getPercentConnected();

	Collection<String> getOutputs();
}