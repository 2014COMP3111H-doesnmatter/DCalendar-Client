package hkust.cse.calendar.api.appointment;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class RejectAPI extends BaseAPI {
	static String baseURL = "/appointment/reject";
	private Appointment appt;
	
	public RejectAPI(Appointment appt) {
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
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}