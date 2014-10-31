package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.{placeholder}ControllerEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.{placeholder_ex};

public abstract class Base{placeholder}View extends {placeholder_ex} implements GenListener<{placeholder}ControllerEvent> {
	private List<GenListener<{placeholder}ViewEvent>> aListener = new ArrayList<GenListener<{placeholder}ViewEvent>>();

	public void add{placeholder}EventListener(GenListener<{placeholder}ViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void trigger{placeholder}ViewEvent({placeholder}ViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class {placeholder}ViewEvent extends EventObject {
		public enum Command {
			// TODO Auto-generated enum
			PLACEHOLDER,
		};
		private Command command;
		public {placeholder}ViewEvent(Object source) {
			super(source);
		}
		
		public {placeholder}ViewEvent(Object source, Command command) {
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

	@Override
	abstract public void fireEvent({placeholder}ControllerEvent e);
}
