package ipsim;
/*
import fpeas.function.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import static ipsim.lang.Objects.*;
import static ipsim.lang.Runnables.nothing;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.persistence.*;
import static ipsim.persistence.XMLDeserialiser.*;
import ipsim.persistence.delegates.*;
import static ipsim.persistence.delegates.DelegateHelper.*;
import static ipsim.persistence.delegates.ProblemDelegate.*;
import ipsim.property.*;
import ipsim.swing.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;
import org.w3c.dom.*;

import javax.swing.*;
import java.util.*;

public final class NetworkContext
{
	public final StreamProperty<PacketSource> visibleComponents=new StreamProperty<PacketSource>(new Stream<PacketSource>());

	public static final Function<NetworkContext, StreamProperty<PacketSource>> visibleComponentsRef=new Function<NetworkContext, StreamProperty<PacketSource>>()
	{
		@NotNull
		public StreamProperty<PacketSource> run(@NotNull final NetworkContext networkContext)
		{
			return networkContext.visibleComponents;
		}
	};

	public static NetworkContext arbitraryNetworkContext(Random random)
	{
		NetworkContext context=new NetworkContext();

		if (random.nextInt(2)==0)
			context.problem.set(ProblemUtility.arbitraryProblem(random));

		int numComponents=random.nextInt(30);

		for (int a=0;a<numComponents;a++)
			context.visibleComponents.prepend(PacketSource.arbitraryPacketSource(random));

		return context;
	}

	public static SerialisationDelegate<NetworkContext,NetworkContext> delegate(final Network network)
	{
		return new SerialisationDelegate<NetworkContext,NetworkContext>()
		{
			public void writeXML(final XMLSerialiser serialiser, final NetworkContext context)
			{
				final Problem problem=context.problem.value;

				lift(problem,nothing,serialiser.writeObjectRef("problem", problemDelegate)).run();

				final int[] a={0};

				context.visibleComponents.get().foreach(new SideEffect<PacketSource>()
				{
					public void run(final PacketSource component)
					{
						DelegateHelper.writePacketSource(serialiser, component, "visibleComponent "+a[0]++, network);
					}
				});

			}

			public NetworkContext readXML(final XMLDeserialiser deserialiser, final Node node, final NetworkContext andOthers)
			{
				andOthers.problem.set(deserialiser.readObject(node, "problem", problemDelegate, Caster.asFunction(Problem.class)));

				for (final String name : getObjectNames(node))
					if (name.startsWith("visibleComponent "))
						andOthers.visibleComponents.prependIfNotPresent(readFromDeserialiser(deserialiser, node, name, network).car());

				return andOthers;
			}

			public NetworkContext construct()
			{
				return new NetworkContext();
			}

			@NotNull
			public String getIdentifier()
			{
				return "NetworkContext";
			}
		};
	}

	public static final Function<NetworkContext, Maybe<Problem>> problemRef=new Function<NetworkContext, Maybe<Problem>>()
	{
		@NotNull
		public Maybe<Problem> run(@NotNull final NetworkContext networkContext)
		{
			return fromNullable(networkContext.problem.value);
		}
	};

	@NotNull
	public final Property3<Problem> problem=new Property3<Problem>(null);

	public static boolean confirm(final JFrame frame, final String message)
	{
		return JOptionPane.YES_OPTION==CustomJOptionPane.showYesNoCancelDialog(frame, message, "Confirm");
	}

	public static void errors(final JFrame frame, final String s)
	{
		JOptionPane.showMessageDialog(frame, s, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static int askUserForNumberOfFaults(JFrame frame)
	{
		while (true)
			try
			{
				int result=Integer.valueOf(JOptionPane.showInputDialog(frame, "How many faults? (1 to 5)"));

				if (result>=1 && result<=5)
					return result;
			}
			catch (NumberFormatException e)
			{
			}
	}

	//TODO make this not a copy of the code in Network
	public Stream<PacketSource> topLevelComponents()
	{
		return visibleComponents.get().only(new Predicate<PacketSource>()
		{
			public boolean invoke(final PacketSource packetSource)
			{
				return packetSource.positions().all(PositionOrParent.isPosition);
			}
		});
	}
}*/