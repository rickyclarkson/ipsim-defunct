package ipsim.network.connectivity.computer;
/*
import static ipsim.network.NetworkUtility.saveToString;
import static ipsim.network.NetworkUtility.loadFromString;
import static ipsim.network.Network.arbitraryNetwork;
import com.rickyclarkson.testsuite.*;
import fpeas.function.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.gui.components.*;
import static ipsim.lang.Comparators.*;
import ipsim.lang.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import static ipsim.network.NetworkUtility.*;
import static ipsim.network.PacketSourceUtility.*;
import static ipsim.network.PositionUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.card.*;
import static ipsim.network.connectivity.card.CardDrivers.*;
import static ipsim.network.connectivity.computer.RoutingTableUtility.*;
import ipsim.network.connectivity.computer.arp.incoming.*;
import ipsim.network.connectivity.computer.arp.outgoing.*;
import ipsim.network.connectivity.computer.ethernet.incoming.*;
import ipsim.network.connectivity.computer.ethernet.outgoing.*;
import ipsim.network.connectivity.computer.ip.incoming.*;
import ipsim.network.connectivity.computer.ip.outgoing.*;
import static ipsim.qc.QuickCheck.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;

import java.io.*;
import static java.util.Collections.*;
import java.util.*;
import fpeas.lazy.Lazy;

public final class Computer extends PacketSource
{
	public boolean ipForwardingEnabled=false;

	public final ArpTable arpTable=new ArpTable();

	@NotNull
	public final RoutingTable routingTable=createRoutingTable();

	private final Listeners<IncomingPacketListener> incomingPacketListeners=new Listeners<IncomingPacketListener>();

	private final Listeners<OutgoingPacketListener> outgoingPacketListeners=new Listeners<OutgoingPacketListener>();

	public int computerID=0;

	private PositionOrParent positionData;

	public static final Lazy<Boolean> testLoadingGivesRightNumberOfComponents=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			Network network=new Network(null);
			loadFromFile(network,new File("datafiles/unconnected/hubdisabled.ipsim"));
			return network.getAll().size()==7 && network.contexts.get().get(0).visibleComponents.get().size()==7;
		}

		public String toString()
		{
			return "testLoadingGivesRightNumberOfComponents";
		}
	};

	private static Predicate<Computer> isISPRef=new Predicate<Computer>()
	{
            public boolean invoke(final Computer computer) {
					return computer.isISP;
		}
	};

    public static final Predicate<Random> testLoadingRestoresBigPipeIcon=new Predicate<Random>()
	{
		public boolean invoke(Random random)
		{
			return check(random,new Predicate<Network>()
			{
				int invocations=0;
				public boolean invoke(final Network network)
				{
					invocations++;
					Function<Network,Integer> numISPs=new Function<Network, Integer>()
					{
						@NotNull
						public Integer run(@NotNull final Network n)
						{
							return n.getAll().only(isComputer).map(asComputer).only(isISPRef).size();
						}
					};

					try
					{
						return numISPs.run(network)==numISPs.run(loadFromString(network,saveToString(network)));
					}
					catch (RuntimeException e)
					{
						e.printStackTrace();
						System.out.println(invocations+" invocations (Computer.java)");//2196
						return false;
					}
				}
			}, arbitraryNetwork);
		}

		//check { => { n: Network =>
		//             def numISPs(n2: Network)=(n2.getAll only (_.isComputer) map (_.asComputer) only (_.isISP)) size
		//             numISPs(n)==numISPs(loadFromString(n,saveToString(n)) } }
	};

	public Computer(final PositionOrParent positionData)
	{
		this.positionData=positionData;

		outgoingPacketListeners.add(new ComputerEthernetOutgoing());
		outgoingPacketListeners.add(new ComputerArpOutgoing());
		outgoingPacketListeners.add(new ComputerIPOutgoing());

		incomingPacketListeners.add(new ComputerEthernetIncoming());
		incomingPacketListeners.add(new ComputerArpIncoming());
		incomingPacketListeners.add(new ComputerIPIncoming());
	}

	public Computer withID(int id)
	{
		this.computerID=id;
		return this;
	}

	public List<CardDrivers> getSortedCards(final Network network)
	{
		final List<CardDrivers> list=arrayList();

		children(network.getAll(), this).foreach(new SideEffect<PacketSource>()
		{
			public void run(final PacketSource packetSource)
			{
				final Card card=packetSource.asCard();

				if (card!=null)
				{
					final CardDrivers withDrivers=card.withDrivers;
					if (withDrivers!=null)
						list.add(withDrivers);
				}
			}
		});

		sort(list, fromFunction(ethNumberRef));

		return unmodifiableList(list);
	}

	public Stream<Card> getCards(final Network network)
	{
		return children(network.getAll(), this).map(asCardRef);
	}

	public int getFirstAvailableEthNumber(final Network network)
	{
		final List<CardDrivers> cards=getSortedCards(network);

		for (int a=0;;a++)
		{
			boolean foundConflict=false;

			for (final CardDrivers card : cards)
				if (a==card.ethNumber)
				{
					foundConflict=true;
					break;
				}

			if (!foundConflict)
				return a;
		}
	}

	@Override
	public String toString()
	{
		return "Computer";
	}

	@Override
	public Listeners<IncomingPacketListener> getIncomingPacketListeners()
	{
		return incomingPacketListeners;
	}

	@Override
	public Listeners<OutgoingPacketListener> getOutgoingPacketListeners()
	{
		return outgoingPacketListeners;
	}

	@Override
	public void accept(final PacketSourceVisitor2 visitor)
	{
		visitor.visit(this);
	}

	@Override
	public void setPositionData(int index, @NotNull PositionOrParent positionData)
	{
		if (index==0)
			this.positionData=positionData;
		else
			throw null;
	}

	public Stream<PositionOrParent> positions()
	{
		return one(positionData);
	}

	@Override
	public <R> R accept(final PacketSourceVisitor<R> visitor)
	{
		return visitor.visit(this);
	}

	public boolean isISP=false;

	public static final Lazy<Boolean> testComputerRetainsInstalledCards=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			Network network=new Network(null);
			NetworkContext context=network.contexts.append(new NetworkContext());

			Computer computer=context.visibleComponents.prependIfNotPresent(new Computer(position(new Point(300,300))));
			Card card=context.visibleComponents.prependIfNotPresent(new Card(false, parent(computer)));
			card.installDeviceDrivers(network);

			return computer.getCards(network).only(Card.hasDrivers).size()==1;
		}

		public String toString()
		{
			return "testComputerRetainsInstalledCard";
		}
	};

	public static final Lazy<Boolean> testDeletingComputerDeletesItsCards=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			Network network=new Network(null);
			NetworkContext context=network.contexts.append(new NetworkContext());

			Computer computer=context.visibleComponents.prependIfNotPresent(new Computer(position(new Point(300,300))));
			context.visibleComponents.prependIfNotPresent(new Card(false,parent(computer)));

			network.delete(computer);
			return network.getAll().isEmpty();
		}

		public String toString()
		{
			return "deleting computer deletes its cards";
		}
	};

	public static PacketSource arbitraryComputer(Random random)
	{
		return new Computer(PositionOrParent.position(PointUtility.arbitraryPoint(random)));
	}
}
*/