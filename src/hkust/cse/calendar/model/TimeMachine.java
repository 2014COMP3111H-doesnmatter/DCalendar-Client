package hkust.cse.calendar.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeMachine extends BaseModel {
	private static long timestamp = 0L;
	
	public TimeMachine(JSONObject json) throws JSONException {
		timestamp = json.getLong(null);
	}

	public long setTime() {
		return timestamp;
	}

	public void getTime(Date d) {
		timestamp = d.getTime();
	}
	
	
}