package ipsim.util;

public interface Stack<T>
{
	void push(T object);

	boolean isEmpty();

	T pop();
}