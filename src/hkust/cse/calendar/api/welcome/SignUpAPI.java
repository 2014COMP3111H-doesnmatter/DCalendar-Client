package hkust.cse.calendar.api.welcome;

import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class SignUpAPI extends BaseAPI {
	static String baseURL = "/welcome/signUp";
	private User user;
	private String password;
	
	public SignUpAPI(User u, String password) {
		this.user = u;
		this.password = password;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("username", user.getUsername());
		params.put("password", password);
		params.put("fullName", user.getFullname());
		params.put("email", user.getEmail());
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}