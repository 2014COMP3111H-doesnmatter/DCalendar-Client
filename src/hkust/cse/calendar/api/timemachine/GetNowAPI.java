package hkust.cse.calendar.api.timemachine;

import java.util.Map;

import hkust.cse.calendar.utils.network.BaseAPI;

public class GetNowAPI extends BaseAPI {
	static String baseURL = "/timeMachine/getNow";

	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}

	@Override
	protected Map<String, String> getParam() {
		return null;
	}
	
}