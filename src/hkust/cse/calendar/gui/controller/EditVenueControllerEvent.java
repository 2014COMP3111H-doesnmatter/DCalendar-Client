package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.Venue;

import java.util.EventObject;

public class EditVenueControllerEvent extends EventObject {
	static public enum Command {
		START,
	};
	private Command command;
	private Venue venue;
	
	public EditVenueControllerEvent(final Object source) {
		super(source);
	}
	
	public EditVenueControllerEvent(final Object source, Command command) {
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
