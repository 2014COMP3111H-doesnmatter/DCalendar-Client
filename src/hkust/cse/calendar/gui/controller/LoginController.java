package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.api.welcome.LoginAPI;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class LoginController 
extends EventSource {
	private BaseLoginView view;
	GenListener<LoginViewEvent> loginViewListener= new GenListener<LoginViewEvent>() {
		@Override
		public void fireEvent(LoginViewEvent e) {
			String command = e.getCommand();
			LoginControllerEvent ev = new LoginControllerEvent(this);
			
			if(command.equals("Login")) {
				String username = e.getUsername();
				String password = e.getPassword();
				LoginAPI api = new LoginAPI(username, password);
				api.addDoneListener(apiRequestListener);
				Thread thrd = new Thread(new APIHandler(api));
				thrd.start();
				
				ev.setCommand("LoginPending");
			}
			else if(command.equals("")) {
				
			}
			else {
				
			}
			fireList(nListener, ev);
		}
	};
	GenListener<APIRequestEvent> apiRequestListener = new GenListener<APIRequestEvent>() {
		@Override
		public void fireEvent(APIRequestEvent e) {
			System.out.println(e.getJSON().toString());
		}
	};
	private List<GenListener<LoginControllerEvent>> nListener = new ArrayList<GenListener<LoginControllerEvent>>();
	
	public LoginController(BaseLoginView view) {
		this.view = view;
		this.view.addLoginEventListener(loginViewListener);
		this.addLoginEventListener(view);
		
	}

	public BaseLoginView getView() {
		return view;
	}

	public void setView(BaseLoginView view) {
		this.view = view;
	}

	public void addLoginEventListener(GenListener<LoginControllerEvent> listener) {
		nListener.add(listener);
	}
	
	public void start() {
		LoginControllerEvent e = new LoginControllerEvent(this);
		e.setCommand("START");
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