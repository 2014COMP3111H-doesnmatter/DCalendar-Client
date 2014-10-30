package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.ApptListControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent.Command;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseApptListView extends JPanel implements GenListener<ApptListControllerEvent>
{
	public static class ApptListViewEvent extends EventObject {
		public enum Command {
			NEW_APPOINTMENT,
			DELETE_APPOITNMENT,
			EDIT_APPOINTMENT,
			DESCRIB_APPOINTMENT
		};
		private Command command;
		public ApptListViewEvent(Object source) {
			super(source);
		}
		
		public ApptListViewEvent(Object source, Command command) {
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
	
	public List<GenListener<ApptListViewEvent>> aListener = new ArrayList<GenListener<ApptListViewEvent>>();
	
	@Override
	abstract public void fireEvent(ApptListControllerEvent e);
	
	final protected void triggerApptListViewEvent(ApptListViewEvent e) {
		EventSource.fireList(this.aListener, e);
	}
}
