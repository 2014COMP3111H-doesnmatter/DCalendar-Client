package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.UserManagerControllerEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JDialog;

public abstract class BaseUserManagerView extends JDialog implements GenListener<UserManagerControllerEvent> {
	private List<GenListener<UserManagerViewEvent>> aListener = new ArrayList<GenListener<UserManagerViewEvent>>();

	public void addUserManagerEventListener(GenListener<UserManagerViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerUserManagerViewEvent(UserManagerViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class UserManagerViewEvent extends EventObject {
		public enum Command {
			EDIT,
			DELETE,
			CLOSE,
		};
		private Command command;
		private User user;
		public UserManagerViewEvent(Object source) {
			super(source);
		}
		
		public UserManagerViewEvent(Object source, Command command) {
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
	}

	@Override
	abstract public void fireEvent(UserManagerControllerEvent e);
}
