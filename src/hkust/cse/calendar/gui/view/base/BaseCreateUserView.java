package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.CreateUserControllerEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JDialog;

public abstract class BaseCreateUserView extends JDialog implements GenListener<CreateUserControllerEvent> {
	private List<GenListener<CreateUserViewEvent>> aListener = new ArrayList<GenListener<CreateUserViewEvent>>();

	public void addCreateUserEventListener(GenListener<CreateUserViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerCreateUserViewEvent(CreateUserViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class CreateUserViewEvent extends EventObject {
		public enum Command {
			// TODO Auto-generated enum
			CREATE,
			CLOSE
		};
		private Command command;
		private User user;
		private String password;
		public CreateUserViewEvent(Object source) {
			super(source);
		}
		
		public CreateUserViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}
		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	@Override
	abstract public void fireEvent(CreateUserControllerEvent e);
}
