package hkust.cse.calendar.api.appointment;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.network.BaseAPI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAvailableTimeSlot extends BaseAPI {
	static String baseURL = "/appointment/getAvailableTimeSlot";
	private List<User> aUser;
	
	public GetAvailableTimeSlot(List<User> aUser) {
		this.aUser = aUser;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	protected Map<String, String> getParam() {
		Map<String, String> params = new HashMap<String, String>();

		long[] aWaitingId = new long[aUser.size()];
		int i = 0;
		for(User u: aUser) {
			aWaitingId[i++] = u.getId();
		}
		params.put("aUserId", Arrays.toString(aWaitingId));
		return params;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}
	
}