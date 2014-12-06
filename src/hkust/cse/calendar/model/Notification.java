package hkust.cse.calendar.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Notification {
	private String type;
	private Object body;
	
	public Notification(String type, Object body) {
		this.setType(type);
		this.setBody(body);
	}
	
	public Notification(String type, JSONObject body) {
		this.setType(type);
		try {
			if(type.startsWith("JointAppointment")) {
				this.setBody(new Appointment(body));
			}
			else if(type.startsWith("User")) {
				this.setBody(new User(body));
			}
			else if(type.startsWith("Venue")) {
				this.setBody(new Venue(body));
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		catch(JSONException ex) {
			ex.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
}
