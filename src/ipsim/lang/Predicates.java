package ipsim.lang;

import fpeas.function.*;
import fpeas.predicate.*;
import static fpeas.predicate.PredicateUtility.*;

import java.awt.*;

public class Predicates
{
	public static final Predicate<Object> TRUE=new Predicate<Object>()
	{
		public boolean invoke(final Object object)
		{
			return true;
		}
	};

	public static <T> Predicate<T> or(final Predicate<T> one, final Predicate<T> two)
	{
		return new Predicate<T>()
		{
			public boolean invoke(final T t)
			{
				return one.invoke(t) || two.invoke(t);
			}
		};
	}

	public static <T> Predicate<T> neither(final Predicate<T> one, final Predicate<T> two)
	{
		return not(or(one,two));
	}

	public static Predicate<Component> covariant(final Predicate<Object> predicate)
	{
		return new Predicate<Component>()
		{
			public boolean invoke(final Component component)
			{
				return predicate.invoke(component);
			}
		};
	}

	public static <T> Predicate<T> fromFunction(final Function<T, Boolean> function)
	{
		return new Predicate<T>()
		{
			public boolean invoke(final T t)
			{
				return function.run(t);
			}
		};
	}
}