package ipsim.network.connectivity.cable;
/*
import static ipsim.lang.PositionOrParent.position;
import static fpeas.predicate.PredicateUtility.*;
import ipsim.gui.components.*;
import ipsim.lang.*;
import ipsim.network.connectivity.*;
import static ipsim.network.connectivity.cable.CableType.*;
import ipsim.network.connectivity.cable.incoming.*;
import ipsim.network.connectivity.cable.outgoing.*;
import ipsim.util.*;
import ipsim.awt.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class Cable extends PacketSource
{
	private final Listeners<IncomingPacketListener> incomingPacketListeners=new Listeners<IncomingPacketListener>();

	private final Listeners<OutgoingPacketListener> outgoingPacketListeners=new Listeners<OutgoingPacketListener>();

	private CableType cableType=STRAIGHT_THROUGH;

	@NotNull
	PositionOrParent positionOne,positionTwo;

	public Cable(@NotNull final PositionOrParent one, @NotNull final PositionOrParent two)
	{
		positionOne=one;
		positionTwo=two;

		incomingPacketListeners.add(new CableIncoming());
		outgoingPacketListeners.add(new CableOutgoing());
	}

	public Stream<PacketSource> getEnds()
	{
		final Stream<PacketSource> possibleParents=new Stream<PacketSource>(positionOne.parent,Promise.<Stream<PacketSource>>forced(new Stream<PacketSource>())).prepend(positionTwo.parent);

		return possibleParents.only(not(NullUtility.<PacketSource>ifNull()));
	}

	public boolean canTransferPackets()
	{
		final Stream<PacketSource> ends=getEnds();

		if (ends.size()!=2)
			return false;

		return cableType.canTransferPackets(ends.car(), ends.cdr().force().car());
	}

	@Override
	public void accept(final PacketSourceVisitor2 visitor)
	{
		visitor.visit(this);
	}

	@Override
	public void setPositionData(int index, @NotNull PositionOrParent positionData)
	{
		switch (index)
		{
			case 0:
				positionOne=positionData;
				break;

			case 1:
				positionTwo=positionData;
				break;

			default:
				throw null;
		}
	}

	public Stream<PositionOrParent> positions()
	{
		return new Stream<PositionOrParent>().prepend(positionTwo).prepend(positionOne);
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

	public CableType getCableType()
	{
		return cableType;
	}

	public void setCableType(final CableType cableType)
	{
		this.cableType=cableType;
	}

	@Override
	public <R> R accept(final PacketSourceVisitor<R> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "Cable";
	}

	public static Cable arbitraryCable(Random random)
	{
		return new Cable(position(PointUtility.arbitraryPoint(random)),position(PointUtility.arbitraryPoint(random)));
	}
}
*/