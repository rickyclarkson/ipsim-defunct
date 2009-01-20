package ipsim.network;

import fpeas.either.*;
import fpeas.function.*;
import fpeas.predicate.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import ipsim.*;
import org.jetbrains.annotations.*;

import java.util.*;

/*class ProblemDifficultyOld
{
	public static final Function<Random,Problem> EASY=new Function<Random,Problem>()
	{
		@NotNull
		public Problem run(@NotNull Random random)
		{
			for (int a=0;a<1000;a++)
			{
				int randomNumber=random.nextInt(65536);
				randomNumber<<=16;

				final IPAddress address=new IPAddress(randomNumber);
				final NetBlock block=new NetBlock(address, NetMaskUtility.fromPrefixLength(16).get());
                                return new Problem(block,3);

			}

			throw null;
		}
	},
	MEDIUM=new Function<Random, Problem>()
	{
		@NotNull
		public Problem run(@NotNull final Random random)
		{
			return generate(random,17,4,4);
		}
	},
	HARD=new Function<Random, Problem>()
	{
		@NotNull
		public Problem run(@NotNull final Random random)
		{
			return generate(random,22,2,5);
		}
	};

	public static final Predicate<Random> testGeneration=new Predicate<Random>()
	{
		public boolean invoke(Random random)
		{
			EASY.run(random);
			MEDIUM.run(random);
			HARD.run(random);
			return true;
		}

		@Override
		public String toString()
		{
			return "ProblemDifficulty.testGeneration";
		}
	};

	private static Problem generate(Random random,final int randomStart, final int randomRange, final int numSubnets)
	{
			final ProblemBuilder builder=new ProblemBuilder();

			for (int a=0;a<100;a++)
			{
				final int random1=random.nextInt(65536);
				final int random2=random.nextInt(65536);
				final int random3=random1<<16+random2;

				final int netmaskLength=random.nextInt(randomRange)+randomStart;
				final NetMask netmask=NetMaskUtility.fromPrefixLength(netmaskLength).get();
				final int rawNetworkNumber=random3&netmask.rawValue();

				if (!(0==(rawNetworkNumber&0xFF00)))
				{
					final IPAddress networkNumber=new IPAddress(rawNetworkNumber);

					final NetBlock netBlock=new NetBlock(networkNumber, netmask);
					if (ProblemUtility.isValidNetworkNumber(netBlock.networkNumber())&&!ProblemUtility.isReservedNetworkNumber(netBlock))
					{
						final Stage2 stage2=EitherUtility.unsafeLeft(builder.withSubnets(numSubnets));
						return EitherUtility.unsafeLeft(stage2.withNetBlock(netBlock));
					}
				}
			}

			throw null;
	}

	public static Function<Random, Problem> valueOf(final String s)
	{
		return Caster.asNotNull(s.equalsIgnoreCase("EASY") ? EASY : s.equalsIgnoreCase("MEDIUM") ? MEDIUM : s.equalsIgnoreCase("HARD") ? HARD : null);
	}
        }*/