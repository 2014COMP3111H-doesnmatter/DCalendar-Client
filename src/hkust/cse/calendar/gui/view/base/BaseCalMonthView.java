package hkust.cse.calendar.gui.view.base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import hkust.cse.calendar.gui.controller.CalMonthControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseDetailsView.DetailsViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import javax.swing.JPanel;
import javax.swing.JTable;

abstract public class BaseCalMonthView extends JPanel implements GenListener<CalMonthControllerEvent> {
	List<GenListener<CalMonthViewEvent>> nListener = new ArrayList<GenListener<CalMonthViewEvent>>();

	public void addMonthEventListener(GenListener<CalMonthViewEvent> listener) {
		nListener.add(listener);
	}
	
	final protected void triggerMonthViewEvent(CalMonthViewEvent e) {
		EventSource.fireList(nListener, e);
	}
	
	abstract public void fireEvent(CalMonthControllerEvent e);
	
	final static public class CalMonthViewEvent extends EventObject {
		static public enum Command {
			SWITCH_DATE
		};
		private Command command;
		private int date;
		
		public CalMonthViewEvent(Object source) {
			super(source);
		}
		
		public CalMonthViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public int getDate() {
			return date;
		}

		public void setDate(int date) {
			this.date = date;
		}
	}
}