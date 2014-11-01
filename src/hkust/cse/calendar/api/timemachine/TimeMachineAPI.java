package hkust.cse.calendar.api.timemachine;

import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeMachineAPI extends BaseAPI {
	static String baseURL = "/timeMachine/setNow";
	private long timestamp;
	
	public TimeMachineAPI(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String,String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("timestamp", String.valueOf(timestamp));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}