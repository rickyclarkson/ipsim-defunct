package ipsim.tree;

import fpeas.sideeffect.*;
import ipsim.util.Collections;
import ipsim.util.Stack;
import ipsim.util.*;

import java.util.*;

/**
	The root element is processed first.
*/
final class DepthFirstIterator<T> implements Iterator<T>
{
	private final Stack<TreeNode<T>> stack=Collections.stack();

	DepthFirstIterator(final Stream<TreeNode<T>> root)
	{
		StackUtility.pushAll(stack,root);
	}

	public boolean hasNext()
	{
		return !stack.isEmpty();
	}

	public T next()
	{
		if (stack.isEmpty())
			throw new NoSuchElementException();

		final TreeNode<T> node=stack.pop();

		node.getChildNodes().foreach(new SideEffect<TreeNode<T>>()
		{
			public void run(final TreeNode<T> treeNode)
			{
				stack.push(treeNode);
			}
		});

		return node.getValue();
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}