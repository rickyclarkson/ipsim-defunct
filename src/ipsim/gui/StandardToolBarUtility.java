package ipsim.gui;

import ipsim.*;
import static ipsim.gui.Global.*;
import scala.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//todo make the components shrink to fit the space
public class StandardToolBarUtility
{
	public static Container createStandardToolBar(final Random random)
	{
		final JPanel tinyIcons=new JPanel(new GridLayout(1, 7));

		class CustomFunction
		{
			public void createTinyIcon(final String imageLocation, final Runnable runnable, final String text)
			{
				final ActionListener listener=new ActionListener()
				{
					public void actionPerformed(final ActionEvent e)
					{
						runnable.run();
					}
				};

				final JButton button=new JButton("<html><center>"+text+"</center></html>");
				if (!"".equals(imageLocation))
					button.setIcon(new ImageIcon(StandardToolBarUtility.class.getResource(imageLocation)));

				button.setHorizontalTextPosition(SwingConstants.CENTER);
				button.setVerticalTextPosition(SwingConstants.BOTTOM);

				button.setOpaque(true);
				button.addActionListener(listener);

				tinyIcons.add(button);
			}
		}

		final CustomFunction createTinyIcon=new CustomFunction();

                Runnable networkNewRef = new Runnable() { public void run() { MenuHandler.networkNew(random); } };
		createTinyIcon.createTinyIcon("/images/file_new.png", networkNewRef, "New");

                Runnable fileOpenRef = new Runnable() { public void run() { MenuHandler.fileOpen(random); } };
		createTinyIcon.createTinyIcon("/images/file_open.png", fileOpenRef, "Open");

                Runnable fileSaveRef = new Runnable() { public void run() { MenuHandler.fileSave(); } };
		createTinyIcon.createTinyIcon("/images/file_save.png", fileSaveRef, "Save");

                Runnable zoomInRef = new Runnable() { public void run() { MenuHandler.zoomIn(); } };
		createTinyIcon.createTinyIcon("/images/view_zoom_in.png", zoomInRef, "Zoom In");

                Runnable zoomOutRef = new Runnable() { public void run() { MenuHandler.zoomOut(); } };
		createTinyIcon.createTinyIcon("/images/view_zoom_out.png", zoomOutRef, "Zoom Out");

                Runnable zoomToFitRef = new Runnable() { public void run() { MenuHandler.zoomToFit(); } };
		createTinyIcon.createTinyIcon("/images/view_zoom_auto.png", zoomToFitRef, "Zoom Auto");

		createTinyIcon.createTinyIcon("", new Runnable()
		{
			public void run()
			{
                            if (!network().userPermissions().allowDownloadingNewConfig())
				{
                                    UserMessages.error("Cannot download a new configuration during a test!");
                                    return;
				}

                            if (network().modified() && !MenuHandler.networkModifiedDialog())
					return;

				MenuHandler.downloadConfiguration();
			}
		}, "<html>Download<br>Config</html>");

		final JPanel tinyPanel=new JPanel(new BorderLayout());
		tinyPanel.add(tinyIcons, BorderLayout.WEST);

		return tinyPanel;
	}
}
