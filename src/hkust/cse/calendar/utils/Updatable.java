package hkust.cse.calendar.utils;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

abstract public class Updatable extends EventSource {
	
	protected List<GenListener<UpdatableEvent>> colListener = new ArrayList<GenListener<UpdatableEvent>>();
	
	public void addColEventListener(GenListener<UpdatableEvent> listener) {
		colListener.add(listener);
	}
	
	final static public class UpdatableEvent extends EventObject {
		static public enum Command {
			NETWORK_ERR,
			INFO_UPDATE
		};
		
		private Command command;
		
		public UpdatableEvent(Object source) {
			super(source);
		}
		
		public UpdatableEvent(Object source, Command command) {
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
}