package ipsim.io;

import java.io.*;

public abstract class WithResource<T extends Closeable>
{
	public abstract T open() throws IOException;
	public abstract void work(T t) throws IOException;

	public WithResource()
	{
		T t=null;

		try
		{
			t=open();
			work(t);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if (t!=null)
				try
				{
					t.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
	}
}