package hkust.cse.calendar.api.welcome;

import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class LogoutAPI extends BaseAPI {
	static String baseURL = "/welcome/logout";
	
	public LogoutAPI() {
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		
		return null;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
	
}