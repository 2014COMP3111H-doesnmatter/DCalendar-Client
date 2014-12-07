package hkust.cse.calendar.collection;

import hkust.cse.calendar.api.venue.AddAPI;
import hkust.cse.calendar.api.venue.EditAPI;
import hkust.cse.calendar.api.venue.InitiateRemovalAPI;
import hkust.cse.calendar.api.venue.ListAPI;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.model.User.UserRemoveQuery.RtnValue;
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
						aVenue.clear();
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
	
	public void addVenue(Venue v, final GenListener<VenueQuery> listener) {
		final AddAPI addApi = new AddAPI(v);
		addApi.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					UpdatableEvent ev = new UpdatableEvent(VenueCollection.this);
					VenueQuery qry = new VenueQuery(VenueCollection.this);
					switch(rtnCode) {
					case 200:
						Venue rtnVenue = new Venue(json.getJSONObject("venue"));
						aVenue.put(rtnVenue.getId(), rtnVenue);
						ev.setCommand(UpdatableEvent.Command.INFO_UPDATE);
						fireList(colListener, ev);
						qry.setRtnValue(VenueQuery.RtnValue.OK);
						qry.setVenue(rtnVenue);
						fireTo(listener, qry);
						break;
					case 201:
						qry.setRtnValue(VenueQuery.RtnValue.DUPLICATE_ERR);
						fireTo(listener, qry);
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
	
	public void editVenue(Venue oldV, Venue newV, final GenListener<VenueQuery> listener) {
		final EditAPI editApi = new EditAPI(oldV, newV);
		editApi.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					UpdatableEvent ev = new UpdatableEvent(VenueCollection.this);
					VenueQuery qry = new VenueQuery(VenueCollection.this);
					switch(rtnCode) {
					case 200:
						Venue rtnVenue = new Venue(json.getJSONObject("venue"));
						aVenue.put(rtnVenue.getId(), rtnVenue);
						ev.setCommand(UpdatableEvent.Command.INFO_UPDATE);
						fireList(colListener, ev);
						qry.setRtnValue(VenueQuery.RtnValue.OK);
						qry.setVenue(rtnVenue);
						fireTo(listener, qry);
						break;
					case 201:
						qry.setRtnValue(VenueQuery.RtnValue.DUPLICATE_ERR);
						fireTo(listener, qry);
					default:
						break;
					}
					
				}
				catch(Exception e1) {
					
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(editApi));
		thrd.start();
	}
	
	public void removeVenue(Venue v) {
		final InitiateRemovalAPI removeApi = new InitiateRemovalAPI(v);
		Thread thrd = new Thread(new APIHandler(removeApi));
		thrd.start();
	}


	static public class VenueQuery extends EventObject {
		public enum RtnValue {
			OK,
			DUPLICATE_ERR,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private RtnValue rtnValue;
		private Venue venue;
		
		public Venue getVenue() {
			return venue;
		}

		public void setVenue(Venue venue) {
			this.venue = venue;
		}

		public VenueQuery(Object source) {
			super(source);
		}
		
		public VenueQuery(Object source, RtnValue rtnValue) {
			super(source);
			this.setRtnValue(rtnValue);
		}

		public RtnValue getRtnValue() {
			return rtnValue;
		}

		public void setRtnValue(RtnValue rtnValue) {
			this.rtnValue = rtnValue;
		}
		
	}
	
}