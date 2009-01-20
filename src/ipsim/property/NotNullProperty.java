package ipsim.property;

import org.jetbrains.annotations.*;

public interface NotNullProperty<T>
{
	void set(@NotNull T t);
	@NotNull T get();
}
