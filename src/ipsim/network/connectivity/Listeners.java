package ipsim.network.connectivity;

import fpeas.sideeffect.*;
import ipsim.util.Collections;

import java.util.*;

public class Listeners<T>
{
	private final List<T> wrapped=Collections.arrayList();

	public void add(final T t)
	{
		wrapped.add(t);
	}

	public void remove(final T listener)
	{
		wrapped.remove(listener);
	}

	public void visitAll(final SideEffect<T> sideEffect)
	{
		for (final T t: wrapped)
			sideEffect.run(t);
	}

	public boolean contains(final T listener)
	{
		return wrapped.contains(listener);
	}
}