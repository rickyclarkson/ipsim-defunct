package ipsim.gui;

import ipsim.property.*;
import ipsim.network.*;
import ipsim.network.connectivity.hub.*;

public class Props
{
    /*	public static void setProperty(final Network network,final NetworkView view,final ModelObject object, final PropertyB property, final boolean value)
	{
		property.set(value);

		object.accept(new ModelVisitor()
		{
			public void visit(Hub hub)
			{
                            network.modified_$eq(true);
                            network.log() = network.log().add((value ? "En" : "Dis")+"abled power on "+PacketSourceUtility.asString(network, hub)+'.');
				if (view!=null)
					view.repaint();
			}
		});
                }*/
}