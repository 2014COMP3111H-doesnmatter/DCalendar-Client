package hkust.cse.calendar.api.welcome;

import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class LoginAPI extends BaseAPI {
	static String baseURL = "/welcome/login";
	private String username;
	private String password;
	
	public LoginAPI(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("username", username);
		params.put("password", password);
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
	
}