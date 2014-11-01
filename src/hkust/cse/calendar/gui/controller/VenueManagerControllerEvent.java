package hkust.cse.calendar.gui.controller;

import java.util.EventObject;

public class VenueManagerControllerEvent extends EventObject {
	static public enum Command {
		// TODO Auto-generated enum
		REFRESH,
	};
	private Command command;
	public String[] aVenueName;
	
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

}
