package hkust.cse.calendar.api.welcome;

import java.io.IOException;

import java.io.OutputStream;

import hkust.cse.calendar.utils.network.BaseAPI;
import hkust.cse.calendar.utils.network.QueryString;

public class LoginAPI extends BaseAPI {
	static String baseURL = "/welcome/login";
	private String username;
	private String password;
	
	public LoginAPI(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String getRequestURL() {
		return getDerivedURL(baseURL);
	}
	
	@Override
	public void writeRequestBody(OutputStream out) {
		try {
			QueryString queryStr = new QueryString();
			queryStr.add("username", username);
			queryStr.add("password", password);
			String body = queryStr.toString();
			out.write(body.getBytes());
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public String getRequestMethod() {
		return "GET";
	}
	
}