package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.utils.GenListener;

import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

abstract public class BaseView extends JFrame {
	final static public <T extends EventObject> void fireList(List<GenListener<T>> list, T e) {
		Iterator<GenListener<T>> itr = list.iterator();
		GenListener<T> listener;
		while(itr.hasNext()) {
			listener = itr.next();
			listener.fireEvent(e);
		}
	}
}