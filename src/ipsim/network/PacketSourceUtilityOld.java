package ipsim.network;
/*
import fpeas.function.*;
import fpeas.predicate.*;
import static ipsim.Caster.*;
import static ipsim.gui.components.NetworkComponentUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import org.jetbrains.annotations.*;

public final class PacketSourceUtility
{
	public static final Predicate<PacketSource> isHub=new Predicate<PacketSource>()
	{
		public boolean invoke(final PacketSource source)
		{
			return source.accept(new PacketSourceVisitor<Boolean>()
			{
				public Boolean visit(final Card card)
				{
					return false;
				}

				public Boolean visit(final Computer computer)
				{
					return false;
				}

				public Boolean visit(final Cable cable)
				{
					return false;
				}

				public Boolean visit(final Hub hub)
				{
					return true;
				}
			});
		}
	};

	public static final Predicate<PacketSource> isCableRef=new Predicate<PacketSource>()
	{
		public boolean invoke(final PacketSource packetSource)
		{
			return isCable(packetSource);
		}
	};

	public static final Predicate<PacketSource> isCardRef=new Predicate<PacketSource>()
	{
		public boolean invoke(final PacketSource component1)
		{
			return isCard(component1);
		}
	};

	public static final Function<PacketSource, Card> asCardRef=new Function<PacketSource, Card>()
	{
		@NotNull
		public Card run(@NotNull final PacketSource packetSource)
		{
			final Card card=asCard(packetSource);

			if (card==null)
				throw null;

			return card;
		}
	};

	public static final Function<PacketSource,Computer> asComputerRef=new Function<PacketSource, Computer>()
	{
		@NotNull
		public Computer run(@NotNull final PacketSource packetSource)
		{
			return asNotNull(asComputer(packetSource));
		}
	};

	public static final Predicate<PacketSource> isComputerRef=new Predicate<PacketSource>()
	{
		public boolean invoke(final PacketSource packetSource)
		{
			return isComputer(packetSource);
		}
	};
	public static Function<PacketSource, Hub> asHubRef=new Function<PacketSource, Hub>()
	{
		@NotNull
		public Hub run(@NotNull final PacketSource packetSource)
		{
			return asNotNull(asHub(packetSource));
		}
	};
	public static Function<PacketSource, Cable> asCableRef=new Function<PacketSource, Cable>()
	{
		@NotNull
		public Cable run(@NotNull final PacketSource packetSource)
		{
			return asNotNull(asCable(packetSource));
		}
	};

	public static boolean isCable(final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<Boolean>()
		{
			public Boolean visit(final Card card)
			{
				return false;
			}

			public Boolean visit(final Computer computer)
			{
				return false;
			}

			public Boolean visit(final Cable cable)
			{
				return true;
			}

			public Boolean visit(final Hub hub)
			{
				return false;
			}
		});
	}

	@Nullable
	public static Hub asHub(final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<Hub>()
		{
			public Hub visit(final Card card)
			{
				return null;
			}

			public Hub visit(final Computer computer)
			{
				return null;
			}

			public Hub visit(final Cable cable)
			{
				return null;
			}

			public Hub visit(final Hub hub)
			{
				return hub;
			}
		});
	}

	@Nullable
	public static Cable asCable(final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<Cable>()
		{
			public Cable visit(final Card card)
			{
				return null;
			}

			public Cable visit(final Computer computer)
			{
				return null;
			}

			public Cable visit(final Cable cable)
			{
				return cable;
			}

			public Cable visit(final Hub hub)
			{
				return null;
			}
		});
	}

	@Nullable
	public static Card asCard(@NotNull final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<Card>()
		{
			public Card visit(final Card card)
			{
				return card;
			}

			public Card visit(final Computer computer)
			{
				return null;
			}

			public Card visit(final Cable cable)
			{
				return null;
			}

			public Card visit(final Hub hub)
			{
				return null;
			}
		});
	}

	@Nullable
	public static Computer asComputer(final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<Computer>()
		{
			public Computer visit(final Card card)
			{
				return null;
			}

			public Computer visit(final Computer computer)
			{
				return computer;
			}

			public Computer visit(final Cable cable)
			{
				return null;
			}

			public Computer visit(final Hub hub)
			{
				return null;
			}
		});
	}

	public static boolean isCard(final PacketSource source)
	{
		return asCard(source)!=null;
	}

	public static boolean isComputer(final PacketSource source)
	{
		return asComputer(source)!=null;
	}

	public static String asString(final Network network,@NotNull final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<String>()
		{
			public String visit(final Card card)
			{
				final StringBuilder answer=new StringBuilder("an Ethernet card");

				@Nullable
				final PacketSource parent=source.positionData(0).parent;

				if (parent!=null)
				{
					if (card.withDrivers!=null)
					{
						final CardDrivers cardWithDrivers=card.withDrivers;
						answer.append(" (");
						answer.append(cardWithDrivers.ipAddress.get());
						answer.append('/');

						final int length=cardWithDrivers.netMask.get().prefixLength();
                                            answer.append(length==-1 ? cardWithDrivers.netMask.get().toString() : length);

						answer.append(')');
					}

					answer.append(" that is connected to ");

					answer.append(asString(network,parent));
				}
				else
				{
					answer.append(" (");

					answer.append(pointsToStringWithoutDelimiters(network,card));
					answer.append(')');
				}

				return answer.toString();
			}

			public String visit(final Computer computer)
			{
				final String ipAddresses=ComputerUtility.ipAddressesToString(network,computer);

				final String computerPlusID="computer "+computer.computerID;

				if (ipAddresses.length()==0)
					return computerPlusID+" ("+pointsToStringWithoutDelimiters(network, computer)+')';

				return computerPlusID+" ("+ipAddresses+')';
			}

			public String visit(final Cable cable)
			{
				return "Ethernet cable number "+network.cableIDFor.run(cable)+' '+pointsToStringWithoutDelimiters(network, cable);
			}

			public String visit(final Hub hub)
			{
				return "Hub number "+network.hubIDFor.run(hub)+" ("+pointsToStringWithoutDelimiters(network, hub)+')';
			}
		});
	}

	public static boolean isSpecialCard(final PacketSource source)
	{
		return source.accept(new PacketSourceVisitor<Boolean>()
		{
			public Boolean visit(final Card card)
			{
				return card.special;
			}

			public Boolean visit(final Computer computer)
			{
				return false;
			}

			public Boolean visit(final Cable cable)
			{
				return false;
			}

			public Boolean visit(final Hub hub)
			{
				return false;
			}
		});
	}
}*/