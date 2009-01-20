package ipsim.network.breakage;
/*
import fpeas.pair.*;
import fpeas.predicate.*;
import fpeas.sideeffect.*;
import static fpeas.sideeffect.SideEffectUtility.*;
import ipsim.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.gui.*;
import static ipsim.gui.UserPermissions.*;
import static ipsim.lang.FunctionOOO.*;
import ipsim.lang.*;
import static ipsim.lang.Predicates.*;
import ipsim.network.*;
import static ipsim.network.Network$.*;
import static ipsim.network.PositionUtility.*;
import static ipsim.network.conformance.ConformanceTestsUtility.*;
import ipsim.network.connectivity.*;
import ipsim.network.connectivity.cable.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.hub.*;
import ipsim.network.connectivity.ip.*;
import ipsim.network.ethernet.*;
import static ipsim.network.IPAddress.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import javax.swing.ProgressMonitor;
import java.util.*;

public class BreakNetwork
{
	public static void breakNetwork(final Random random,final JFrame frame, final Network network, final NetworkView view)
	{
		if (!network.userPermissions.allowBreakingNetwork())
		{
			NetworkContext.errors(frame, "You are not allowed to break the network during a test");
			return;
		}

		if (network.modified)
		{
			if (!confirm(frame, "The network has been modified.  Continue anyway?"))
				return;
		}

		final int numberOfFaultsVar=askUserForNumberOfFaults(frame);

		final ProgressMonitor[] monitor=new ProgressMonitor[]{new ProgressMonitor(frame,
				"Breaking network                  ",
				"Breaking network                    ",
				0,
				100
		)};

		monitor[0].setMillisToPopup(0);
		monitor[0].setMillisToDecideToPopup(0);
		monitor[0].setProgress(1);

		new SwingWorker<Void, Object>()
		{
			@Override
			public Void doInBackground()
			{
				doIt();
				return null;
			}

			private void doIt()
			{
				view.ignorePaints.let(true, new Runnable()
				{
					public void run()
					{


						monitor[0].setNote("Testing connectivity");

						final ConnectivityResults results=ConnectivityTest.testConnectivity(network,
								new SideEffect<String>()
								{
									public void run(final String s)
									{
										monitor[0].setNote(s);
									}
								},
								new SideEffect<Integer>()
								{
									public void run(final Integer integer)
									{
										monitor[0].setProgress(integer);
									}
								}
						);

						if (results.getPercentConnected()!=100)
						{
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									JOptionPane.showMessageDialog(frame,
											"The network must be 100% connected before it can be broken",
											"Error",
											JOptionPane.ERROR_MESSAGE
									);
								}
							}
							);
							view.ignorePaints.set(false);
							monitor[0].close();
							return;
						}

						final String savedNetwork=NetworkUtility.saveToString(network);

						network.log.add("Breaking network");

						monitor[0].close();

						monitor[0]=new ProgressMonitor(frame,
								"Breaking network                  ",
								"Breaking network                    ",
								0,
								100
						);

						monitor[0].setMillisToPopup(0);
						monitor[0].setMillisToDecideToPopup(0);
						monitor[0].setProgress(1);

						monitor[0].setNote("Trying to break the network");
						monitor[0].setProgress(0);

						int totalFailures=0;
						for (int a=0;a<numberOfFaultsVar && totalFailures<50;a++)
						{
							monitor[0].setNote("Breaking network (try "+(totalFailures+1)+"/50)");
							final SideEffect<String> noLog=doNothing();
							final SideEffect<Integer> noProgress=doNothing();
							final int connectivityBefore=ConnectivityTest.testConnectivity(network, noLog, noProgress
							).getPercentConnected();

							oneRandomBreakage(random, network);

							final int connectivityAfter=ConnectivityTest.testConnectivity(network, noLog, noProgress).getPercentConnected();

							if (connectivityAfter>=connectivityBefore)
							{
								a=-1;
								totalFailures++;

								NetworkUtility.loadFromString(network, savedNetwork);
							}

							monitor[0].setProgress(100*a/numberOfFaultsVar);
						}

						if (totalFailures>49)
							JOptionPane.showMessageDialog(frame,
									"Failed to break network - try a larger network or less faults",
									"Error",
									JOptionPane.ERROR_MESSAGE
							);
                                                else
                                                    { UserMessages.message("Network broken, with "+numberOfFaultsVar+" faults");
                                                    network.userPermissions=FREEFORM_WITH_BREAKS; }

						network.log.clear();
						monitor[0].close();
					}
				});
			}
		}.execute();
	}

	private static void oneRandomBreakage(final Random random, final Network network)
	{
		randomOneOf(random,turnAHubOff(random, network),
				turnPacketForwardingOffOnARouter(random, network),
				changeCableType(random, network),
				disconnectCable(random, network),
				swapIPsOnARouter(random, network),
				changeIP(random, network),
				changeNetMask(random, network),
				changeRoute(random, network),
				deleteRoute(random, network)
		);
	}

	private static Runnable deleteRoute(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final Computer computer=randomOneOf(random, getAllComputers(network));

				if (computer==null)
					return;

				final RoutingTable table=computer.routingTable;
				final Route route=randomOneOf(random, table.routes());

				if (route==null)
					return;

				table.remove(route);
			}
		};
	}

	private static Runnable changeRoute(final Random random, final Network context)
	{
		return new Runnable()
		{
			public void run()
			{
				final Computer computer=randomOneOf(random, getAllComputers(context));
				if (computer==null)
					return;

				final RoutingTable table=computer.routingTable;
				final Route route=randomOneOf(random, table.routes());

				if (route==null)
					return;

				computer.routingTable.replace(route, breakRoute(random,route));
			}
		};
	}

	private static Route breakRoute(Random random,@NotNull final Route route)
	{
		if (random.nextInt(2)==0)
			return new Route(route.block, IPAddressUtility.randomIP(random));

		if (random.nextInt(2)==0)
                    return new Route(new NetBlock(route.block.networkNumber(), NetMaskUtility.randomNetMask(random)), route.gateway);

		return new Route(new NetBlock(IPAddressUtility.randomIP(random), route.block.netMask()), route.gateway);
	}

	private static Runnable changeNetMask(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final CardDrivers card=randomOneOf(random, getAllCardsWithDrivers(network));

				if (card==null)
					return;

				card.netMask.set(NetMaskUtility.randomNetMask(random));
			}
		};
	}

	private static Runnable changeIP(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final CardDrivers card=randomOneOf(random, getAllCardsWithDrivers(network));

				if (card==null)
					return;

				card.ipAddress.set(IPAddressUtility.randomIP(random));
			}
		};
	}

	private static Runnable swapIPsOnARouter(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final Computer computer=randomOneOf(random,
						getAllComputers(network, fromFunction(partApply(isARouter(), network)))
				);

				if (computer==null)
					return;

				final Stream<CardDrivers> cards=ComputerUtility.cardsWithDrivers(network, computer);

				final Pair<CardDrivers, CardDrivers> pair=randomTwoOf(random, cards);

				if (pair==null)
					return;

				final IPAddress tmp=pair.first().ipAddress.get();
				pair.first().ipAddress.set(pair.second().ipAddress.get());
				pair.second().ipAddress.set(tmp);
			}
		};
	}

	private static <T> Pair<T, T> randomTwoOf(final Random random, final Stream<T> stream)
	{
		T one=randomOneOf(random, stream);
		for (int i=0;i<1000;i++)
		{
			T two=randomOneOf(random, stream);
			if (!one.equals(two))
				return PairUtility.pair(one, two);
		}

		return null;

	}

	private static Runnable disconnectCable(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final Predicate<Cable> connected=new Predicate<Cable>()
				{
					public boolean invoke(@NotNull final Cable cable)
					{
						return cable.canTransferPackets();
					}
				};

				final Cable cable=randomOneOf(random, NetworkUtility.getAllCables(network, connected));

				if (cable==null)
					return;

				final int end=random.nextInt(2);
				cable.setPositionData(end, PositionOrParent.position(getPosition(network, cable, end)));
			}
		};
	}

	private static Runnable changeCableType(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final Cable cable=randomOneOf(random, NetworkUtility.getAllCables(network));

				if (cable==null)
					return;

				cable.setCableType(cable.getCableType().another(random));
			}
		};
	}

	private static Runnable turnPacketForwardingOffOnARouter(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final Computer computer=randomOneOf(random, getAllComputers(network));

				if (computer==null)
					return;

				if (isARouter().invoke(network, computer))
					computer.ipForwardingEnabled=false;
			}
		};
	}

	private static Runnable turnAHubOff(final Random random, final Network network)
	{
		return new Runnable()
		{
			public void run()
			{
				final Hub hub=randomOneOf(random, getAllHubs(network));

				if (hub==null)
					return;

				if (hub.isPowerOn())
					hub.setPower(false);
			}
		};
	}

	private static void randomOneOf(Random random,final Runnable... runnables)
	{
		runnables[random.nextInt(runnables.length)].run();
	}

	public static <T> T randomOneOf(final Random random, final Stream<T> stream)
	{
		final int[] n={0};
		final List<T> result=arrayList();
		result.add(null);

		stream.foreach(new SideEffect<T>()
		{
			public void run(final T t)
			{
				if (random.nextInt(n[0]+1)==0)
					result.set(0, t);
			}
		}
		);

		return result.get(0);
	}
}
*/