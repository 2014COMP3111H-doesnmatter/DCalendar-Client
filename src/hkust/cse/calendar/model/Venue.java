package hkust.cse.calendar.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Venue extends BaseModel {
	private String name;
	private int capacity = 0;
	
	public Venue(JSONObject json) throws JSONException {
		id = json.getInt("id");
		name = json.getString("name");
		capacity = json.getInt("capacity");
	}

	public Venue() {
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}