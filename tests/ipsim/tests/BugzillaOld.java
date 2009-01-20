package ipsim.tests;
/*
import ipsim.network.Problem;
import ipsim.network.NetBlock;
import fpeas.lazy.Lazy;
public class Bugzilla
{
	public static final Lazy<Boolean> bug18=new Lazy<Boolean>()
	{
		public Boolean invoke()
		{
			final String[] nets={"192.168.1.0/24","192.168.0.0/17","192.168.0.0/15","192.128.0.0/10","192.0.0.0/8"};
			for (final String net: nets)
			{
                            final NetBlock block=NetBlockUtility.valueOf(net).get();
				if (!ProblemUtility.isReservedNetworkNumber(block))
					return false;
			}

			final String[] allowed={"192.128.0.0/11"};
			for (final String net: allowed)
			{
                            final NetBlock block=NetBlockUtility.valueOf(net).get();
				if (ProblemUtility.isReservedNetworkNumber(block))
					return false;
			}

			return true;
		}

		@Override
		public String toString()
		{
			return "Bugzilla bug 18";
		}
	};
}
*/