package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.CreateVenueControllerEvent;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JDialog;

public abstract class BaseCreateVenueView extends JDialog implements GenListener<CreateVenueControllerEvent> {
	private List<GenListener<CreateVenueViewEvent>> aListener = new ArrayList<GenListener<CreateVenueViewEvent>>();

	public void addCreateVenueEventListener(GenListener<CreateVenueViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerCreateVenueViewEvent(CreateVenueViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class CreateVenueViewEvent extends EventObject {
		public enum Command {
			CREATE,
			CLOSE,
		};
		private Command command;
		private Venue venue;
		public CreateVenueViewEvent(Object source) {
			super(source);
		}
		
		public CreateVenueViewEvent(Object source, Command command) {
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
	abstract public void fireEvent(CreateVenueControllerEvent e);
}
