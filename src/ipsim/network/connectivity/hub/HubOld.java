package ipsim.network.connectivity.hub;
/*
import com.rickyclarkson.testsuite.*;
import ipsim.*;
import ipsim.awt.*;
import ipsim.gui.components.*;
import ipsim.lang.*;
import static ipsim.lang.PositionOrParent.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.hub.incoming.*;
import ipsim.network.connectivity.hub.outgoing.*;
import ipsim.property.*;
import ipsim.util.Collections;
import ipsim.util.*;
import static ipsim.util.Stream.*;
import org.jetbrains.annotations.*;
import fpeas.lazy.Lazy;
import java.util.*;

public class Hub extends PacketSource implements ModelObject
{
	public final PropertyB power=new PropertyB(false);

	private final Listeners<IncomingPacketListener> incomingPacketListeners=new Listeners<IncomingPacketListener>();

	private final Listeners<OutgoingPacketListener> outgoingPacketListeners=new Listeners<OutgoingPacketListener>();

	@NotNull
	private PositionOrParent positionData;

	public Hub(final PositionOrParent positionData)
	{
		this.positionData=positionData;

		incomingPacketListeners.add(new HubIncoming());
		outgoingPacketListeners.add(new HubOutgoing());

	}

	public void setPower(final boolean status)
	{
		power.set(status);
	}

	public boolean isPowerOn()
	{
		return power.get();
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

	public Collection<Cable> getCables(Network network)
	{
		final Collection<Cable> cables=Collections.hashSet();

		for (final PacketSource element: PositionUtility.children(network.getAll(),this))
		{
			@Nullable final Cable cable=PacketSourceUtility.asCable(element);

			if (cable!=null)
				cables.add(cable);
		}

		return cables;
	}

	@Override
	public String toString()
	{
		return "Hub";
	}

	@Override
	public final Listeners<IncomingPacketListener> getIncomingPacketListeners()
	{
		return incomingPacketListeners;
	}

	@Override
	public final Listeners<OutgoingPacketListener> getOutgoingPacketListeners()
	{
		return outgoingPacketListeners;
	}

	@Override
	public <R> R accept(final PacketSourceVisitor<R> visitor)
	{
		return visitor.visit(this);
	}

	public void accept(ModelVisitor visitor)
	{
		visitor.visit(this);
	}

	public static final Lazy<Boolean> testDeletingHubDeletesCables=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			Network network=new Network(null);
			NetworkContext context=network.contexts.append(new NetworkContext());

			Hub hub=context.visibleComponents.prependIfNotPresent(new Hub(position(new Point(300,300))));
			context.visibleComponents.prependIfNotPresent(new Cable(Caster.asNotNull(parent(hub)),position(new Point(400,400))));

			network.delete(hub);
			return network.getAll().isEmpty();
		}
	};

	public static PacketSource arbitraryHub(Random random)
	{
		return new Hub(PositionOrParent.position(PointUtility.arbitraryPoint(random)));
	}
}
*/