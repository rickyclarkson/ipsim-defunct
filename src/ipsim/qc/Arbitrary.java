package ipsim.qc;

import ipsim.util.*;

import java.util.*;

public interface Arbitrary<T>
{
	Stream<T> data(Random random);
}