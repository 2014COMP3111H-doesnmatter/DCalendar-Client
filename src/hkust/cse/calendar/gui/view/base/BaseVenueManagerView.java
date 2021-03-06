package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.VenueManagerControllerEvent;
import hkust.cse.calendar.model.Venue;
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
			CREATE,
			EDIT,
			DELETE,
			CLOSE,
		};
		private Command command;
		private Venue venue;
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

		public Venue getVenue() {
			return venue;
		}

		public void setVenue(Venue venue) {
			this.venue = venue;
		}
	}

	@Override
	abstract public void fireEvent(VenueManagerControllerEvent e);
}
