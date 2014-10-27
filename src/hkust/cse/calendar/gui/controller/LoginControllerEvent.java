package hkust.cse.calendar.gui.controller;

import java.util.EventObject;


public class LoginControllerEvent extends EventObject {
	static public enum Command {
		START,
		LOGINPENDING,
		PROMPT_ERR
	};
	private Command command;
	private String errTitle;
	private String errText;
	
	public LoginControllerEvent(final Object source) {
		super(source);
	}
	
	public LoginControllerEvent(final Object source, Command command) {
		super(source);
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(final Command command) {
		this.command = command;
	}

	public String getErrTitle() {
		return errTitle;
	}

	public void setErrTitle(final String errTitle) {
		this.errTitle = errTitle;
	}

	public String getErrText() {
		return errText;
	}

	public void setErrText(final String errText) {
		this.errText = errText;
	}
}