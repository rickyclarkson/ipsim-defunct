package ipsim.network;
/*
import com.rickyclarkson.testsuite.*;
import static fpeas.either.EitherUtility.*;
import fpeas.lazy.Lazy;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.network.ip.*;

public class ProblemTest
{
	public static Lazy<Boolean> instance()
	{
		return new Lazy<Boolean>()
		{
			public Boolean invoke()
			{
				return test1()&&testInvalidNetMaskRejection();
			}

			private boolean testInvalidNetMaskRejection()
			{
				return invalidNetMask("255.255.22.0")&&!invalidNetMask("255.0.0.0");
			}

			private boolean invalidNetMask(final String mask)
			{
                            return NetMask.valueOf(mask).isEmpty();
			}

			public boolean test1()
			{
				final IPAddress address=new IPAddress(221<<24);
				final NetMask mask=netMask(255<<24).get();

				final NetBlock netBlock=new NetBlock(address, mask);

				return netBlock.networkNumber().equals(IPAddressUtility.valueOf("221.0.0.0").get());
			}

			@Override
			public String toString()
			{
				return "ProblemTest";
			}
		};
	}
}
*/