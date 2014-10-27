package hkust.cse.calendar.utils;

import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

abstract public class EventSource {
	final static public <T extends EventObject> void fireList(final List<GenListener<T>> list, final T e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Iterator<GenListener<T>> itr = list.iterator();
				GenListener<T> listener;
				while(itr.hasNext()) {
					listener = itr.next();
					listener.fireEvent(e);
				}
			}
		});
	}
	
	final static public <T extends EventObject> void fireTo(final GenListener<T> listener, final T e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				listener.fireEvent(e);
			}
		});
	}
}