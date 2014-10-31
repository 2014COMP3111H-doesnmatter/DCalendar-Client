package hkust.cse.calendar.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Venue extends BaseModel {
	private String name;
	
	public Venue(JSONObject json) throws JSONException {
		id = json.getInt("id");
		name = json.getString("name");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}