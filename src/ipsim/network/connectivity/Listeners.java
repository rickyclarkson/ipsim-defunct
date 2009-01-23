package ipsim.network.connectivity;

import ipsim.util.Collections;

import java.util.*;

import fj.Effect;

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

	public void visitAll(final Effect<T> sideEffect)
	{
		for (final T t: wrapped)
			sideEffect.e(t);
	}

	public boolean contains(final T listener)
	{
		return wrapped.contains(listener);
	}
}