package ipsim.util;

import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import fpeas.pair.*;
import fpeas.lazy.Lazy;
import static fpeas.pair.PairUtility.*;
import fpeas.predicate.*;
import static fpeas.predicate.PredicateUtility.*;
import fpeas.sideeffect.*;
import static ipsim.Caster.*;
import static ipsim.util.Collections.*;
import org.jetbrains.annotations.*;

import static java.util.Collections.*;
import java.util.*;

public final class Stream<T> implements Iterable<T>
{
	private final T car;
	private final Promise<Stream<T>> cdr;

	public static <T> Function2<Stream<T>, Stream<T>, Stream<T>> concat()
	{
		return new Function2<Stream<T>, Stream<T>, Stream<T>>()
		{
			public Stream<T> invoke(final Stream<T> one, final Stream<T> two)
			{
				final List<T> list=arrayList();

				final SideEffect<T> effect=new SideEffect<T>()
				{
					public void run(final T t)
					{
						list.add(t);
					}
				};

				one.foreach(effect);
				two.foreach(effect);

				return fromIterable(list);
			}
		};
	}

	public Stream()
	{
		car=null;
		cdr=null;
	}

	public Stream(T car, Promise<Stream<T>> cdr)
	{
		this.car=car;
		this.cdr=cdr;
	}

	@NotNull
	public Stream<T> only(final Predicate<? super T> predicate)
	{
		Stream<T> current=this;
		while (!current.isEmpty() && !predicate.invoke(current.car()))
			current=current.cdr().force();

		final Stream<T> found=current;

		return current.isEmpty() ? current : new Stream<T>(current.car(), new Promise<Stream<T>>()
		{
			@Override
			@NotNull
			public Stream<T> override()
			{
				return found.cdr().force().only(predicate);
			}
		});
	}

	public void foreach(@NotNull final SideEffect<T> sideEffect)
	{
		if (isEmpty())
			return;

		@NotNull
		Stream<T> current=this;

		while (!current.isEmpty())
		{
			sideEffect.run(current.car());
			current=current.cdr().force();
		}
	}

	public <U> Stream<U> map(final Function<T, U> function)
	{
		return staticMap(this, function);
	}

	private static <T, U> Stream<U> staticMap(final Stream<T> stream, final Function<T, U> function)
	{
		return stream.isEmpty() ? new Stream<U>() : new Stream<U>(function.run(stream.car()), new Promise<Stream<U>>()
		{
			@Override
			@NotNull
			public Stream<U> override()
			{
				return staticMap(stream.cdr().force(), function);
			}
		});
	}


	public static <T> Stream<T> fromIterable(final Iterable<T> iterable)
	{
		return getNext(iterable.iterator());
	}

	@NotNull
	private static <T> Stream<T> getNext(final Iterator<T> iterator)
	{
		if (iterator.hasNext())
			return new Stream<T>(iterator.next(), new Promise<Stream<T>>()
			{
				@Override
				@NotNull
				public Stream<T> override()
				{
					return getNext(iterator);
				}
			});

		return new Stream<T>();
	}

	public static <T> Stream<T> only(final Stream<T> stream, final Predicate<? super T> predicate)
	{
		return stream.isEmpty() ? stream : stream.only(predicate);
	}

	public Stream<T> prepend(T t)
	{
		return new Stream<T>(t, Promise.<Stream<T>>forced(this));
	}

	public int size()
	{
		final int[] count={0};

		foreach(new SideEffect<T>()
		{
			public void run(final T t)
			{
				count[0]++;
			}
		});

		return count[0];
	}

	public boolean contains(final T t)
	{
		final boolean[] found={false};

		foreach(new SideEffect<T>()
		{
			public void run(final T t2)
			{
				if (t2.equals(t))
					found[0]=true;
			}
		});

		return found[0];
	}

	@NotNull
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			Stream<T> rest=Stream.this;

			public boolean hasNext()
			{
				return !rest.isEmpty();
			}

			public T next()
			{
				if (!hasNext())
					throw new NoSuchElementException();

				T result=rest.car();
				rest=rest.cdr.force();
				return result;
			}

