package hkust.cse.calendar.utils.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.CookieManager;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


public class APIHandler implements Runnable {
	static private CookieManager cookieManager = new CookieManager();
	static {
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);
	}
	
	private HttpURLConnection connection;
	private BaseAPI api;
	
	public APIHandler(BaseAPI api) {
		this.api = api;
		connection = prepareConnection();
	}
	
	protected HttpURLConnection prepareConnection() {
		HttpURLConnection conn;
		String sUrl = this.api.getRequestURL();
		String method = this.api.getRequestMethod();
		String contentType = this.api.getRequestContentType();
		try {
			URL url = new URL(sUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			if(!method.equals("GET")) {
				conn.setRequestProperty("Content-Type", contentType);
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) JavaSE/1.7 Jre/7(java.net, Like Chrome)");
			conn.setUseCaches(false);
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	private void dispatchRequest() throws IOException {
		if(connection.getDoOutput()) {
			OutputStream os = connection.getOutputStream();
			this.api.writeRequestBody(os);
			os.close();
		}
		connection.connect();
	}
	
	private String readResponse() throws IOException {
		if(connection.getResponseCode() == -1) {
			throw new IOException();
		}
		InputStream response = connection.getInputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response, "utf-8"));
		StringBuilder builder = new StringBuilder();
		
		String line = null;
		while((line = reader.readLine()) != null) {
			builder.append(line + "\n");
		}
		
		connection.disconnect();
		return builder.toString();
	}
	
	private JSONObject doRequest() {
		JSONObject jsonObj;
		try {
			dispatchRequest();
			String jsonText = readResponse();
			jsonObj = new JSONObject(jsonText);

			String rtn = jsonObj.getString("rtnCode");
			int realRtnCode = Integer.parseInt(rtn.split(" ", 2)[0]);
			String realRtnMessage = rtn.split(" ", 2)[1];
			
			jsonObj.put("rtnCode", realRtnCode);
			jsonObj.put("rtnMessage", realRtnMessage);
		} catch (IOException | JSONException e) {
			jsonObj = new JSONObject();
			try {
				jsonObj.put("rtnCode", -1);
				jsonObj.put("rtnMessage", "Network Error");
			} catch (JSONException neverHappen) {
				neverHappen.printStackTrace();
			}
		}
		return jsonObj;
	}
	
	static public void resetCookie() {
		cookieManager.getCookieStore().removeAll();
	}
	
	@Override
	public void run() {
		JSONObject jsonObj = doRequest();
		this.api.handleResponse(jsonObj);
	}
};