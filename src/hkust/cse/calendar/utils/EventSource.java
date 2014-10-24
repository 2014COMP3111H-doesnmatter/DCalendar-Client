package hkust.cse.calendar.utils;

import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

abstract public class EventSource {
	final static public <T extends EventObject> void fireList(List<GenListener<T>> list, T e) {
		Iterator<GenListener<T>> itr = list.iterator();
		GenListener<T> listener;
		while(itr.hasNext()) {
			listener = itr.next();
			listener.fireEvent(e);
		}
	}
}