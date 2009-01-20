package ipsim.property;

import org.jetbrains.annotations.*;

public interface Property2<T>
{
	void set(@Nullable T t);
	@Nullable T get();
}