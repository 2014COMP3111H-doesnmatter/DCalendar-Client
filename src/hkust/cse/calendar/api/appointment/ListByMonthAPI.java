package hkust.cse.calendar.api.appointment;

import java.util.HashMap;
import java.util.Map;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.network.BaseAPI;

public class ListByMonthAPI extends BaseAPI {
	static String baseURL = "/appointment/listByMonth";
	private long startOfMonth;
	
	public ListByMonthAPI(long startOfMonth) {
		this.startOfMonth = startOfMonth;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL) + "?" + getParamString();
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("timestamp", String.valueOf(startOfMonth));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
}