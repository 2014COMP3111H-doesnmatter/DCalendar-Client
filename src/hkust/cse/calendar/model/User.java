package hkust.cse.calendar.model;

import hkust.cse.calendar.api.welcome.LoginAPI;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.EventObject;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseModel {
	private String username;
	private String fullname;
	private String email;
	private boolean isAdmin;

	public User() {
		
	}
	
	public User(JSONObject json) throws JSONException {
		id = json.getLong("id");
		username = json.getString("username");
		fullname = json.getString("fullName");
		email = json.getString("email");
		isAdmin = json.getBoolean("isAdmin");
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	static public void authUser(final String username, final String password, final GenListener<UserQuery> listener) {
		LoginAPI api = new LoginAPI(username, password);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				UserQuery qry = new UserQuery(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setRtnValue(UserQuery.RtnValue.AUTH_OK);
						JSONObject userJson = json.getJSONObject("user");
						User user = new User(userJson);
						qry.setUser(user);
					}
					else if(rtnCode == 201 || rtnCode == 202) {
						qry.setRtnValue(UserQuery.RtnValue.AUTH_FAIL);
					}
					else if(rtnCode == -1) {
						qry.setRtnValue(UserQuery.RtnValue.NETWORK_ERR);
					}
					else {
						qry.setRtnValue(UserQuery.RtnValue.UNKNOWN_ERR);
					}
					fireTo(listener, qry);
				} catch(JSONException ex) {
					ex.printStackTrace();
				}
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Override
	public String toString() {
		return this.username;
	}

	static public class UserQuery extends EventObject {
		public enum RtnValue {
			AUTH_OK,
			AUTH_FAIL,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private RtnValue rtnValue;
		private User user;
		
		public UserQuery(Object source) {
			super(source);
		}
		
		public UserQuery(Object source, RtnValue rtnValue) {
			super(source);
			this.setRtnValue(rtnValue);
		}

		public RtnValue getRtnValue() {
			return rtnValue;
		}

		public void setRtnValue(RtnValue rtnValue) {
			this.rtnValue = rtnValue;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
	}
}
