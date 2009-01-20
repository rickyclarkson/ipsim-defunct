package ipsim.property;

public class PropertyB
{
	private boolean value;

	public PropertyB(final boolean initial)
	{
		value=initial;
	}

	public boolean get()
	{
		return value;
	}

	public void set(boolean value)
	{
		this.value=value;
	}
}