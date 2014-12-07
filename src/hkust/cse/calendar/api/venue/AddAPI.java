package hkust.cse.calendar.api.venue;

import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class AddAPI extends BaseAPI {
	static String baseURL = "/venue/add";
	private Venue v;
	
	public AddAPI(Venue v) {
		this.v = v;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("name", v.getName());
		params.put("capacity", String.valueOf(v.getCapacity()));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}