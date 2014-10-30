package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.gui.controller.LoginControllerEvent.Command;

import java.util.EventObject;

public class TimeMachineControllerEvent extends EventObject
{
	static public final class Command {
		public static final int START = 0;
	};
	private int command;
	
	public TimeMachineControllerEvent(final Object source) {
		super(source);
	}
	
	public TimeMachineControllerEvent(final Object source, int command) {
		super(source);
		this.command = command;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}
}
