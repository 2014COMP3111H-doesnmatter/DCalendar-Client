package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.CalMainControllerEvent;
import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;

abstract public class BaseCalMainView extends JFrame implements GenListener<CalMainControllerEvent> {
	private List<GenListener<CalMainViewEvent>> nListener = new ArrayList<GenListener<CalMainViewEvent>>();

	public void addCalMainEventListener(GenListener<CalMainViewEvent> listener) {
		nListener.add(listener);
	}
	
	final protected void triggerCalMainViewEvent(CalMainViewEvent e) {
		EventSource.fireList(nListener, e);
	}
	
	abstract public void setCalMonthView(BaseCalMonthView monthView);
	abstract public void setApptListView(BaseApptListView apptListView);
	abstract public void setMonthSelectView(BaseMonthSelectorView monthSelectorView);
	
	abstract public void fireEvent(CalMainControllerEvent e);
	
	static public class CalMainViewEvent extends EventObject {
		public enum Command {
			LOGOUT,
			EXIT,
			MANUAL_SCHEDULE,
			TIME_MACHINE,
			VENUE,
			CHANGE_PROFILE,
			USER,
		};
		private Command command;
		
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