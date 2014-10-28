package hkust.cse.calendar.api.appointment;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class AddAPI extends BaseAPI {
	static String baseURL = "/appointment/add";
	private Appointment appt;
	
	public AddAPI(Appointment appt) {
		this.appt = appt;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("name", appt.getName());
		params.put("venueId", String.valueOf(appt.getVenue().getId()));
		params.put("startTime", String.valueOf(appt.getStartTime()));
		params.put("endTime", String.valueOf(appt.getEndTime()));
		params.put("info", appt.getInfo());
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}