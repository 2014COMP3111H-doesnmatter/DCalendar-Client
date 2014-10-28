package hkust.cse.calendar.collection;

import hkust.cse.calendar.api.venue.ListAPI;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VenueCollection extends BaseCollection {
	private Map<Long, Venue> aVenue = new HashMap<Long, Venue>();
	
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
					CollectionEvent ev = new CollectionEvent(that);
					if(rtnCode == 200) {
						JSONArray aJson = json.getJSONArray("aVenue");
						for(i = 0, size = aJson.length(); i < size; i++) {
							JSONObject apptJson = aJson.getJSONObject(i);
							Venue venue = new Venue(apptJson);
							aVenue.put(venue.getId(), venue);
						}
						ev.setCommand(CollectionEvent.Command.INFO_UPDATE);
					}
					else {
						ev.setCommand(CollectionEvent.Command.NETWORK_ERR);
					}
					fireList(colListener, ev);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
				
			}
			
		});
	}

	public Map<Long, Venue> getVenueList() {
		return aVenue;
	}
}