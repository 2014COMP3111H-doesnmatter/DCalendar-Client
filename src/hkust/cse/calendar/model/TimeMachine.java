package hkust.cse.calendar.model;

import java.util.Date;

import hkust.cse.calendar.api.timemachine.TimeMachineAPI;
import hkust.cse.calendar.model.TimeMachine.SetTime;
import hkust.cse.calendar.model.TimeMachine.SetTime.RtnValue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.EventObject;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeMachine extends BaseModel {
	private long timeDiff = 0L;
	
	public TimeMachine() { }
	public TimeMachine(JSONObject json) throws JSONException {
		timeDiff = json.getLong(null);
	}

	public Date getTime() {
		if(timeDiff==0) return new Date();
		return new Date(new Date().getTime() + timeDiff);
	}
	
	public void setTime(long timestamp) {
		timeDiff = timestamp - new Date().getTime();
	}
	
	//add server listener
	static public void setTime(final long timestamp, final GenListener<SetTime> listener) {
		TimeMachineAPI api =  new TimeMachineAPI(timestamp);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				SetTime qry = new SetTime(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setRtnValue(SetTime.RtnValue.COMPLETE);
						TimeMachine tm = new TimeMachine();
						JSONObject tmJson = json.getJSONObject("tm");
						tm.id = tmJson.getLong("id");
						tm.timeDiff = tmJson.getLong("timeDiff");
						qry.setTimeMachine(tm);
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
		private TimeMachine tm;
		
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

		public TimeMachine getTimeMachine() {
			return tm;
		}

		public void setTimeMachine(TimeMachine tm) {
			this.tm = tm;
		}
		
	}
	
}