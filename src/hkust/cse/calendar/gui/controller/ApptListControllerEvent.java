package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.gui.controller.LoginControllerEvent.Command;

import java.util.EventObject;

public class ApptListControllerEvent extends EventObject
{
	static public enum Command {
		START,
	};
	private Command command;
	
	public ApptListControllerEvent(final Object source) {
		super(source);
	}
	
	public ApptListControllerEvent(final Object source, Command command) {
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
