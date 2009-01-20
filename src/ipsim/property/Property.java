package ipsim.property;

import fpeas.sideeffect.*;
import org.jetbrains.annotations.*;

public abstract class Property<T>
{
	@NotNull
	public abstract T get();

	public abstract void set(@NotNull final T newValue);
	public abstract void addPropertyListener(@NotNull final SideEffect<T> propertyListener);

	public void let(T newValue, final Runnable thunk)
	{
		T old=get();
		set(newValue);
		try
		{
			thunk.run();
		}
		finally
		{
			set(old);
		}
	}
}