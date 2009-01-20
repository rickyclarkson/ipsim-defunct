package ipsim.tree;

import ipsim.util.*;

import java.util.*;

final class DepthFirstIterable<T> implements Iterable<T>
{
	private final Stream<TreeNode<T>> roots;

	DepthFirstIterable(final Stream<TreeNode<T>> roots)
	{
		this.roots=roots;
	}

	public Iterator<T> iterator()
	{
		return new DepthFirstIterator<T>(roots);
	}
}