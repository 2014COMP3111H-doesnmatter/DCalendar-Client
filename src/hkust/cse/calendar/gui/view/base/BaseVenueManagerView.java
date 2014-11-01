package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.VenueManagerControllerEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JDialog;

public abstract class BaseVenueManagerView extends JDialog implements GenListener<VenueManagerControllerEvent> {
	private List<GenListener<VenueManagerViewEvent>> aListener = new ArrayList<GenListener<VenueManagerViewEvent>>();

	public void addVenueManagerEventListener(GenListener<VenueManagerViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerVenueManagerViewEvent(VenueManagerViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class VenueManagerViewEvent extends EventObject {
		public enum Command {
			// TODO Auto-generated enum
			ADD_VENUE,
		};
		private Command command;
		public String name;
		public VenueManagerViewEvent(Object source) {
			super(source);
		}
		
		public VenueManagerViewEvent(Object source, Command command) {
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

	@Override
	abstract public void fireEvent(VenueManagerControllerEvent e);
}
