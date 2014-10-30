package hkust.cse.calendar.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.utils.EventSource;

public class Appointment extends BaseModel {
	
	private long startTime;
	private long endTime;
	private User initiator;
	private String name;
	private String info;
	private Venue venue;
	private int frequency;
	private long lastDay;
	
	public Appointment() {
		name = "Untitled";
	}
	
	public Appointment(JSONObject json) throws JSONException {
		id = json.getInt("id");
		name = json.getString("name");
		setStartTime(json.getLong("startTime"));
		setEndTime(json.getLong("endTime"));
		info = json.getString("info");
		frequency = json.getInt("frequency");
		lastDay = json.getLong("lastDay");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public long getLastDay() {
		return lastDay;
	}
	public void setLastDay(long lastDay) {
		this.lastDay = lastDay;
	}

	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public User getInitiator() {
		return initiator;
	}


	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public static final class Frequency {
		public static final int ONCE = 0;
		public static final int DAILY = 1;
		public static final int WEEKLY = 2;
		public static final int MONTHLY = 3;
	}
	
}