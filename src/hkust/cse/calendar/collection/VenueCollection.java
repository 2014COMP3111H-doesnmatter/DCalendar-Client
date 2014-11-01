package hkust.cse.calendar.collection;

import hkust.cse.calendar.api.venue.AddAPI;
import hkust.cse.calendar.api.venue.ListAPI;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VenueCollection extends Updatable {
	private Map<Long, Venue> aVenue = new HashMap<Long, Venue>();
	static private VenueCollection instance;
	
	public VenueCollection() {
		instance = this;
	}
	
	static public VenueCollection getInstance() {
		return instance;
	}
	
	public void load() {
		final ListAPI api = new ListAPI();
		final VenueCollection that = this;
		
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				int i, size;
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					UpdatableEvent ev = new UpdatableEvent(that);
					if(rtnCode == 200) {
						JSONArray aJson = json.getJSONArray("aVenue");
						for(i = 0, size = aJson.length(); i < size; i++) {
							JSONObject apptJson = aJson.getJSONObject(i);
							Venue venue = new Venue(apptJson);
							aVenue.put(venue.getId(), venue);
						}
						ev.setCommand(UpdatableEvent.Command.INFO_UPDATE);
					}
					else {
						ev.setCommand(UpdatableEvent.Command.NETWORK_ERR);
					}
					fireList(colListener, ev);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
				
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}

	public Map<Long, Venue> getVenueList() {
		return aVenue;
	}
	
	public Venue getVenue(long id) {
		return aVenue.get(id);
	}
	
	public void addVenue(String name) {
		final AddAPI addApi = new AddAPI(name);
		addApi.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				
				JSONObject json = e.getJSON();
				
				try {
					int rtnCode = json.getInt("rtnCode");
					UpdatableEvent ev = new UpdatableEvent(VenueCollection.this);
					switch(rtnCode) {
					case 200:
						Venue rtnVenue = new Venue(json.getJSONObject("venue"));
						aVenue.put(rtnVenue.getId(), rtnVenue);
						ev.setCommand(UpdatableEvent.Command.INFO_UPDATE);
						fireList(colListener, ev);
						break;
					default:
						break;
					}
					
				}
				catch(Exception e1) {
					
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(addApi));
		thrd.start();
	}

}