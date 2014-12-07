package hkust.cse.calendar.api.welcome;

import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class EditUserAPI extends BaseAPI {
	static String baseURL = "/welcome/editUser";
	private User oldU;
	private User newU;
	private String password;
	
	public EditUserAPI(User oldU, User newU, String password) {
		this.oldU = oldU;
		this.newU = newU;
		this.password = password;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(oldU.getId()));
		params.put("username", newU.getUsername());
		if(password.length() > 0)
			params.put("password", password);
		params.put("fullName", newU.getFullname());
		params.put("email", newU.getEmail());
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}