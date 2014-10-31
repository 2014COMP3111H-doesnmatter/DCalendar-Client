package hkust.cse.calendar.gui.controller;

import java.util.EventObject;

public class ApptSchedulerControllerEvent extends EventObject {
	static public enum Command {
		// TODO Auto-generated enum
		START,
		SAVE_PENDING,
		PROMPT_ERR,
	};
	private Command command;
	private String errTitle;
	private String errText;
	
	public ApptSchedulerControllerEvent(final Object source) {
		super(source);
	}
	
	public ApptSchedulerControllerEvent(final Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getErrTitle() {
		return errTitle;
	}

	public void setErrTitle(String errTitle) {
		this.errTitle = errTitle;
	}

	public String getErrText() {
		return errText;
	}

	public void setErrText(String errText) {
		this.errText = errText;
	}

}
