package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.User;

import java.util.EventObject;
import java.util.List;

public class UserManagerControllerEvent extends EventObject {
	static public enum Command {
		INFO_UPDATE,
		START,
	};
	private Command command;
	private List<User> aUser;
	
	public UserManagerControllerEvent(final Object source) {
		super(source);
	}
	
	public UserManagerControllerEvent(final Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public List<User> getaUser() {
		return aUser;
	}

	public void setaUser(List<User> aUser) {
		this.aUser = aUser;
	}

}
