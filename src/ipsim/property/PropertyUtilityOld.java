package ipsim.property;

/*import fpeas.sideeffect.*;
import ipsim.util.Collections;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PropertyUtility
{
	public static <T> Property<T> newProperty(@NotNull final T starting)
	{
		return new Property<T>()
		{
			public T value=starting;
			public final List<SideEffect<T>> listeners=Collections.arrayList();

			@Override
			@NotNull
			public T get()
			{
				return value;
			}

			@Override
			public void set(@NotNull final T newValue)
			{
				value=newValue;
				sideEffectOnEach(listeners, value);
			}

			@Override
			public void addPropertyListener(@NotNull final SideEffect<T> propertyListener)
			{
				listeners.add(propertyListener);
			}
		};
	}

	public static <T> Property2<T> property2(final T starting)
	{
		return new Property2<T>()
		{
			T current=starting;

			public void set(final T t)
			{
				current=t;
			}

			public T get()
			{
				return current;
			}
		};
	}
}
*/