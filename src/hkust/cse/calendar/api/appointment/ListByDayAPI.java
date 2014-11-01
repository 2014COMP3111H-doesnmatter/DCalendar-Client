package hkust.cse.calendar.api.appointment;

import java.util.HashMap;
import java.util.Map;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.network.BaseAPI;

public class ListByDayAPI extends BaseAPI {
	static String baseURL = "/appointment/listByDay";
	private long startOfDay;
	
	public ListByDayAPI(long startOfDay) {
		this.startOfDay = startOfDay;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL) + "?" + getParamString();
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("timestamp", String.valueOf(startOfDay));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
}