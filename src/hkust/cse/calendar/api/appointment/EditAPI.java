package hkust.cse.calendar.api.appointment;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class EditAPI extends BaseAPI {
	static String baseURL = "/appointment/edit";
	private Appointment appt;
	
	public EditAPI(Appointment appt) {
		this.appt = appt;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(appt.getId()));
		params.put("venueId", String.valueOf(appt.getVenueId()));
		params.put("startTime", String.valueOf(appt.getStartTime()));
		params.put("endTime", String.valueOf(appt.getEndTime()));
		params.put("frequency", String.valueOf(appt.getFrequency()));
		params.put("lastDay", String.valueOf(appt.getLastDay()));
		params.put("reminderAhead", String.valueOf(appt.getReminderAhead()));
		params.put("info", appt.getInfo());
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}