package view.misc;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

import javax.swing.JDialog;

public class TabControl extends FocusTraversalPolicy {
	private Vector<Component> order;

	public TabControl(Vector<Component> iOrder) {
		order = new Vector<Component>(iOrder.size());
		order.addAll(iOrder);
	}
	
	public TabControl(Component... iInit) {
		order = new Vector<Component>(1);
		for(Component i : iInit) order.add(i);
	}
	
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
		if(aComponent instanceof JDialog) aComponent = ((JDialog)aComponent).getFocusOwner();
		int idx = (order.indexOf(aComponent) + 1) % order.size();
		return order.get(idx);
	}

	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
		if(aComponent instanceof JDialog) aComponent = ((JDialog)aComponent).getFocusOwner();
		int idx = order.indexOf(aComponent) - 1;
		if (idx < 0) idx = order.size() - 1;
		return order.get(idx);
	}

	public Component getDefaultComponent(Container focusCycleRoot) {
		return order.size() == 0 ? null : order.get(0);
	}

	public Component getLastComponent(Container focusCycleRoot) {
		return order.lastElement();
	}

	public Component getFirstComponent(Container focusCycleRoot) {
		return order.get(0);
	}
}
