package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.Appointment;

import java.util.EventObject;

public class DetailsControllerEvent extends EventObject {
	static public enum Command {
		START,
		UPDATE_TEXT
	};
	private Command command;
	private Appointment appt;
	
	public DetailsControllerEvent(final Object source) {
		super(source);
	}
	
	public DetailsControllerEvent(final Object source, Command command) {
		super(source);
		this.setCommand(command);
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Appointment getAppt() {
		return appt;
	}

	public void setAppt(Appointment appt) {
		this.appt = appt;
	}
}