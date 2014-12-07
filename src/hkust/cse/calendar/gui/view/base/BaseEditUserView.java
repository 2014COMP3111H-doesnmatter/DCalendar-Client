package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.EditUserControllerEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JDialog;

public abstract class BaseEditUserView extends JDialog implements GenListener<EditUserControllerEvent> {
	private List<GenListener<EditUserViewEvent>> aListener = new ArrayList<GenListener<EditUserViewEvent>>();

	public void addEditUserEventListener(GenListener<EditUserViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerEditUserViewEvent(EditUserViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class EditUserViewEvent extends EventObject {
		public enum Command {
			EDIT,
			CLOSE,
		};
		private Command command;
		private User user;
		private String password;
		public EditUserViewEvent(Object source) {
			super(source);
		}
		
		public EditUserViewEvent(Object source, Command command) {
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
	abstract public void fireEvent(EditUserControllerEvent e);
}
