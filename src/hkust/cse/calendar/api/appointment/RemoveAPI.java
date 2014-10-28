package hkust.cse.calendar.api.appointment;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class RemoveAPI extends BaseAPI {
	static String baseURL = "/appointment/remove";
	private Appointment appt;
	
	public RemoveAPI(Appointment appt) {
		this.appt = appt;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(appt));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}