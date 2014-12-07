package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.User;

import java.util.EventObject;

public class EditUserControllerEvent extends EventObject {
	static public enum Command {
		START,
	};
	private Command command;
	private User user;
	
	public EditUserControllerEvent(final Object source) {
		super(source);
	}
	
	public EditUserControllerEvent(final Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
