package ipsim.network;
/*
import fpeas.function.*;
import fpeas.lazy.*;
import fpeas.maybe.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import ipsim.*;
import ipsim.qc.*;
import static ipsim.NetworkContext.*;
import ipsim.gui.*;
import ipsim.lang.*;
import static ipsim.lang.Randoms.*;
import static ipsim.network.NetworkUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.hub.*;
import ipsim.property.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import static ipsim.util.Stream.only;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public final class Network
{
	public String testNumber;

	public Network(String testNumber)
	{
		this.testNumber=testNumber;
	}

	@NotNull
	public UserPermissions userPermissions=UserPermissions.FREEFORM;

	public final NotNullProperty<Double> zoomLevel=new NotNullProperty<Double>()
	{
		double value=1.0;

		public void set(@NotNull final Double aDouble)
		{
			if (aDouble<0.01)
				throw null;

			value=aDouble;
		}

		@NotNull
		public Double get()
		{
			return value;
		}
	};

	public String emailAddress=null;

	public final Lazy<Integer> generateComputerIDRef=new Lazy<Integer>()
	{
		public Integer invoke()
		{
			return generateComputerID();
		}
	};

	public static final class Log
	{
		public StreamProperty<String> entries=new StreamProperty<String>(new Stream<String>());

		public void clear()
		{
			entries.clear();
		}

		public void add(final String entry)
		{
			entries.append(entry);
		}
	}

	@NotNull
	public final Log log=new Log();

	public final PacketQueue packetQueue=new PacketQueue();
	public final List<PacketSource> macAddresses=arrayList();

	public int arpCacheTimeout=20;

	public boolean modified=false;

	public StreamProperty<NetworkContext> contexts=new StreamProperty<NetworkContext>(new Stream<NetworkContext>());

	public SideEffect<Card> deleteCard=new SideEffect<Card>()
	{
		public void run(final Card card)
		{
			contexts.get().foreach(deleteFrom(visibleComponentsRef, card));
		}
	};

	private static SideEffect<NetworkContext> deleteFrom(final Function<NetworkContext, StreamProperty<PacketSource>> function, final PacketSource item)
	{
		return new SideEffect<NetworkContext>()
		{
			public void run(final NetworkContext networkContext)
			{
				final StreamProperty<PacketSource> x=function.run(networkContext);
				x.set(only(x.get(), Objects.notEquals(item)));
			}
		};
	}

	public void delete(final PacketSource source)
	{
		for (final PacketSource child : PositionUtility.children(getAll(), source))
			delete(child);

		for (final NetworkContext context : contexts.get())
			context.visibleComponents.remove(source);
	}

	public static interface NetworkContexts extends Iterable<NetworkContext>
	{
		NetworkContext add(final NetworkContext context);

		int size();

		NetworkContext get(final int index);

		void clear();

		boolean isEmpty();

		void each(final SideEffect<NetworkContext> effect);
	}

	public final NetworkContext ispContext=new NetworkContext();

	public static <T> Function<T, Integer> idFactory()
	{
		return new Function<T, Integer>()
		{
			public final Map<Integer, T> ids=hashMap();

			@NotNull
			public Integer run(@NotNull final T t)
			{
				for (final Map.Entry<Integer, T> entry : ids.entrySet())
					if (entry.getValue().equals(t))
						return entry.getKey();

				for (int a=0;;a++)
					if (!ids.containsKey(a))
					{
						ids.put(a, t);
						return a;
					}
			}
		};
	}

	public final Function<Cable, Integer> cableIDFor=idFactory();
	public final Function<Hub, Integer> hubIDFor=idFactory();

	public Stream<PacketSource> topLevelComponents()
	{
		return getAll().only(new Predicate<PacketSource>()
		{
			public boolean invoke(final PacketSource packetSource)
			{
				for (int a=0;a<packetSource.numPositions();a++)
					if (packetSource.positionData(a).parent==null)
						return true;

				return false;
			}
		}
		);
	}

	public int nextComputerID=200;

	public int generateComputerID()
	{
		return nextComputerID++;
	}

	public Iterable<Maybe<Problem>> getProblems()
	{
		return map(contexts.get(), NetworkContext.problemRef);
	}

	public static Arbitrary<Network> arbitraryNetwork=new Arbitrary<Network>()
	{
		public Stream<Network> data(final Random random)
		{
			Network network=new Network(null);

			int numContexts=random.nextInt(10);
			for (int a=0;a<numContexts;a++)
				network.contexts.prepend(NetworkContext.arbitraryNetworkContext(random));

			return new Stream<Network>(network, new Promise<Stream<Network>>()
			{
				@NotNull
				public Stream<Network> override()
				{
					return data(random);
				}
			}
			);
		}
	};

	public static final Function<Random, Network> randomNetwork=new Function<Random, Network>()
	{
		@NotNull
		public Network run(@NotNull Random random)
		{
			Network network=new Network(null);
			final File file=randomOneOf(random, new File("datafiles/fullyconnected").listFiles());
			loadFromFile(network, file);
			return network;
		}
	};

	public Stream<PacketSource> getAll()
	{
		final Collection<PacketSource> all=hashSet();

		contexts.get().prepend(ispContext).foreach(new SideEffect<NetworkContext>()
		{
			public void run(final NetworkContext networkContext)
			{
				networkContext.visibleComponents.get().foreach(add(all));
			}
		}
		);

		return Stream.fromIterable(all);
	}

	public void clearAll()
	{
		for (final NetworkContext context : contexts.get().prepend(ispContext))
			context.visibleComponents.set(new Stream<PacketSource>());
	}
}
*/