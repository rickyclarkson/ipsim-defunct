package ipsim.awt;

import java.awt.event.*;
import java.awt.Component;

import fpeas.sideeffect.SideEffect;

public class ActionListeners
{
    public static ActionListener fromSideEffect(final SideEffect<Component> effect,final Component c)
    {
        return new ActionListener() { public void actionPerformed(ActionEvent e) { effect.run(c); } };
    }
}
