package hkust.cse.calendar.gui.view.base;

import hkust.cse.calendar.gui.controller.ApptSchedulerControllerEvent;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

public abstract class BaseApptSchedulerView extends JDialog implements GenListener<ApptSchedulerControllerEvent> {
	private List<GenListener<ApptSchedulerViewEvent>> aListener = new ArrayList<GenListener<ApptSchedulerViewEvent>>();
	protected Map<Long, Venue> aVenue;
	protected Appointment appt;
	protected List<User> aUser;
	
	public void addApptSchedulerEventListener(GenListener<ApptSchedulerViewEvent> listener) {
		aListener.add(listener);
	}
	
	final protected void triggerApptSchedulerViewEvent(ApptSchedulerViewEvent e) {
		EventSource.fireList(aListener, e);
	}
	
	final public void setaVenue(Map<Long, Venue> aVenue) {
		this.aVenue = aVenue;
		updateVenueList();
	}
	
	abstract protected void updateVenueList();
	
	final public void setAppt(Appointment appt) {
		this.appt = appt;
		updateAppt();
	}
	
	abstract protected void updateUserList();
	
	final public void setUserList(List<User> aUser) {
		this.aUser = aUser;
		updateUserList();
		
	}
	
	abstract protected void updateAppt();

	public static class ApptSchedulerViewEvent extends EventObject {
		public enum Command {
			SAVE,
			RECOMMEND,
			CLOSE,
		};
		private Command command;
		private Appointment appt;
		private List<User> aUser;
		public ApptSchedulerViewEvent(Object source) {
			super(source);
		}
		
		public ApptSchedulerViewEvent(Object source, Command command) {
			super(source);
			this.command = command;
		}
		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public Appointment getAppt() {
			return appt;
		}

		public void setAppt(Appointment appt) {
			this.appt = appt;
		}

		public List<User> getaUser() {
			return aUser;
		}

		public void setaUser(List<User> aUser) {
			this.aUser = aUser;
		}
	}

	@Override
	abstract public void fireEvent(ApptSchedulerControllerEvent e);

	abstract public void setStartTime(long startTime);

}
