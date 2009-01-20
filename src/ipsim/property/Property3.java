package ipsim.property;

import fpeas.sideeffect.*;
import ipsim.util.Collections;

import java.util.*;

public final class Property3<T>
{
	public T value;
	public Collection<SideEffect<T>> listeners=Collections.hashSet();

	public Property3(T value)
	{
		this.value=value;
	}

	public void set(final T value)
	{
		this.value=value;

		for (final SideEffect<T> t: listeners)
			t.run(value);
	}
}