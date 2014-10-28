package hkust.cse.calendar.collection;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

abstract public class BaseCollection extends EventSource {
	
	protected List<GenListener<CollectionEvent>> colListener = new ArrayList<GenListener<CollectionEvent>>();
	
	final static public class CollectionEvent extends EventObject {
		static public enum Command {
			NETWORK_ERR,
			INFO_UPDATE
		};
		
		private Command command;
		
		public CollectionEvent(Object source) {
			super(source);
		}
		
		public CollectionEvent(Object source, Command command) {
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