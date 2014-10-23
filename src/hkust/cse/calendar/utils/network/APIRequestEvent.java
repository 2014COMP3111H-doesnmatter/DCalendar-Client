package hkust.cse.calendar.utils.network;

import java.util.EventObject;

import org.json.JSONObject;


public class APIRequestEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private JSONObject jsonObj;

	public APIRequestEvent(Object source, JSONObject resJson) {
		super(source);
		this.jsonObj = resJson;
	}
	
	public JSONObject getJSON() {
		return jsonObj;
	}
}