package hkust.cse.calendar.api.notification;

import java.util.Map;

import hkust.cse.calendar.utils.network.BaseAPI;

public class NextAPI extends BaseAPI {
	static String baseURL = "/notification/next";

	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}

	@Override
	protected Map<String, String> getParam() {
		return null;
	}
	
}