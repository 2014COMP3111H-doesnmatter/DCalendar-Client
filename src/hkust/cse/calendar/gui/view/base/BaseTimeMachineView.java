package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.TimeMachineControllerEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;


public abstract class BaseTimeMachineView extends JFrame implements GenListener<TimeMachineControllerEvent>
{
	private List<GenListener<TimeMachineViewEvent>> aListener = new ArrayList<GenListener<TimeMachineViewEvent>>();

	public void addTimeMachineEventListener(GenListener<TimeMachineViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerTimeMachineViewEvent(TimeMachineViewEvent e) {
		EventSource.fireList(aListener, e);
	}
	
	
	
	public static class TimeMachineViewEvent extends EventObject {
		public enum Command {
			SETTIME,
			DONE
		};
		private Command command;
		private static long timestamp = 0L;
		
		
		public TimeMachineViewEvent(Object source) {
			super(source);
		}
		
		public TimeMachineViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}
		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
		
		public long getTime (){
			return timestamp;
		}
		
		public void setTime(long ts){
			timestamp = ts;
		}
	}

	@Override
	abstract public void fireEvent(TimeMachineControllerEvent e);
}

