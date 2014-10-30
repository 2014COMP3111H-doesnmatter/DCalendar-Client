package hkust.cse.calendar.gui.controller;

import java.util.EventObject;

public class MonthSelectorControllerEvent extends EventObject
{
	static public enum Command {
		UPDATE
	};
	private Command command;
	private long selectedDay;
	
	public MonthSelectorControllerEvent(final Object source) {
		super(source);
	}
	
	public MonthSelectorControllerEvent(final Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public long getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(long selectedDay) {
		this.selectedDay = selectedDay;
	}

}
