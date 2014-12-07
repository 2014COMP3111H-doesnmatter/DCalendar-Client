package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.gui.view.PrimCreateUserView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.User.UserQuery;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class LoginController 
extends EventSource implements Controller {
	private BaseLoginView view;
	private GenListener<LoginViewEvent> loginViewListener= new GenListener<LoginViewEvent>() {
		@Override
		public void fireEvent(LoginViewEvent e) {
			LoginViewEvent.Command command = e.getCommand();
			LoginControllerEvent ev = new LoginControllerEvent(this);
			
			if(command == LoginViewEvent.Command.LOGIN) {
				String username = e.getUsername();
				String password = e.getPassword();
				
				if(username.length() == 0) {
					ev.setCommand(LoginControllerEvent.Command.PROMPT_ERR);
					ev.setErrTitle("Invalid username");
					ev.setErrText("Username cannot be empty");
					fireList(nListener, ev);
					return;
				}
				
				if(password.length() == 0) {
					ev.setCommand(LoginControllerEvent.Command.PROMPT_ERR);
					ev.setErrTitle("Invalid password");
					ev.setErrText("Password cannot be empty");
					fireList(nListener, ev);
					return;
				}
				
				User.authUser(username, password, userQuerytListener);
				ev.setCommand(LoginControllerEvent.Command.LOGINPENDING);
				fireList(nListener, ev);
			}
			else if(command == LoginViewEvent.Command.SIGNUP) {
				CreateUserController ctrl = new CreateUserController(new PrimCreateUserView());
				ctrl.start();
			}
			else if(command == LoginViewEvent.Command.EXIT) {
				DCalendarApp.getApp().exitApp();
				view.dispose();
			}
		}
	};
	private GenListener<UserQuery> userQuerytListener = new GenListener<UserQuery>() {
		@Override
		public void fireEvent(UserQuery qry) {
			UserQuery.RtnValue rtnVal = qry.getRtnValue();
			if(rtnVal == UserQuery.RtnValue.AUTH_OK) {
				DCalendarApp app = DCalendarApp.getApp();
				User user = qry.getUser();
				app.setCurrentUser(user);
				
				// Pass control to another controller
				//System.out.println("hey~");
				app.switchController(new CalMainController(app.getViewManager().getCalMainView()));
				view.dispose();
				
			}
			else if(rtnVal == UserQuery.RtnValue.AUTH_FAIL) {
				LoginControllerEvent ev = new LoginControllerEvent(this, LoginControllerEvent.Command.PROMPT_ERR);
				ev.setErrText("Username or password incorrect");
				ev.setErrTitle("Authentication Failed");
				fireList(nListener, ev);
			}
			else if(rtnVal == UserQuery.RtnValue.NETWORK_ERR) {
				LoginControllerEvent ev = new LoginControllerEvent(this, LoginControllerEvent.Command.PROMPT_ERR);
				ev.setErrText("Network is not available, please check your connection");
				ev.setErrTitle("Connection Failed");
				fireList(nListener, ev);
			}
			else if(rtnVal == UserQuery.RtnValue.UNKNOWN_ERR) {
				LoginControllerEvent ev = new LoginControllerEvent(this, LoginControllerEvent.Command.PROMPT_ERR);
				ev.setErrText("Oops");
				ev.setErrTitle("Unknown Error");
				fireList(nListener, ev);
			}
		}
	};
	private List<GenListener<LoginControllerEvent>> nListener = new ArrayList<GenListener<LoginControllerEvent>>();
	
	public LoginController(BaseLoginView view) {
		setView(view);
	}

	public BaseLoginView getView() {
		return view;
	}

	public void setView(BaseLoginView view) {
		this.view = view;
		this.view.addLoginEventListener(loginViewListener);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addLoginEventListener(view);
	}

	public void addLoginEventListener(GenListener<LoginControllerEvent> listener) {
		nListener.add(listener);
	}
	
	public void start() {
		LoginControllerEvent e = new LoginControllerEvent(this);
		e.setCommand(LoginControllerEvent.Command.START);
		fireList(nListener, e);
	}
	
	// This method checks whether a string is a valid user name or password, as they can contains only letters and numbers
	public static boolean ValidString(String s)
	{
		char[] sChar = s.toCharArray();
		for(int i = 0; i < sChar.length; i++)
		{
			int sInt = (int)sChar[i];
			if(sInt < 48 || sInt > 122)
				return false;
			if(sInt > 57 && sInt < 65)
				return false;
			if(sInt > 90 && sInt < 97)
				return false;
		}
		return true;
	}
	
}