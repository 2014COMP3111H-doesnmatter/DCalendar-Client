package hkust.cse.calendar.gui.controller;

import java.util.EventObject;

public class DetailsControllerEvent extends EventObject {
	static public enum Command {
		START,
		LOGINPENDING,
		PROMPT_ERR
	};
	private Command command;
	
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
}