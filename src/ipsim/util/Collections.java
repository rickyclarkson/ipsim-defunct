package ipsim.util;

import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import fpeas.lazy.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import ipsim.lang.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.Map.*;

public class Collections
{
	public static <T> List<T> arrayList()
	{
		return new ArrayList<T>();
	}

	public static <T extends Stringable> String asString(final Iterable<T> iterable)
	{
		final StringBuilder answer=new StringBuilder();

		boolean empty=true;

		for (final T item : iterable)
		{
			empty=false;
			answer.append(item.toString());
			answer.append(',');
		}

		if (!empty)
			answer.deleteCharAt(answer.length()-1);

		return answer.toString();
	}

	public static <K, V> Map<K, V> hashMap()
	{
		return new LinkedHashMap<K, V>();
	}

	public static <T> Set<T> hashSet()
	{
		return new LinkedHashSet<T>();
	}

	public static <T> Stack<T> stack()
	{
		final java.util.Stack<T> real=new java.util.Stack<T>();

		return new Stack<T>()
		{
			public void push(final T object)
			{
				real.push(object);
			}

			public boolean isEmpty()
			{
				return real.isEmpty();
			}

			public T pop()
			{
				return real.pop();
			}
		};
	}

	public static <T extends Stringable> String asString(final Iterable<T> stringables, final String separator)
	{
		final StringBuilder builder=new StringBuilder();
		boolean isEmpty=true;

		for (final Stringable stringable : stringables)
		{
			builder.append(stringable.toString());
			builder.append(separator);
			isEmpty=false;
		}

		if (!isEmpty)
			builder.delete(builder.length()-separator.length(), builder.length());

		return builder.toString();
	}

	public static <K, V> String asString(final Collection<Entry<K, V>> entrySet, final Function<K, String> keyAsString, final Function<V, String> valueAsString, final String betweenKeyAndValue, final String separator)
	{
		final StringBuilder builder=new StringBuilder();
		boolean isEmpty=true;

		for (final Entry<K, V> entry : entrySet)
		{
			builder.append(keyAsString.run(entry.getKey()));
			builder.append(betweenKeyAndValue);
			builder.append(valueAsString.run(entry.getValue()));
			builder.append(separator);
			isEmpty=false;
		}

		if (!isEmpty)
			builder.delete(builder.length()-separator.length(), builder.length());

		return builder.toString();
	}

	public static <T> int count(final Iterable<T> iterable, final Predicate<T> matcher)
	{
		int total=0;

		for (final T t : iterable)
			if (matcher.invoke(t))
				total++;

		return total;
	}

	public static <T> boolean any(final Stream<? extends T> stream, final Predicate<T> matcher)
	{
		return !stream.only(matcher).isEmpty();
	}

	public static <T, R> Iterable<R> forEach(final Iterable<T> iterable, final Function<T, R> runer)
	{
		final Collection<R> results=arrayList();

		for (final T item : iterable)
			results.add(runer.run(item));

		return results;
	}

	public static <T> boolean all(final Iterable<T> iterable, final Predicate<? super T> predicate)
	{
		for (final T item : iterable)
			if (!predicate.invoke(item))
				return false;

		return true;
	}

	public static <T> Stream<T> only(final Iterable<T> iterable,final Predicate<T> filter)
	{
		return Stream.fromIterable(iterable).only(filter);
	}

	public static <T> String append(final Iterable<T> iterable, final Function<T, String> runer)
	{
		final StringBuilder builder=new StringBuilder();

		for (final T item : iterable)
			builder.append(runer.run(item));

		return builder.toString();
	}

	public static <T> int sum(final Function<T, Integer> function, final T... operands)
	{
		int result=0;

		for (final T item : operands)
			result+=function.run(item);

		return result;
	}

	public static <T> int max(final Function<T, Integer> function, final T... operands)
	{
		int result=0;

		for (final T item : operands)
			result=Math.max(result, function.run(item));

		return result;
	}

	public static <T> List<T> sort2(final Stream<T> stream, final Comparator<? super T> comparator)
	{
		final List<T> copy=arrayList();
		stream.foreach(add(copy));
		java.util.Collections.sort(copy, comparator);
		return copy;
	}

	public static <T> SideEffect<T> add(final Collection<T> to)
	{
		return new SideEffect<T>()
		{
			public void run(final T t)
			{
				to.add(t);
			}
		};
	}

	public static <K, V> Map<K, V> linkedHashMap()
	{
		return new LinkedHashMap<K, V>();
	}

