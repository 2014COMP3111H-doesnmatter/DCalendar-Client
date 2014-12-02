package hkust.cse.calendar.gui.controller;

import java.util.EventObject;

public class CalMainControllerEvent extends EventObject {
	static public enum Command {
		START,
		UPDATE_INFO,
	};
	private Command command;
	private String username;
	private long selectedDay;
	
	CalMainControllerEvent(Object source) {
		super(source);
	}
	
	CalMainControllerEvent(Object source, Command command) {
		super(source);
		this.setCommand(command);
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(long selectedDay) {
		this.selectedDay = selectedDay;
	}
	
}