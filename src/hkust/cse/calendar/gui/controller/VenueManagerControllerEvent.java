package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.Venue;

import java.util.EventObject;
import java.util.List;

public class VenueManagerControllerEvent extends EventObject {
	static public enum Command {
		// TODO Auto-generated enum
		START,
		INFO_UPDATE,
	};
	private Command command;
	private List<Venue> aVenue;
	
	public VenueManagerControllerEvent(final Object source) {
		super(source);
	}
	
	public VenueManagerControllerEvent(final Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public List<Venue> getaVenue() {
		return aVenue;
	}

	public void setaVenue(List<Venue> aVenue) {
		this.aVenue = aVenue;
	}

}
