package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;

abstract public class BaseCalMainView extends JFrame implements GenListener<LoginControllerEvent> {
	private List<GenListener<CalMainViewEvent>> nListener = new ArrayList<GenListener<CalMainViewEvent>>();

	public void addLoginEventListener(GenListener<CalMainViewEvent> listener) {
		nListener.add(listener);
	}
	
	final protected void triggerLoginViewEvent(CalMainViewEvent e) {
		EventSource.fireList(nListener, e);
	}
	
	abstract public void fireEvent(LoginControllerEvent e);
	
	static public class CalMainViewEvent extends EventObject {
		public enum Command {
			EXIT
		};
		private Command command;
		private String username;
		private String password;
		
		public CalMainViewEvent(Object source) {
			super(source);
		}
		
		public CalMainViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
		
	}

}