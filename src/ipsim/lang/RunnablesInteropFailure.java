package ipsim.lang;

public class RunnablesInteropFailure
{
	public static final Runnable throwRuntimeException=new Runnable()
	{
		public void run()
		{
			throw new RuntimeException();
		}
	};

	public static final Runnable nothing=new Runnable()
	{
		public void run()
		{
		}
	};

}