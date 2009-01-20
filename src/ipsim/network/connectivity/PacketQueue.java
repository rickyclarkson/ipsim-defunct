package ipsim.network.connectivity;

import fpeas.sideeffect.*;
import ipsim.network.*;
import ipsim.util.Collections;
import org.jetbrains.annotations.*;

import java.util.*;

public class PacketQueue
{
	public final Queue<Runnable> pendingRequests;

	public final List<Runnable> emptyQueueListeners;

	public PacketQueue()
	{
		pendingRequests=new LinkedList<Runnable>();
		emptyQueueListeners=new LinkedList<Runnable>();
	}

	public void enqueueOutgoingPacket(final Network network,final Packet packet, final PacketSource source)
	{
		final Listeners<OutgoingPacketListener> listeners=source.getOutgoingPacketListeners();
		listeners.visitAll(new SideEffect<OutgoingPacketListener>()
		{
			public void run(final OutgoingPacketListener listener)
			{
				pendingRequests.add(new Runnable()
				{
					public void run()
					{
						listener.packetOutgoing(packet, source).run(network);

						processed(this);

						handleIfEmpty();
					}
				});
			}
		});
	}

	public void enqueueIncomingPacket(@NotNull final Network network,@NotNull final Packet packet, @NotNull final PacketSource source, @NotNull final PacketSource destination)
	{
		destination.getIncomingPacketListeners().visitAll(new SideEffect<IncomingPacketListener>()
		{
			public void run(final IncomingPacketListener listener)
			{
				pendingRequests.add(new Runnable()
				{
					public void run()
					{
						listener.packetIncoming(packet, source, destination).run(network);

						pendingRequests.remove(this);

						handleIfEmpty();
					}
				});
			}
		});
	}

	void handleIfEmpty()
	{
		if (pendingRequests.isEmpty())
		{
			final Collection<Runnable> temp=Collections.hashSet();

			for (final Runnable runnable : emptyQueueListeners)
				temp.add(runnable);

			for (final Runnable runnable : temp)
			{
				emptyQueueListeners.remove(runnable);
				runnable.run();
			}
		}
	}

	public void addEmptyQueueListener(final Runnable runnable)
	{
		emptyQueueListeners.add(runnable);
	}

	public void processed(final Runnable runnable)
	{
		pendingRequests.remove(runnable);
	}

	public void processAll()
	{
		while (!pendingRequests.isEmpty())
			pendingRequests.remove().run();

		while (!emptyQueueListeners.isEmpty())
			emptyQueueListeners.remove(0).run();
	}
}