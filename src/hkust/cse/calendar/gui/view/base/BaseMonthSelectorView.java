package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.MonthSelectorControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView.MonthSelectorViewEvent.Command;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JPanel;

public abstract class BaseMonthSelectorView extends JPanel implements GenListener<MonthSelectorControllerEvent>
{
	private List<GenListener<MonthSelectorViewEvent>> aListener = new ArrayList<GenListener<MonthSelectorViewEvent>>();

	public void addMonthSelectorEventListener(GenListener<MonthSelectorViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerMonthSelectorViewEvent(MonthSelectorViewEvent e) {
		EventSource.fireList(aListener, e);
	}

	public static class MonthSelectorViewEvent extends EventObject {
		public enum Command {
			// TODO Auto-generated enum
			PLACEHOLDER,
		};
		private Command command;
		public MonthSelectorViewEvent(Object source) {
			super(source);
		}
		
		public MonthSelectorViewEvent(Object source, Command command) {
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
	abstract public void fireEvent(MonthSelectorControllerEvent e);
}
