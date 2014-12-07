package hkust.cse.calendar.api.venue;

import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class ConfirmVenueRemovalAPI extends BaseAPI {
	static String baseURL = "/venue/confirmRemoval";
	private Venue venue;
	
	public ConfirmVenueRemovalAPI(Venue venue) {
		this.venue = venue;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("id", String.valueOf(venue.getId()));
		
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
}