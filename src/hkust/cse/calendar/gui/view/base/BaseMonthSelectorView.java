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
			UPDATE,
			UPDATE_YEAR,
			UPDATE_MONTH,
			PREV_YEAR,
			NEXT_YEAR
		};
		private Command command;
		private int year;
		/**
		 * From 0-11, as in Date
		 */
		private int month;
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

		/**
		 * Full year in this event. Equivalent to Date.getYear() + 1900
		 * @see java.util.Date#getYear
		 * @return the month
		 */
		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		/**
		 * A number in 0-11 with 0=January, 11=December
		 * @see java.util.Date#getMonth
		 * @return the month
		 */
		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}
	}

	@Override
	abstract public void fireEvent(MonthSelectorControllerEvent e);
}
