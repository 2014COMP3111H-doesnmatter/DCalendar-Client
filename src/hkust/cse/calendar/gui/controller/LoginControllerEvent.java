package hkust.cse.calendar.gui.controller;

import java.util.EventObject;


public class LoginControllerEvent extends EventObject {
	private String command;
	private String errTitle;
	private String errText;
	
	public LoginControllerEvent(final Object source) {
		super(source);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(final String command) {
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