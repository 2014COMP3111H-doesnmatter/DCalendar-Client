package hkust.cse.calendar.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeMachine extends BaseModel {
	private static long timeDiff = 0L;
	
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
}