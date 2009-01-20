package ipsim.network.connectivity.card;
/*
import fpeas.function.*;
import fpeas.predicate.*;
import static ipsim.Caster.*;
import ipsim.gui.components.*;
import ipsim.lang.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.incoming.*;
import ipsim.network.connectivity.card.outgoing.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ethernet.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import ipsim.awt.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class Card extends PacketSource
{
	@Nullable
	public CardDrivers withDrivers=null;

	public final Listeners<IncomingPacketListener> incomingPacketListeners=new Listeners<IncomingPacketListener>();
	public final Listeners<OutgoingPacketListener> outgoingPacketListeners=new Listeners<OutgoingPacketListener>();
	public final boolean special;
	public static final Predicate<Card> hasDrivers=new Predicate<Card>()
	{
		public boolean invoke(final Card card)
		{
			return card.withDrivers!=null;
		}
	};
	public static Function<Card, CardDrivers> withDriversRef=new Function<Card, CardDrivers>()
	{
		@Override
		@NotNull
		public CardDrivers run(@NotNull final Card card)
		{
			return card.withDrivers;
		}
	};

	public Card(final boolean special, final PositionOrParent positionData)
	{
		this.special=special;
		this.positionData=positionData;

		incomingPacketListeners.add(new CardIncoming());
		outgoingPacketListeners.add(new CardOutgoing());
	}

	public CardDrivers installDeviceDrivers(final Network network)
	{
		final Computer computer=asNotNull(positionData.parent.asComputer());
		final int ethNumber=computer.getFirstAvailableEthNumber(network);

		withDrivers=new CardDrivers(IPAddressUtility.zero(), NetMaskUtility.zero(), ethNumber, this);
		return withDrivers;
	}

	public void uninstallDeviceDrivers()
	{
		final CardDrivers drivers=withDrivers;

		if (drivers==null)
			return;

		if (0!=drivers.ipAddress.get().rawValue())
			return;

		drivers.ethNumber=-1;
		withDrivers=null;
	}

	@Nullable
	public Cable getCable(@NotNull Network network)
	{
		final Stream<PacketSource> iter=PositionUtility.children(network.getAll(),this);
		return iter.isEmpty() ? null : iter.car().asCable();
	}

	public MacAddress getMacAddress(final Network network)
	{
		if (!network.macAddresses.contains(this))
			network.macAddresses.add(this);

		return new MacAddress(network.macAddresses.indexOf(this)+1);
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
	public String toString()
	{
		return "Card";
	}

	PositionOrParent positionData;

	@Override
	public <R> R accept(final PacketSourceVisitor<R> visitor)
	{
		return visitor.visit(this);
	}

	public static PacketSource arbitraryCard(Random random)
	{
		return new Card(random.nextInt(2)==0,PositionOrParent.position(PointUtility.arbitraryPoint(random)));
	}
}
*/