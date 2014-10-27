package hkust.cse.calendar.api.welcome;

import java.util.Map;

import hkust.cse.calendar.utils.network.BaseAPI;

public class GetActiveUserAPI extends BaseAPI {
	static String baseURL = "/welcome/getActiveUser";

	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}

	@Override
	protected Map<String, String> getParam() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
	
}