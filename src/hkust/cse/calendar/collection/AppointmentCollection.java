package hkust.cse.calendar.collection;

import hkust.cse.calendar.api.appointment.AddAPI;
import hkust.cse.calendar.api.appointment.EditAPI;
import hkust.cse.calendar.api.appointment.ListByMonthAPI;
import hkust.cse.calendar.api.appointment.RemoveAPI;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.DateTimeHelper;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class maintains a collection of Appointments in one month.<br>
 * 
 * <p>Typically this class is not thread-safe.</p>
 * <p>The tricky part is that, while a class A(may be controller) asks this class 
 * for some information. At that time this class may have already sent a request
 * to server but not yet get answered. So we take advantage of the fact that we run
 * all these event things on EDT of swing. So at any time, only one event is being 
 * processed. So we can append the request of A to the listener of the API request
 * to avoid synchronization problem or redundant request.</p>
 * @author john
 *
 */
public class AppointmentCollection extends Updatable {
	static private AppointmentCollection instance;
	final static private int MAX_DAY_IN_MONTH = 31;
	
	private long startOfMonth;
	
	private Map<Long, Appointment> aAppt = new HashMap<Long, Appointment>();
	
	final static private int CLEAN = 0;
	final static private int DIRTY = 1;
	final static private int QUERYING = 2;
	
	private int state;
	private ListByMonthAPI monthAPI;
	private boolean[] aHasEvent = new boolean[MAX_DAY_IN_MONTH];
	
	public AppointmentCollection(long initStamp) {
		state = DIRTY;
		this.startOfMonth = DateTimeHelper.getStartOfMonth(initStamp);
		instance = this;
	}
	
	static public AppointmentCollection getInstance() {
		return instance;
	}
	