			public void remove()
			{
				throw null;
			}
		};
	}

	public static <T> Stream<T> one(final T t)
	{
		return new Stream<T>().prepend(t);
	}

	public boolean isEmpty()
	{
		return car==null;
	}

	@Nullable
	public Integer indexOf(final Object object)
	{
		if (object instanceof Predicate)
			throw null;

		int a=0;

		for (final T t2: this)
			if (t2.equals(object))
				return a;
			else
				a++;

		return null;
	}

	public T car()
	{
		return asNotNull(car);
	}

	public Promise<Stream<T>> cdr()
	{
		return asNotNull(cdr);
	}

	public static final Lazy<Boolean> testOnly=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Predicate<? super Integer> isOdd=new Predicate<Integer>()
			{
				public boolean invoke(final Integer integer)
				{
					return integer%2!=0;
				}
			};

			return fromIterable(Collections.asList(1, 2, 3, 4)).only(isOdd).size()==2;
		}
	}, testMap=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Function<Integer, Integer> doubleIt=new Function<Integer, Integer>()
			{
				@Override
				@NotNull
				public Integer run(@NotNull final Integer integer)
				{
					return integer*2;
				}
			};

			return fromIterable(Collections.asList(1, 2, 3, 4)).map(doubleIt).car()==2;
		}
	}, testIndexOf=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			return fromIterable(Collections.asList(1, 2, 3, 4)).indexOf(2)==1;
		}
	}, testIterator=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			return fromIterable(Collections.asList(1, 2, 3, 4)).iterator().next()==1;
		}
	}, testEmpty=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final Function<Integer, Integer> throwException=FunctionUtility.throwRuntimeException();

			final Predicate<? super Integer> none=new Predicate<Integer>()
			{
				public boolean invoke(final Integer integer)
				{
					return false;
				}
			};

			return new Stream<Integer>().map(throwException).map(throwException).only(none).prepend(3).contains(3);
		}
	}, testContains=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			return fromIterable(Collections.asList(1, 2, 3, 4)).contains(3);
		}
	};

	public Pair<Stream<T>, Stream<T>> partition(final Predicate<T> by)
	{
		return pair(only(by), only(not(by)));
	}

	public static <T> Function<Stream<T>, Stream<T>> sortByRef(final Comparator<T> comparator)
	{
		return new Function<Stream<T>, Stream<T>>()
		{
			@Override
			@NotNull
			public Stream<T> run(@NotNull final Stream<T> stream)
			{
				return stream.sortBy(comparator);
			}
		};
	}

	public Stream<T> sortBy(final Comparator<T> comparator)
	{
		final List<T> list=asList();

		sort(list, comparator);

		return fromIterable(list);
	}

	private List<T> asList()
	{
		final List<T> list=arrayList();

		foreach(new SideEffect<T>()
		{
			public void run(final T t)
			{
				list.add(t);
			}
		});
		return list;
	}

	public T get(final int index)
	{
		int counter=0;
		for (T t : this)
			if (counter++==index)
				return t;

		throw null;
	}

	public Stream<T> append(final T t)
	{
		List<T> list=asList();
		list.add(t);
		return fromIterable(list);
	}

	public Stream<T> ifNotEmpty()
	{
		if (isEmpty() || hasDuplicates())
			throw null;

		return this;
	}

	public boolean hasDuplicates()
	{
		Set<T> against=new HashSet<T>();
		
		for (final T t: this)
			against.add(t);

		return size()!=against.size();
	}

	public Stream<T> merge(final Stream<T> stream)
	{
		Stream<T> result=this;

		for (final T t: stream)
			if (!result.contains(t))
				result=result.append(t);

		return result;
	}

	public Stream<T> ifNoDuplicates()
	{
		if (hasDuplicates())
			throw null;

		return this;
	}

	public boolean any(final Predicate<T> predicate)
	{
		for (final T t: this)
			if (predicate.invoke(t))
				return true;

		return false;
	}

	public boolean all(final Predicate<T> predicate)
	{
		for (final T t: this)
			if (!predicate.invoke(t))
				return false;

		return true;
	}

	public Stream<T> take(final int number)
	{
		return isEmpty() ? this : number<=0 ? new Stream<T>() : new Stream<T>(car(),new Promise<Stream<T>>()
		{
			@NotNull
			public Stream<T> override()
			{
				return cdr().force().take(number-1);
			}
		});
	}
}
