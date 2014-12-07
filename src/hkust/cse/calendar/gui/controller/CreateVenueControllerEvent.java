package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.Venue;

import java.util.EventObject;

public class CreateVenueControllerEvent extends EventObject {
	static public enum Command {
		START,
	};
	private Command command;
	
	public CreateVenueControllerEvent(final Object source) {
		super(source);
	}
	
	public CreateVenueControllerEvent(final Object source, Command command) {
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