	public void load() {
		final ListByMonthAPI api = new ListByMonthAPI(startOfMonth);
		final AppointmentCollection that = this;
		state = QUERYING;
		monthAPI = api;
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				int i, size;
				if(!isLatestRequest(api)) {
					return;
				}
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					UpdatableEvent ev = new UpdatableEvent(that);
					if(rtnCode == 200) {
						JSONArray aJson = json.getJSONArray("aAppointment");
						for(i = 0, size = aJson.length(); i < size; i++) {
							JSONObject apptJson = aJson.getJSONObject(i);
							Appointment appt = new Appointment(apptJson);
							if(appt.isScheduled())
								aAppt.put(appt.getId(), appt);
						}
						flagHasEvent();
						state = CLEAN;
						triggerUpdate();
					}
					else {
						ev.setCommand(UpdatableEvent.Command.NETWORK_ERR);
						fireList(colListener, ev);
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			
		});
		
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}

	public void createAppt(Appointment appt, final GenListener<AddAppointmentQuery> listener) {
		AddAPI api = new AddAPI(appt);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				System.out.println(json);
				AddAppointmentQuery qry = new AddAppointmentQuery(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setCommand(AddAppointmentQuery.Command.OK);
						Appointment rtnAppt = new Appointment(json.getJSONObject("appointment"));
						qry.setAppt(rtnAppt);
						if(DateTimeHelper.isInMonth(startOfMonth, rtnAppt)) {
							aAppt.put(rtnAppt.getId(), rtnAppt);
							flagHasEvent();
							triggerUpdate();
						}
					}
					else if(rtnCode == 405) {
						qry.setCommand(AddAppointmentQuery.Command.VENUE_NOT_FOUND);
					}
					else if(rtnCode == 406) {
						qry.setCommand(AddAppointmentQuery.Command.ILLEGAL_TIME);
						qry.setExplain(json.getString("explain"));
					}
					else if(rtnCode == -1) {
						qry.setCommand(AddAppointmentQuery.Command.NETWORK_ERR);
					}
					else {
						qry.setCommand(AddAppointmentQuery.Command.UNKNOWN_ERR);
					}
					fireTo(listener, qry);
				} catch(JSONException ex) {
					ex.printStackTrace();
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	public void editAppt(Appointment appt, final GenListener<EditAppointmentQuery> listener) {
		EditAPI api = new EditAPI(appt);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				EditAppointmentQuery qry = new EditAppointmentQuery(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setCommand(EditAppointmentQuery.Command.OK);
						Appointment rtnAppt = new Appointment(json.getJSONObject("appointment"));
						qry.setAppt(rtnAppt);
						if(aAppt.containsKey(rtnAppt.getId())) {
							aAppt.remove(rtnAppt.getId());
						}
						if(DateTimeHelper.isInMonth(startOfMonth, rtnAppt)) {
							aAppt.put(rtnAppt.getId(), rtnAppt);
						}
						flagHasEvent();
						triggerUpdate();
					}
					else if(rtnCode == 405) {
						qry.setCommand(EditAppointmentQuery.Command.APPT_NOT_FOUND);
					}
					else if(rtnCode == 406) {
						qry.setCommand(EditAppointmentQuery.Command.PERM_DENIED);
					}
					else if(rtnCode == 407) {
						qry.setCommand(EditAppointmentQuery.Command.VENUE_NOT_FOUND);
					}
					else if(rtnCode == 408) {
						qry.setCommand(EditAppointmentQuery.Command.ILLEGAL_TIME);
						qry.setExplain(json.getString("explain"));
					}
					else if(rtnCode == -1) {
						qry.setCommand(EditAppointmentQuery.Command.NETWORK_ERR);
					}
					else {
						qry.setCommand(EditAppointmentQuery.Command.UNKNOWN_ERR);
					}
					fireTo(listener, qry);
				} catch(JSONException ex) {
					ex.printStackTrace();
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	public void removeAppt(final Appointment appt, final GenListener<RemoveAppointmentQuery> listener) {
		RemoveAPI api = new RemoveAPI(appt);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				RemoveAppointmentQuery qry = new RemoveAppointmentQuery(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setCommand(RemoveAppointmentQuery.Command.OK);
						if(aAppt.containsKey(appt.getId())) {
							aAppt.remove(appt.getId());
							flagHasEvent();
							triggerUpdate();
						}
					}
					else if(rtnCode == 405) {
						qry.setCommand(RemoveAppointmentQuery.Command.APPT_NOT_FOUND);
					}
					else if(rtnCode == 406) {
						qry.setCommand(RemoveAppointmentQuery.Command.PERM_DENIED);
					}
					else if(rtnCode == 407) {
						qry.setCommand(RemoveAppointmentQuery.Command.ILLEGAL_TIME);
					}
					else if(rtnCode == -1) {
						qry.setCommand(RemoveAppointmentQuery.Command.NETWORK_ERR);
					}
					else {
						qry.setCommand(RemoveAppointmentQuery.Command.UNKNOWN_ERR);
					}
					fireTo(listener, qry);
				} catch(JSONException ex) {
					ex.printStackTrace();
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	public void getEventInDay(final long startOfDay, final GenListener<ListAppointmentQuery> listener) {
		switch(state) {
		case DIRTY:
			load();
		case QUERYING:
			final ListByMonthAPI api = monthAPI;
			monthAPI.addDoneListener(new GenListener<APIRequestEvent>() {

				@Override
				public void fireEvent(APIRequestEvent e) {
					if(!isLatestRequest(api)) {
						return;
					}
					JSONObject json = e.getJSON();
					ListAppointmentQuery qry = new ListAppointmentQuery(this);
					
					try {
						int rtnCode = json.getInt("rtnCode");
						if(rtnCode == 200) {
							sendListQuery(startOfDay, listener);
							return;
						}
						else if(rtnCode == -1) {
							qry.setCommand(ListAppointmentQuery.Command.NETWORK_ERR);
						}
						else {
							qry.setCommand(ListAppointmentQuery.Command.UNKNOWN_ERR);
						}
						fireTo(listener, qry);
					} catch(JSONException ex) {
						ex.printStackTrace();
					}
				}
				
			});
			break;
		default:
			sendListQuery(startOfDay, listener);
		}
	}

	public void getOccupiedInMonth(final GenListener<MonthAppointmentQuery> listener) {
		switch(state) {
		case DIRTY:
			load();
		case QUERYING:
			final ListByMonthAPI api = monthAPI;
			monthAPI.addDoneListener(new GenListener<APIRequestEvent>() {

				@Override
				public void fireEvent(APIRequestEvent e) {
					if(!isLatestRequest(api)) {
						return;
					}
					JSONObject json = e.getJSON();
					MonthAppointmentQuery qry = new MonthAppointmentQuery(this);
					
					try {
						int rtnCode = json.getInt("rtnCode");
						if(rtnCode == 200) {
							sendMonthQuery(listener);
							return;
						}
						else if(rtnCode == -1) {
							qry.setCommand(MonthAppointmentQuery.Command.NETWORK_ERR);
						}
						else {
							qry.setCommand(MonthAppointmentQuery.Command.UNKNOWN_ERR);
						}
						fireTo(listener, qry);
					} catch(JSONException ex) {
						ex.printStackTrace();
					}
				}
				
			});
			break;
		default:
			sendMonthQuery(listener);
		}
	}
	
	public void setMonthStart(long stamp) {
		long newStart = DateTimeHelper.getStartOfMonth(stamp);
		if(newStart != startOfMonth) {
			state = DIRTY;
			this.startOfMonth = DateTimeHelper.getStartOfMonth(stamp);
			load();
		}
	}
	
	private void triggerUpdate() {
		UpdatableEvent ev = new UpdatableEvent(this, UpdatableEvent.Command.INFO_UPDATE);
		fireList(colListener, ev);
	}
	
	private void sendListQuery(long startOfDay, GenListener<ListAppointmentQuery> listener) {
		List<Appointment> filteredEvent = new ArrayList<Appointment>();
		Iterator<Entry<Long, Appointment>> itr = aAppt.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<Long, Appointment> entry = itr.next();
			Appointment appt = entry.getValue();
			if(DateTimeHelper.isInDay(startOfDay, appt)) {
				filteredEvent.add(appt);
			}
		}
		ListAppointmentQuery qry = new ListAppointmentQuery(this, ListAppointmentQuery.Command.OK);
		qry.setaAppt(filteredEvent);
		fireTo(listener, qry);
	}
	
	private void sendMonthQuery(GenListener<MonthAppointmentQuery> listener) {
		MonthAppointmentQuery qry = new MonthAppointmentQuery(this, MonthAppointmentQuery.Command.OK);
		qry.occupied = aHasEvent.clone();
		fireTo(listener, qry);
	}
	
	private void flagHasEvent() {
		Iterator<Entry<Long, Appointment>> itr = aAppt.entrySet().iterator();
		Arrays.fill(aHasEvent, false);
		while(itr.hasNext()) {
			Map.Entry<Long, Appointment> entry = (Map.Entry<Long, Appointment>)itr.next();
			flagHasEvent(entry.getValue());
		}
	}
	
	private void flagHasEvent(Appointment appt) {
		int dayInMonth = DateTimeHelper.getDayInMonth(startOfMonth);
		int i;
		long startOfDay;
		for(i = 0, startOfDay = startOfMonth; i < dayInMonth; i++, startOfDay += DateTimeHelper.TIME_OF_DAY) {
			aHasEvent[i] = aHasEvent[i] || DateTimeHelper.isInDay(startOfDay, appt);
		}
	}
	
	private boolean isLatestRequest(ListByMonthAPI api) {
		return monthAPI == api;
	}
	
	
	final static public class AddAppointmentQuery extends EventObject {
		static public enum Command {
			OK,
			VENUE_NOT_FOUND,
			ILLEGAL_TIME,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private Command command;
		private Appointment appt;
		private String explain;
		
		AddAppointmentQuery(Object source) {
			super(source);
		}
		
		AddAppointmentQuery(Object source, Command command) {
			super(source);
			this.setCommand(command);
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public Appointment getAppt() {
			return appt;
		}

		public void setAppt(Appointment appt) {
			this.appt = appt;
		}

		public String getExplain() {
			return explain;
		}

		public void setExplain(String explain) {
			this.explain = explain;
		}
		
	}
	final static public class EditAppointmentQuery extends EventObject {
		static public enum Command {
			OK,
			APPT_NOT_FOUND,
			VENUE_NOT_FOUND,
			ILLEGAL_TIME,
			PERM_DENIED,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private Command command;
		private Appointment appt;
		private String explain;
		
		EditAppointmentQuery(Object source) {
			super(source);
		}
		
		EditAppointmentQuery(Object source, Command command) {
			super(source);
			this.setCommand(command);
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public Appointment getAppt() {
			return appt;
		}

		public void setAppt(Appointment appt) {
			this.appt = appt;
		}

		public String getExplain() {
			return explain;
		}

		public void setExplain(String explain) {
			this.explain = explain;
		}
		
	}
	final static public class RemoveAppointmentQuery extends EventObject {
		static public enum Command {
			OK,
			APPT_NOT_FOUND,
			PERM_DENIED,
			ILLEGAL_TIME,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private Command command;
		
		RemoveAppointmentQuery(Object source) {
			super(source);
		}
		
		RemoveAppointmentQuery(Object source, Command command) {
			super(source);
			this.setCommand(command);
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
		
	}
	final static public class ListAppointmentQuery extends EventObject {
		static public enum Command {
			OK,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private Command command;
		private List<Appointment> aAppt;
		
		ListAppointmentQuery(Object source) {
			super(source);
		}
		
		ListAppointmentQuery(Object source, Command command) {
			super(source);
			this.setCommand(command);
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public List<Appointment> getaAppt() {
			return aAppt;
		}

		public void setaAppt(List<Appointment> aAppt) {
			this.aAppt = aAppt;
		}
		
	}
	final static public class MonthAppointmentQuery extends EventObject {
		static public enum Command {
			OK,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private Command command;
		public boolean[] occupied = new boolean[DateTimeHelper.MAX_DAY_IN_MONTH];
		
		MonthAppointmentQuery(Object source) {
			super(source);
		}
		
		MonthAppointmentQuery(Object source, Command command) {
			super(source);
			this.setCommand(command);
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
		
	}

}