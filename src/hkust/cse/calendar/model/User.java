package hkust.cse.calendar.model;

import hkust.cse.calendar.api.welcome.InitiateRemovalAPI;
import hkust.cse.calendar.api.welcome.ListUserAPI;
import hkust.cse.calendar.api.welcome.LoginAPI;
import hkust.cse.calendar.api.welcome.LogoutAPI;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseModel implements Comparable<User> {
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
	
	public void logout() {
		LogoutAPI api = new LogoutAPI();
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	public void remove(final GenListener<UserRemoveQuery> listener) {
		InitiateRemovalAPI api = new InitiateRemovalAPI(this);
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				UserRemoveQuery qry = new UserRemoveQuery(this);
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setRtnValue(UserRemoveQuery.RtnValue.OK);
					}
					else if(rtnCode == -1) {
						qry.setRtnValue(UserRemoveQuery.RtnValue.NETWORK_ERR);
					}
					else {
						qry.setRtnValue(UserRemoveQuery.RtnValue.UNKNOWN_ERR);
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
	
	static public List<User> fromJSONArray(JSONArray arr) throws JSONException {
		int i;
		List<User> aUser = new ArrayList<User>();
		for(i = 0; i < arr.length(); i++) {
			User u = new User(arr.getJSONObject(i));
			aUser.add(u);
		}
		return aUser;
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
	
	static public void listUser(final GenListener<UserListQuery> listener) {
		ListUserAPI api = new ListUserAPI();
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				int i;
				UserListQuery qry = new UserListQuery(this);
				
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						qry.setRtnValue(UserListQuery.RtnValue.LIST_OK);
						JSONArray jsonUser = json.getJSONArray("aUser");
						List<User> aUser = new ArrayList<User>();
						for(i = 0; i < jsonUser.length(); i++) {
							User user = new User(jsonUser.getJSONObject(i));
							aUser.add(user);
						}
						qry.setaUser(aUser);
					}
					else if(rtnCode == 201 || rtnCode == 202) {
						qry.setRtnValue(UserListQuery.RtnValue.AUTH_FAIL);
					}
					else if(rtnCode == -1) {
						qry.setRtnValue(UserListQuery.RtnValue.NETWORK_ERR);
					}
					else {
						qry.setRtnValue(UserListQuery.RtnValue.UNKNOWN_ERR);
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

	static public class UserRemoveQuery extends EventObject {
		public enum RtnValue {
			OK,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private RtnValue rtnValue;
		private User user;
		
		public UserRemoveQuery(Object source) {
			super(source);
		}
		
		public UserRemoveQuery(Object source, RtnValue rtnValue) {
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
	
	static public class UserListQuery extends EventObject {
		public enum RtnValue {
			LIST_OK,
			AUTH_FAIL,
			NETWORK_ERR,
			UNKNOWN_ERR
		};
		private RtnValue rtnValue;
		private List<User> aUser;
		
		public UserListQuery(Object source) {
			super(source);
		}
		
		public UserListQuery(Object source, RtnValue rtnValue) {
			super(source);
			this.setRtnValue(rtnValue);
		}

		public RtnValue getRtnValue() {
			return rtnValue;
		}

		public void setRtnValue(RtnValue rtnValue) {
			this.rtnValue = rtnValue;
		}

		public List<User> getaUser() {
			return aUser;
		}

		public void setaUser(List<User> aUser) {
			this.aUser = aUser;
		}

		
	}
	
	@Override
	public int compareTo(User o) {
		return (int)(this.id - o.id);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof User)) return false;
		return this.id == ((User)o).id;
	}
}
