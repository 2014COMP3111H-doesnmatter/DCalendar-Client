package hkust.cse.calendar.utils.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hkust.cse.calendar.Main.Config;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseAPI {
	private List<APIRequestEventListener> successListener = new ArrayList<APIRequestEventListener>();
	private List<APIRequestEventListener> failListener = new ArrayList<APIRequestEventListener>();
	private List<APIRequestEventListener> doneListener = new ArrayList<APIRequestEventListener>();
	
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
	
	public void handleResponse(JSONObject response) {
		notifyListener(response);
	}
	
	protected String getDerivedURL(String route) {
		return Config.baseServerURL + route;
	}
	
	public void addSuccessListener(APIRequestEventListener listener) {
		this.successListener.add(listener);
	}
	
	public void addFailListener(APIRequestEventListener listener) {
		this.failListener.add(listener);
	}
	
	public void addDoneListener(APIRequestEventListener listener) {
		this.doneListener.add(listener);
	}
	
	private void notifyListener(JSONObject response) {
		APIRequestEvent e = new APIRequestEvent(this, response);
		APIRequestEventListener listener;
		Iterator<APIRequestEventListener> itr;
		int rtnCode;
		
		try {
			rtnCode = response.getInt("rtnCode");
		} catch (JSONException ex) {
			ex.printStackTrace();
			rtnCode = -1;
		}
		
		//Do done for all
		itr = this.doneListener.iterator();
		while(itr.hasNext()) {
			listener = itr.next();
			listener.fireAPIRequestEvent(e);
		}
		
		//Do success only when succeed
		if(rtnCode == 200) {
			itr = this.successListener.iterator();
			while(itr.hasNext()) {
				listener = itr.next();
				listener.fireAPIRequestEvent(e);
			}
		}
		else {
			itr = this.failListener.iterator();
			while(itr.hasNext()) {
				listener = itr.next();
				listener.fireAPIRequestEvent(e);
			}
		}
	}
};