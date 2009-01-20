package ipsim.tree;
/*
import fpeas.function.*;
import ipsim.network.*;
import ipsim.network.connectivity.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

public final class Trees
{
	public static <T> Iterable<T> getDepthFirstIterable(final Stream<TreeNode<T>> roots)
	{
		return new DepthFirstIterable<T>(roots);
	}

	private static final class NetworkNode implements TreeNode<PacketSource>
	{
		private final PacketSource component;
		private final Network network;

		NetworkNode(final PacketSource component, Network network)
		{
			this.component=component;
			this.network=network;
		}

		public PacketSource getValue()
		{
			return component;
		}

		@Override
		public String toString()
		{
			return "NetworkNode["+PacketSourceUtility.asString(network, component)+']';
		}

		public Stream<TreeNode<PacketSource>> getChildNodes()
		{
			final Stream<PacketSource> children=PositionUtility.children(network.getAll(), component);
			return nodify(network, children);
		}
	}

	public static <T extends PacketSource> Stream<TreeNode<PacketSource>> nodify(final Network network, final Stream<T> components)
	{
		return components.map(new Function<T, TreeNode<PacketSource>>()
		{
			@NotNull
			public TreeNode<PacketSource> run(@NotNull final PacketSource packetSource)
			{
				return new NetworkNode(packetSource,network);
			}
		});
	}
        }*/