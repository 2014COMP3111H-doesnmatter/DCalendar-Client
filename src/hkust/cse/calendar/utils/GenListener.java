package hkust.cse.calendar.utils;

import java.util.EventObject;

abstract public interface GenListener<T extends EventObject> {
	void fireEvent(T e);
}
