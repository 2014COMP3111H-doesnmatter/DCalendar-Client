package hkust.cse.calendar.api.venue;

import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class EditAPI extends BaseAPI {
	static String baseURL = "/venue/edit";
	private Venue oldV;
	private Venue newV;
	
	public EditAPI(Venue oldV, Venue newV) {
		this.oldV = oldV;
		this.newV = newV;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(oldV.getId()));
		params.put("name", newV.getName());
		params.put("capacity", String.valueOf(newV.getCapacity()));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}