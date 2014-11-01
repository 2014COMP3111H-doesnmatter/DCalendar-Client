package hkust.cse.calendar.gui.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hkust.cse.calendar.collection.AppointmentCollection;
import hkust.cse.calendar.collection.AppointmentCollection.MonthAppointmentQuery;
import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.domainModel.CalMainModel.CalMainModelEvent;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView.CalMonthViewEvent;
import hkust.cse.calendar.utils.DateTimeHelper;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;

public class CalMonthController extends EventSource implements Controller {
	private BaseCalMonthView view;
	private CalMainModel model;
	private AppointmentCollection aAppt;
	private List<GenListener<CalMonthControllerEvent>> nListener = new ArrayList<GenListener<CalMonthControllerEvent>>();
	
	private GenListener<CalMonthViewEvent> viewListener = new GenListener<CalMonthViewEvent>() {
		@Override
		public void fireEvent(CalMonthViewEvent e) {
			if(e.getCommand() == CalMonthViewEvent.Command.SWITCH_DATE) {
				model.setDate(e.getDate());
			}
		}
	};
	
	private GenListener<CalMainModelEvent> modelListener = new GenListener<CalMainModelEvent>() {

		@Override
		public void fireEvent(CalMainModelEvent e) {
			CalMainModelEvent.Command command = e.getCommand();
			if(command == CalMainModelEvent.Command.UPDATE) {
				//Change the view first then fetch occupied

				long newStamp = model.getSelectedDayStamp();
				//TODO: Adapt to Timemachine
				long today = (new Date()).getTime();
				boolean[] occupied = new boolean[DateTimeHelper.MAX_DAY_IN_MONTH];
				Arrays.fill(occupied, false);
				
				CalMonthControllerEvent ev = new CalMonthControllerEvent(this, CalMonthControllerEvent.Command.UPDATE_ALL);
				ev.setToday(today);
				ev.setStartOfMonth(DateTimeHelper.getStartOfMonth(newStamp));
				ev.setOccupied(occupied);
				fireList(nListener, ev);
				
				queryOccupied();
			}
		}
		
	};
	
	private GenListener<UpdatableEvent> collectionListener = new GenListener<UpdatableEvent>() {

		@Override
		public void fireEvent(UpdatableEvent e) {
			UpdatableEvent.Command command = e.getCommand();
			if(command == UpdatableEvent.Command.INFO_UPDATE) {
				//Let's find out what's new
				queryOccupied();
			}
			
		}
		
	};
	
	private void queryOccupied() {
		aAppt.getOccupiedInMonth(new GenListener<MonthAppointmentQuery>() {

			@Override
			public void fireEvent(MonthAppointmentQuery e) {
				MonthAppointmentQuery.Command command = e.getCommand();
				CalMonthControllerEvent ev = new CalMonthControllerEvent(this);
				if(command == MonthAppointmentQuery.Command.OK) {
					boolean[] occupied = e.occupied.clone();
					ev.setCommand(CalMonthControllerEvent.Command.UPDATE_OCCUPIED);
					ev.setOccupied(occupied);
					
				}
				else if(command == MonthAppointmentQuery.Command.NETWORK_ERR) {
					//Pop up
					ev.setCommand(CalMonthControllerEvent.Command.NETWORK_ERR);
				}
				else if(command == MonthAppointmentQuery.Command.UNKNOWN_ERR) {
					ev.setCommand(CalMonthControllerEvent.Command.UNKNOWN_ERR);
					//Pop up
				}
				fireList(nListener, ev);
			}
			
		});
	}
	
	public CalMonthController(BaseCalMonthView view) {
		setView(view);
		setModel(CalMainModel.getInstance());
		setCollection(AppointmentCollection.getInstance());
	}

	public BaseCalMonthView getView() {
		return view;
	}

	public void setView(BaseCalMonthView view) {
		this.view = view;
		this.view.addMonthEventListener(viewListener);
		this.addCalMonthEventListener(view);
	}
	
	public CalMainModel getModel() {
		return model;
	}

	public void setModel(CalMainModel model) {
		this.model = model;
		this.model.addModelEventListener(modelListener);
	}
	
	public AppointmentCollection getCollection() {
		return aAppt;
	}

	public void setCollection(AppointmentCollection aAppt) {
		this.aAppt = aAppt;
		this.aAppt.addColEventListener(collectionListener);
	}

	public void addCalMonthEventListener(GenListener<CalMonthControllerEvent> listener) {
		nListener.add(listener);
	}

	@Override
	public void start() {
		
		
	}
	
}