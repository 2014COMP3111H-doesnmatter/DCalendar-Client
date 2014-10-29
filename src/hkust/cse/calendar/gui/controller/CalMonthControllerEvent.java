package hkust.cse.calendar.gui.controller;

import java.util.EventObject;

public class CalMonthControllerEvent extends EventObject {
	static public enum Command {
		UPDATE_ALL,
		UPDATE_TODAY,
		UPDATE_OCCUPIED,
		
		/**
		 * Pop up events
		 */
		NETWORK_ERR,
		UNKNOWN_ERR
	};
	
	private Command command;
	private long startOfMonth;
	private long today;
	private boolean[] occupied;
	
	public CalMonthControllerEvent(Object source) {
		super(source);
	}
	
	public CalMonthControllerEvent(Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public long getStartOfMonth() {
		return startOfMonth;
	}

	public void setStartOfMonth(long startOfMonth) {
		this.startOfMonth = startOfMonth;
	}

	public long getToday() {
		return today;
	}

	public void setToday(long today) {
		this.today = today;
	}

	public boolean[] getOccupied() {
		return occupied;
	}

	public void setOccupied(boolean[] occupied) {
		this.occupied = occupied;
	}

}