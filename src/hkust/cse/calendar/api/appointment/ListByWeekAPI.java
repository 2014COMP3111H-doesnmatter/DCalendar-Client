package hkust.cse.calendar.api.appointment;

import java.util.HashMap;
import java.util.Map;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.utils.network.BaseAPI;

public class ListByWeekAPI extends BaseAPI {
	static String baseURL = "/appointment/listByWeek";
	private long startOfWeek;
	
	public ListByWeekAPI(long startOfWeek) {
		this.startOfWeek = startOfWeek;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL) + "?" + getParamString();
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("timestamp", String.valueOf(startOfWeek));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
}