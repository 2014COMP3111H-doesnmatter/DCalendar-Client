package hkust.cse.calendar.api.venue;

import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.Map;

public class ListAPI extends BaseAPI {
	static String baseURL = "/venue/list";
	
	public ListAPI() {
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