package ipsim.tree;

import ipsim.lang.*;
import ipsim.util.*;

public interface TreeNode<T> extends Stringable
{
	Stream<TreeNode<T>> getChildNodes();

	T getValue();
}