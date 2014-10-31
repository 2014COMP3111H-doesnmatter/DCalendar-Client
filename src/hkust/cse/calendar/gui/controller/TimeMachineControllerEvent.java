package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.gui.controller.LoginControllerEvent.Command;

import java.util.EventObject;

public class TimeMachineControllerEvent extends EventObject
{
	static public enum Command {
		START,
		SETTIMEPENDING,
		GETTIMEPENDING
	};
	private Command command;
	
	public TimeMachineControllerEvent(final Object source) {
		super(source);
	}
	
	public TimeMachineControllerEvent(final Object source, Command command) {
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
