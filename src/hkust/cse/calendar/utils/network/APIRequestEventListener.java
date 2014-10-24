package hkust.cse.calendar.utils.network;

import java.util.EventListener;


public interface APIRequestEventListener extends EventListener {
	
	public void fireAPIRequestEvent(APIRequestEvent e);
}