package ipsim.property;

import fpeas.function.*;
import ipsim.lang.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class StreamProperty<T> implements Iterable<T>
{
	@NotNull
	private Stream<T> value;

	public StreamProperty(@NotNull Stream<T> value)
	{
		this.value=value;
	}

	public <U extends T> U prepend(U u)
	{
		if (value.contains(u))
			throw null;

		set(new Stream<T>(u, Promise.<Stream<T>>forced(value)));
		return u;
	}

	public void set(@NotNull final Stream<T> stream)
	{
		value=stream.ifNoDuplicates();
	}

	@NotNull
	public Stream<T> get()
	{
		return value;
	}

	public <U extends T> U remove(final U u)
	{
		value=value.only(Objects.notEquals(u));
		return u;
	}

	public <U extends T> U prependIfNotPresent(final U u)
	{
		if (!value.contains(u))
			prepend(u);

		return u;
	}

	public <U extends T> U append(final U u)
	{
		value=value.append(u);
		return u;
	}

	public void clear()
	{
		value=new Stream<T>();
	}

	public <R> Stream<R> map(final Function<T, R> func)
	{
		return value.map(func);
	}

	public int size()
	{
		return value.size();
	}

	public Iterator<T> iterator()
	{
		return value.iterator();
	}

	public void addAll(final Stream<T> stream)
	{
		value=Stream.<T>concat().invoke(value,stream);
	}

	@Nullable
	public Integer indexOf(final Object object)
	{
		return value.indexOf(object);
	}

	public void merge(final Stream<T> stream)
	{
		set(value.merge(stream));
	}
}