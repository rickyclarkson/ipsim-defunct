package ipsim.gui.components;
/*
import anylayout.*;
import anylayout.extras.*;
import fpeas.either.*;
import static fpeas.either.EitherUtility.*;
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import fpeas.maybe.*;
import static fpeas.maybe.MaybeUtility.*;
import fpeas.sequence.*;
import static fpeas.sequence.SequenceUtility.*;
import fpeas.sideeffect.*;
import static ipsim.network.NetworkContext$.*;
import ipsim.awt.*;
import ipsim.lang.Runnables;
import static ipsim.lang.Stringables.*;
import ipsim.network.*;
import ipsim.network.connectivity.card.*;
import ipsim.network.connectivity.computer.*;
import ipsim.network.connectivity.ip.*;
import static ipsim.network.Computer$.*;
import static ipsim.network.route.Route$.*;
import ipsim.network.ip.*;
import static ipsim.swing.Dialogs.*;

import javax.swing.*;
import java.awt.event.*;

public class RoutingTableDialogUtility
{
	public static RoutingTableDialog createRoutingTableDialog(final JFrame frame,final Network network,final Computer computer)
	{
            final JDialog dialog=createDialogWithEscapeKeyToClose("Routing Table", frame);

		final JList entries=new JList();

		final JButton editButton=new JButton("Edit...");

		final RoutingTableDialog routingTableDialog=new RoutingTableDialog()
		{
			public final RoutingTableDialog thiss=this;

			public Maybe<Node<Either<String, Route>>> list=empty();

			public Runnable populateElements()
			{
				return new Runnable()
				{
					public void run()
					{
						doStuff();
					}
				};
			}

			public void doStuff()
			{
				final RoutingTable routingTable=computer.routingTable;

				final Iterable<Route> allRoutes=routingTable.routes();

				final Iterable<CardDrivers> cards=getSortedCards(network,computer);

				list=empty();

				for (final CardDrivers cardWithDrivers : cards)
				{
                                    if (!(0==cardWithDrivers.ipAddress.get().rawValue()))
					{
                                            final IPAddress destination=new IPAddress(cardWithDrivers.netMask.get().rawValue()&cardWithDrivers.ipAddress.get().rawValue());

						final NetMask netmask=cardWithDrivers.netMask.get();

						final StringBuilder buffer=new StringBuilder();

						buffer.append("Destination: ");

						buffer.append(destination);

						buffer.append(" netmask ");

						buffer.append(netmask);

						buffer.append(" Gateway: *");

						final Either<String, Route> either=EitherUtility.left(buffer.toString());
						list=cons(either, list);
					}
				}

				for (final Route entry : allRoutes)
				{
					final Either<String, Route> either=EitherUtility.right(entry);
					list=cons(either, list);
				}

				list=reverse(list);
				final String[] array=new String[size(list)];

				final Function<String, String> identity=identity();

				final int[] a={0};

				SequenceUtility.forEach(list, new SideEffect<Either<String, Route>>()
				{
					public void run(final Either<String, Route> either)
					{
						final Function<Route, String> asString=asString();
						array[a[0]]=either.visit(identity, asString);
						a[0]++;
					}
				});

				entries.setListData(array);

				dialog.invalidate();
				dialog.validate();
				dialog.repaint();
			}

			public void editButtonClicked()
			{
				final int index=entries.getSelectedIndex();

				if (-1==index)
				{
					noEntrySelected();
					return;
				}

				MaybeUtility.run(SequenceUtility.get(list, entries.getSelectedIndex()), new SideEffect<Either<String, Route>>()
				{
					public void run(final Either<String, Route> either)
					{
						EitherUtility.runRight(either, new SideEffect<Route>()
						{
							public void run(final Route entry)
							{
								final RouteInfo entryInfo=new RouteInfo(entry.block, entry.gateway);

								final JDialog editDialog=RoutingTableEntryEditDialog.createRoutingTableEntryEditDialog(frame,network,computer, entryInfo, just(entry), just(thiss)).getDialog();

								editDialog.setVisible(true);
							}
						});
					}
				});
			}

			public void deleteButtonClicked()
			{
				final int index=entries.getSelectedIndex();

				if (-1==index)
				{
					noEntrySelected();
					return;
				}

				MaybeUtility.run(SequenceUtility.get(list, entries.getSelectedIndex()), new SideEffect<Either<String, Route>>()
				{
					public void run(final Either<String, Route> either)
					{
						runRight(either, new SideEffect<Route>()
						{
							public void run(final Route route)
							{
								final String previous=asCustomString(route);

								computer.routingTable.remove(route);
								network.log.add("Deleted a route ("+previous+") from "+PacketSourceUtility.asString(network, computer));
							}
						});
					}
				});

				populateElements();
			}

			public void noEntrySelected()
			{
				errors(frame,"Select an item before clicking on Edit or Delete");
			}

			public JDialog getJDialog()
			{
				return dialog;
			}

		};

		dialog.setSize(600, 400);

		routingTableDialog.populateElements().run();

		ComponentUtility.centreOnParent(dialog, frame);

		final PercentConstraints constraints=PercentConstraintsUtility.newInstance(dialog.getContentPane());
		AnyLayout.useAnyLayout(dialog.getContentPane(), 0.5f, 0.5f, constraints.getSizeCalculator(), ConstraintUtility.typicalDefaultConstraint(Runnables.throwRuntimeException));

		constraints.add(new JLabel("Routing Table"), 5, 5, 30, 5, false, false);
		constraints.add(entries, 5, 15, 90, 60, true, true);

		constraints.add(editButton, 10, 85, 20, 10, false, false);

		editButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				routingTableDialog.editButtonClicked();
			}
		});

		final JButton deleteButton=new JButton("Delete");
		constraints.add(deleteButton, 40, 85, 20, 10, false, false);

		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				routingTableDialog.deleteButtonClicked();
			}
		});

		final JButton closeButton=new JButton("Close");
		constraints.add(closeButton, 70, 85, 20, 10, false, false);

		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent event)
			{
				dialog.setVisible(false);
				dialog.dispose();
			}
		});

		return routingTableDialog;
	}

	public static final SideEffect<RoutingTableDialog> populateElements=new SideEffect<RoutingTableDialog>()
	{
		public void run(final RoutingTableDialog routingTableDialog)
		{
			routingTableDialog.populateElements();
		}
	};
}*/