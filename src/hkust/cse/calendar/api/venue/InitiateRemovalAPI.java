package hkust.cse.calendar.api.venue;

import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class InitiateRemovalAPI extends BaseAPI {
	static String baseURL = "/venue/initiateRemoval";
	private Venue v;
	
	public InitiateRemovalAPI(Venue v) {
		this.v = v;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(v.getId()));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
}