	public static <T> int size(final Iterable<T> iterable)
	{
		int count=0;
		for (final T anIterable : iterable)
			count++;

		return count;
	}

	public static <T> List<T> arrayList(@NotNull final Collection<? extends T> collection)
	{
		return new ArrayList<T>(collection);
	}

	public static <T> List<T> asList(final T... array)
	{
		return new ArrayList<T>(java.util.Arrays.asList(array));
	}

	public static <T, R> Iterable<R> map(final Iterable<T> input, final Function<T, R> converter)
	{
		final Iterator<T> wrapped=input.iterator();

		return new Iterable<R>()
		{
			public Iterator<R> iterator()
			{
				return new Iterator<R>()
				{
					public boolean hasNext()
					{
						return wrapped.hasNext();
					}

					public R next()
					{
						return converter.run(wrapped.next());
					}

					public void remove()
					{
						wrapped.remove();
					}
				};
			}
		};
	}

	public static <T> Lazy<List<T>> arrayListRef()
	{
		return new Lazy<List<T>>()
		{
			public List<T> invoke()
			{
				return arrayList();
			}
		};
	}

	public static String join(@NotNull final Iterable<String> iterable, final String separator)
	{
		final StringBuilder builder=new StringBuilder();

		boolean first=true;

		for (final String string: iterable)
		{
			if (!first)
				builder.append(separator);

			builder.append(string);

			first=false;
		}

		return builder.toString();
	}

	public static <T,C extends Iterable<? extends T>,D extends Collection<T>> D add(final Function<C,D> clone,final C collection, final T element)
	{
		final D result=clone.run(collection);
		result.add(element);
		return result;
	}

	public static final Lazy<Boolean> testAddCollection=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
                    System.out.println("Collections.testAddCollection");

			final Function<List<? extends String>, List<String>> clone=arrayListCopy();
			final List<String> list=arrayList();
			final Collection<String> objects=add(clone,add(clone, list, "hello"), "goodbye");
			return objects.contains("hello") && objects.contains("goodbye");
		}

		@Override
		public String toString()
		{
			return "testAddCollection";
		}
	};

	public static <T> Function<List<? extends T>, List<T>> arrayListCopy()
	{
		return new Function<List<? extends T>, List<T>>()
		{
			@NotNull
			public List<T> run(@NotNull final List<? extends T> ts)
			{
				return arrayList(ts);
			}
		};
	}

	public static <T> void sideEffectOnEach(final Iterable<SideEffect<T>> effects, final T value)
	{
		for (final SideEffect<T> effect: effects)
			effect.run(value);
	}

	public static <T,R,C extends Collection<R>> C map(final Iterable<T> iterable,final Lazy<C> constructor, final Function<T, R> mapper)
	{
		final C c=constructor.invoke();

		for (final T t: iterable)
			c.add(mapper.run(t));

		return c;
	}

	public static <T> AddOrSet<T> addOrSet(final List<T> list, final int index, final T item)
	{
		if (list.size()<=index)
			list.add(index,item);
		else
			list.set(index,item);

		return new AddOrSet<T>()
		{
			public AddOrSet<T> addOrSet(final int index2, final T item2)
			{
				return Collections.addOrSet(list,index2,item2);
			}
		};
	}

	public static <K,V> Map<V,Set<K>> invert(final Map<K,V> map)
	{
		Map<V, Set<K>> result=hashMap();

		for (final Map.Entry<K,V> entry: map.entrySet())
		{
			if (!result.containsKey(entry.getValue()))
				result.put(entry.getValue(),Collections.<K>hashSet());

			result.get(entry.getValue()).add(entry.getKey());
		}

		return result;
	}

	public static <K,V> Map<K,V> reinvert(final Map<V, Set<K>> inverted)
	{
		Map<K,V> result=hashMap();

		for (final Entry<V, Set<K>> entry: inverted.entrySet())
			for (final K key: entry.getValue())
				result.put(key,entry.getKey());

		return result;
	}

	public static interface AddOrSet<T>
	{
		AddOrSet<T> addOrSet(int index,T item);
	}

	public static final Lazy<Boolean> testAddOrSet=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			List<Integer> list=arrayList();

			addOrSet(list,0,5).addOrSet(1,6).addOrSet(0,7).addOrSet(0,8).addOrSet(1,3);

			final Iterator<Integer> iter=list.iterator();
			return iter.next()==8 && iter.next()==3 && !iter.hasNext();
		}
	};
}
