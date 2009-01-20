package ipsim.network.connectivity;
/*
import static ipsim.network.PacketSourceUtility.asComputerRef;
import ipsim.gui.components.*;
import ipsim.lang.*;
import ipsim.util.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.*;
import org.jetbrains.annotations.*;
import fpeas.predicate.*;
import fpeas.function.*;

import java.util.*;

public abstract class PacketSource
{
	public static final Predicate<PacketSource> isComputer=PacketSourceUtility.isComputerRef;
	public static Function<PacketSource, Computer> asComputer=asComputerRef;

	public abstract <R> R accept(PacketSourceVisitor<R> visitor);
	public abstract Listeners<IncomingPacketListener> getIncomingPacketListeners();
	public abstract Listeners<OutgoingPacketListener> getOutgoingPacketListeners();
	public abstract void accept(final PacketSourceVisitor2 visitor);

	public final PositionOrParent positionData(int index)
	{
		return positions().get(index);
	}

	public abstract void setPositionData(int index,@NotNull PositionOrParent positionData);

	public final int numPositions()
	{
		return positions().size();
	}

	public abstract Stream<PositionOrParent> positions();
	@Override
	public abstract String toString();

	public Computer asComputer()
	{
		return PacketSourceUtility.asComputer(this);
	}

	public Hub asHub()
	{
		return PacketSourceUtility.asHub(this);
	}

	public Card asCard()
	{
		return PacketSourceUtility.asCard(this);
	}

	public Cable asCable()
	{
		return PacketSourceUtility.asCable(this);
	}

	public static PacketSource arbitraryPacketSource(Random random)
	{
		switch (random.nextInt(4))
		{
			case 0:
				return Cable.arbitraryCable(random);

			case 1:
				return Card.arbitraryCard(random);

			case 2:
				return Computer.arbitraryComputer(random);

			case 3:
				return Hub.arbitraryHub(random);

			default:
				throw null;
		}
	}
        }*/