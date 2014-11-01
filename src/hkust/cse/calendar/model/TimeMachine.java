package hkust.cse.calendar.model;

import hkust.cse.calendar.api.timemachine.GetNowAPI;
import hkust.cse.calendar.api.timemachine.TimeMachineAPI;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.Date;
import java.util.EventObject;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeMachine extends Updatable {
	private long timeDiff = 0L;
	static private TimeMachine instance;
	
	public TimeMachine() {
		instance = this;
	}
	public TimeMachine(JSONObject json) throws JSONException {
		timeDiff = json.getLong(null);
		instance = this;
	}
	
	static public TimeMachine getInstance() {
		return instance;
	}

	public Date getNow() {
		if(timeDiff==0) return new Date();
		return new Date(new Date().getTime() + timeDiff);
	}
	
	private void setNow(long timestamp) {
		timeDiff = timestamp - new Date().getTime();
	}
	
	public void load() {
		GetNowAPI api = new GetNowAPI();
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						setNow(json.getLong("timestamp"));
						UpdatableEvent ev = new UpdatableEvent(this);
						ev.setCommand(UpdatableEvent.Command.INFO_UPDATE);
						fireList(colListener, ev);
					}
				} catch(JSONException ex) {
					ex.printStackTrace();
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	//add server listener
	public void setTime(final long timestamp, final GenListener<SetTime> listener) {
		TimeMachineAPI api = new TimeMachineAPI(timestamp);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				SetTime qry = new SetTime(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						setNow(timestamp);
						qry.setRtnValue(SetTime.RtnValue.COMPLETE);
						UpdatableEvent ev = new UpdatableEvent(this);
						ev.setCommand(UpdatableEvent.Command.INFO_UPDATE);
						fireList(colListener, ev);
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
	
	//add event "setTimeCompletion"
	static public class SetTime extends EventObject {
		public enum RtnValue {
			COMPLETE
		};
		private RtnValue rtnValue;
		
		public SetTime(Object source) {
			super(source);
		}
		
		public SetTime(Object source, RtnValue rtnValue) {
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