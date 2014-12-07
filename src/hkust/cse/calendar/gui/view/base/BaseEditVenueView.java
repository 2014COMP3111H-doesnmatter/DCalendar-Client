package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.EditVenueControllerEvent;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JDialog;

public abstract class BaseEditVenueView extends JDialog implements GenListener<EditVenueControllerEvent> {
	private List<GenListener<EditVenueViewEvent>> aListener = new ArrayList<GenListener<EditVenueViewEvent>>();

	public void addEditVenueEventListener(GenListener<EditVenueViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerEditVenueViewEvent(EditVenueViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class EditVenueViewEvent extends EventObject {
		public enum Command {
			EDIT,
			CLOSE,
		};
		private Command command;
		private Venue venue;
		public EditVenueViewEvent(Object source) {
			super(source);
		}
		
		public EditVenueViewEvent(Object source, Command command) {
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
	abstract public void fireEvent(EditVenueControllerEvent e);
}
