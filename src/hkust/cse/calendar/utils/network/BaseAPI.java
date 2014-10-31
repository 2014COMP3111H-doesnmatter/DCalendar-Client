package hkust.cse.calendar.utils.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hkust.cse.calendar.Main.Config;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.EventSource;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseAPI extends EventSource {
	private List<GenListener<APIRequestEvent>> successListener = new ArrayList<GenListener<APIRequestEvent>>();
	private List<GenListener<APIRequestEvent>> failListener = new ArrayList<GenListener<APIRequestEvent>>();
	private List<GenListener<APIRequestEvent>> doneListener = new ArrayList<GenListener<APIRequestEvent>>();
	
	abstract public String getRequestURL();
	
	public String getRequestMethod() {
		return "GET";
	}

	public String getRequestContentType() {
		return "application/x-www-form-urlencoded";
	}
	
	public void writeRequestBody(OutputStream out) {
		try {
			String body = getParamString();
			out.write(body.getBytes());
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	protected String getParamString() {
		QueryString queryStr = new QueryString();
		Map<String, String> params = getParam();
		Iterator<Entry<String, String>> itr = params.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<String, String> pair = itr.next();
			queryStr.add(pair.getKey(), pair.getValue());
		}
		return queryStr.toString();
	}
	
	abstract protected Map<String, String> getParam();
	protected Map<Date, Date> getDateParam() {
		return null;
	}
	
	public void handleResponse(JSONObject response) {
		notifyListener(response);
	}
	
	protected String getDerivedURL(String route) {
		return Config.baseServerURL + route;
	}
	
	public void addSuccessListener(GenListener<APIRequestEvent> listener) {
		this.successListener.add(listener);
	}
	
	public void addFailListener(GenListener<APIRequestEvent> listener) {
		this.failListener.add(listener);
	}
	
	public void addDoneListener(GenListener<APIRequestEvent> listener) {
		this.doneListener.add(listener);
	}
	
	private void notifyListener(JSONObject response) {
		APIRequestEvent e = new APIRequestEvent(this, response);
		int rtnCode;
		
		try {
			rtnCode = response.getInt("rtnCode");
		} catch (JSONException ex) {
			ex.printStackTrace();
			rtnCode = -1;
		}
		
		//Do done for all
		fireList(doneListener, e);
		
		//Do success only when succeed
		if(rtnCode == 200) {
			fireList(successListener, e);
		}
		else {
			fireList(failListener, e);
		}
	}
};