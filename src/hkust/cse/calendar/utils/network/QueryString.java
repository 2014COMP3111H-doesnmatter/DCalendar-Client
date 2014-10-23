package hkust.cse.calendar.utils.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QueryString {
	StringBuffer query = new StringBuffer();
	static String ENCODING = "UTF-8";
	
	public QueryString() { }
	
	public QueryString(String name, String value) {
		add(name, value);
	}
	
	public void add(String name, String value) {
		if(query.length() > 0) {
			query.append("&");
		}
		try {
			query.append(URLEncoder.encode(name, ENCODING));
			query.append("=");
			query.append(URLEncoder.encode(value, ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Broken VM does not support " + ENCODING);
		}
	}
	
	public String getQuery() {
		return query.toString();
	}
	
	public String toString() {
		return getQuery();
	}
}