package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.DetailsControllerEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;

abstract public class BaseDetailsView extends JFrame implements GenListener<DetailsControllerEvent> {
	private List<GenListener<DetailsViewEvent>> nListener = new ArrayList<GenListener<DetailsViewEvent>>();

	public void addDetailsEventListener(GenListener<DetailsViewEvent> listener) {
		nListener.add(listener);
	}
	
	final protected void triggerDetailsViewEvent(DetailsViewEvent e) {
		EventSource.fireList(nListener, e);
	}
	
	abstract public void fireEvent(DetailsControllerEvent e);
	
	static public class DetailsViewEvent extends EventObject {
		public enum Command {
			ACCEPT,
			REJECT,
			EXIT
		};
		private Command command;
		
		public DetailsViewEvent(Object source) {
			super(source);
		}
		
		public DetailsViewEvent(Object source, Command command) {
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