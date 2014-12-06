package hkust.cse.calendar.api.welcome;

import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class ConfirmUserRemovalAPI extends BaseAPI {
	static String baseURL = "/welcome/confirmRemoval";
	private User user;
	
	public ConfirmUserRemovalAPI(User user) {
		this.user = user;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(user.getId()));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "PUT";
	}
}