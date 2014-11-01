package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Venue;

import java.util.EventObject;
import java.util.Map;

public class DetailsControllerEvent extends EventObject {
	static public enum Command {
		START,
		UPDATE_TEXT
	};
	private Command command;
	private Appointment appt;
	private Map<Long, Venue> aVenue;
	
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

	public Map<Long, Venue> getaVenue() {
		return aVenue;
	}

	public void setaVenue(Map<Long, Venue> aVenue) {
		this.aVenue = aVenue;
	}
}