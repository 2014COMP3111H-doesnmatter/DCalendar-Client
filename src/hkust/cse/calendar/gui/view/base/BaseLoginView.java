package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

abstract public class BaseLoginView extends BaseView implements GenListener<LoginControllerEvent> {
	private List<GenListener<LoginViewEvent>> nListener = new ArrayList<GenListener<LoginViewEvent>>();

	public void addLoginEventListener(GenListener<LoginViewEvent> listener) {
		nListener.add(listener);
	}
	
	final protected void triggerLoginViewEvent(LoginViewEvent e) {
		fireList(nListener, e);
	}
	
	public void fireEvent(LoginControllerEvent e) {
		System.out.println(e.getCommand());
	}
	
	static public class LoginViewEvent extends EventObject {
		public enum Command {
			LOGIN,
			SIGNUP,
			EXIT
		};
		private Command command;
		private String username;
		private String password;
		
		public LoginViewEvent(Object source) {
			super(source);
		}
		
		public LoginViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}		
	}

}