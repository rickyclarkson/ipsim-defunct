package ipsim.swing;

import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;

//TODO make anonymous
public final class ValidatingDocumentListener implements DocumentListener
{
	private final Color invalid;
	private final Color valid;
	private final Component parentComponent;
	private final DocumentValidator validator;

	public ValidatingDocumentListener(final Component parentComponent,final Color valid,final Color invalid,final DocumentValidator validator)
	{
		this.parentComponent=parentComponent;
		this.valid=valid;
		this.invalid=invalid;
		this.validator=validator;
	}

	public void insertUpdate(final DocumentEvent event)
	{
		changedUpdate(event);
	}

	public void removeUpdate(final DocumentEvent event)
	{
		changedUpdate(event);
	}

	public void changedUpdate(final DocumentEvent event)
	{
		if (isValid(event.getDocument()) || 0==event.getDocument().getLength())
			parentComponent.setBackground(valid);
		else
			parentComponent.setBackground(invalid);
	}

	public boolean isValid(final Document document)
	{
		return validator.isValid(document);
	}
}