package hkust.cse.calendar.collection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.api.notification.NextAPI;
import hkust.cse.calendar.model.Notification;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class NotificationCollection extends Updatable {
	static private NotificationCollection instance;
	private List<Notification> aNotification = new ArrayList<Notification>();
	
	public NotificationCollection() {
		instance = this;
	}
	
	public void load() {
		issuePolling();
	}
	
	private void issuePolling() {
		NextAPI api = new NextAPI();
		final NotificationCollection that = this;
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				int i;
				if(instance != that) {
					return;
				}
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode != 200) {
						return;
					}
					
					JSONArray arr = json.getJSONArray("aNotification");
					if(arr.length() > 0) {
						List<Notification> aNew = new ArrayList<Notification>();
						for(i = 0; i < arr.length(); i++) {
							JSONObject obj = arr.getJSONObject(i);
							String type = obj.getString("type");
							JSONObject target = obj.getJSONObject("value");
							aNew.add(new Notification(type, target));
						}
						aNotification.addAll(aNew);
						UpdatableEvent ev = new UpdatableEvent(that, UpdatableEvent.Command.INFO_UPDATE);
						ev.setNewVal(aNew);
						fireList(colListener, ev);
					}
				}
				catch(JSONException ex) {
					ex.printStackTrace();
				}

				issuePolling();
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	static public NotificationCollection getInstance() {
		return instance;
	}

	public List<Notification> getaNotification() {
		return aNotification;
	}
	
	public void pushNotification(Notification n) {
		List<Notification> aNew = new ArrayList<Notification>();
		aNotification.add(n);
		aNew.add(n);
		UpdatableEvent ev = new UpdatableEvent(this, UpdatableEvent.Command.INFO_UPDATE);
		ev.setNewVal(aNew);
		fireList(colListener, ev);
	}
}