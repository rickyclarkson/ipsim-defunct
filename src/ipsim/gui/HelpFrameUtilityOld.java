package ipsim.gui;
/*
import com.rickyclarkson.javax.swing.*;
import fpeas.either.*;
import fpeas.function.*;
import static fpeas.function.FunctionUtility.*;
import fpeas.sideeffect.*;
import static ipsim.lang.Runnables.*;
import static ipsim.swing.Buttons.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public final class HelpFrameUtility
{
	public static final URL helpRoot=HelpFrameUtility.class.getResource("/help/index.html");

	public static JFrame createHelpFrame()
	{
		final JFrame frame=new JFrame();
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,600);
		frame.setLocationRelativeTo(null);
		frame.setTitle("IPSim Help");
		frame.setLayout(new BorderLayout());
		final JScrollPane pane=new JScrollPane();

		final Either<JEditorPane,IOException> htmlPane=ScrollableEditorPaneUtility.createScrollableEditorPane(pane);

		final Function<IOException,Runnable> doNothing=constant(nothing);

		htmlPane.visit(new Function<JEditorPane,Runnable>()
		{
			@NotNull
			public Runnable run(@NotNull final JEditorPane pane2)
			{
				return new Runnable()
				{
					public void run()
					{
						pane2.setEditable(false);
						pane2.setCaretPosition(0);
						final Hyperactive hyperactive=new Hyperactive(pane2);
						pane2.addHyperlinkListener(hyperactive);
						pane2.setAutoscrolls(true);
						pane.getViewport().add(pane2);

						frame.add(pane,BorderLayout.CENTER);

						doStuff(hyperactive, frame);
					}
				};
			}
		},doNothing).run();

		return frame;
	}

	private static void doStuff(final Hyperactive hyperactive, final Container parent)
	{
		final JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT));

		panel.add(newButton("Contents",new Runnable()
		{
			public void run()
			{
				hyperactive.goHome();
			}
		}));

		panel.add(newButton("Back",new Runnable()
		{
			public void run()
			{
				hyperactive.back();
			}
		}));

		parent.add(panel,BorderLayout.NORTH);
	}

	public static final SideEffect<Container> createPane=new SideEffect<Container>()
	{
		public void run(final Container parent)
		{
			parent.setLayout(new BorderLayout());
			final JScrollPane pane=new JScrollPane();

			final Either<JEditorPane,IOException> htmlPane=ScrollableEditorPaneUtility.createScrollableEditorPane(pane);

			final Function<IOException,Runnable> doNothing=constant(nothing);

			htmlPane.visit(new Function<JEditorPane,Runnable>()
			{
				@NotNull
				public Runnable run(@NotNull final JEditorPane pane2)
				{
					return new Runnable()
					{
						public void run()
						{
							pane2.setEditable(false);
							pane2.setCaretPosition(0);
							final Hyperactive hyperactive=new Hyperactive(pane2);
							pane2.addHyperlinkListener(hyperactive);
							pane2.setAutoscrolls(true);
							pane.getViewport().add(pane2);

							parent.add(pane,BorderLayout.CENTER);

							doStuff(hyperactive,parent);
						}
					};
				}
			},doNothing).run();
		}
	};
        }*/