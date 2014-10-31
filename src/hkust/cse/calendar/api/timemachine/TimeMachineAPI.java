package hkust.cse.calendar.api.timemachine;

import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class TimeMachineAPI extends BaseAPI {
	static String baseURL = "/TimeMachine/SetTime";
	private Date timestamp;
	
	public TimeMachineAPI(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<Date,Date> getDateParam() {
		Map<Date, Date> params = new HashMap<Date, Date>();
		
		params.put(new Date(), timestamp);
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}