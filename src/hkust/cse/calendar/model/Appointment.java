package hkust.cse.calendar.model;

import hkust.cse.calendar.Main.DCalendarApp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Appointment extends BaseModel {
	
	private long startTime;
	private long endTime;
	private User initiator;
	private String name;
	private String info;
	private long venueId;
	private int frequency;
	private long lastDay;
	private long reminderAhead;

	private List<User> aAccepted;
	private List<User> aRejected;
	private List<User> aWaiting;
	
	private boolean isJoint;
	
	public boolean isJoint() {
		return isJoint;
	}

	public void setJoint(boolean isJoint) {
		this.isJoint = isJoint;
	}

	public Appointment() {
		name = "Untitled";
	}
	
	public Appointment(JSONObject json) throws JSONException {
		id = json.getInt("id");
		name = json.getString("name");
		startTime = json.getLong("startTime");
		endTime = json.getLong("endTime");
		venueId = json.getLong("venueId");
		info = json.getString("info");
		frequency = json.getInt("frequency");
		lastDay = json.getLong("lastDay");
		reminderAhead = json.getLong("reminderAhead");
		initiator = new User(json.getJSONObject("initiator"));
		isJoint = json.getBoolean("isJoint");
		
		if(isJoint) {
			aAccepted = User.fromJSONArray(json.getJSONArray("aAccepted"));
			aRejected = User.fromJSONArray(json.getJSONArray("aRejected"));
			aWaiting = User.fromJSONArray(json.getJSONArray("aWaiting"));
		}
	}
	
	public boolean isScheduled() {
		if(isJoint 
				&& (!aRejected.isEmpty() || !aWaiting.isEmpty()) 
				&& !initiator.equals(DCalendarApp.getApp().getCurrentUser())) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
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

	public long getReminderAhead() {
		return reminderAhead;
	}

	public void setReminderAhead(long reminderAhead) {
		this.reminderAhead = reminderAhead;
	}

	public long getVenueId() {
		return venueId;
	}

	public void setVenueId(long venueId) {
		this.venueId = venueId;
	}

	public List<User> getaAccepted() {
		return aAccepted;
	}

	public void setaAccepted(List<User> aAccepted) {
		this.aAccepted = aAccepted;
	}

	public List<User> getaRejected() {
		return aRejected;
	}

	public void setaRejected(List<User> aRejected) {
		this.aRejected = aRejected;
	}

	public List<User> getaWaiting() {
		return aWaiting;
	}

	public void setaWaiting(List<User> aWaiting) {
		this.aWaiting = aWaiting;
	}

	public static final class Frequency {
		public static final int ONCE = 0;
		public static final int DAILY = 1;
		public static final int WEEKLY = 2;
		public static final int MONTHLY = 3;
	}
	
}