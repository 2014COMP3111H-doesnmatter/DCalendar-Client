package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.model.Notification;
import hkust.cse.calendar.model.User;

import java.util.EventObject;
import java.util.List;

public class CalMainControllerEvent extends EventObject {
	static public enum Command {
		START,
		UPDATE_INFO,
	};
	private Command command;
	private String username;
	private User user;
	private long selectedDay;
	private List<Notification> aNotification;
	
	CalMainControllerEvent(Object source) {
		super(source);
	}
	
	CalMainControllerEvent(Object source, Command command) {
		super(source);
		this.setCommand(command);
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(long selectedDay) {
		this.selectedDay = selectedDay;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Notification> getaNotification() {
		return aNotification;
	}

	public void setaNotification(List<Notification> aNotification) {
		this.aNotification = aNotification;
	}
	
}