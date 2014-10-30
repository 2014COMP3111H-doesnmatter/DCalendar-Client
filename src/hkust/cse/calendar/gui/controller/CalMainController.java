package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.collection.AppointmentCollection;
import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.domainModel.CalMainModel.CalMainModelEvent;
import hkust.cse.calendar.gui.view.PrimApptListView;
import hkust.cse.calendar.gui.view.PrimCalMonthView;
import hkust.cse.calendar.gui.view.ViewManager;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView.CalMainViewEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;

public class CalMainController 
extends EventSource implements Controller {
	private BaseCalMainView view;
	private CalMonthController monthController;
	private CalMainModel model;
	private AppointmentCollection aAppt;
	
	private List<GenListener<CalMainControllerEvent>> nListener = new ArrayList<GenListener<CalMainControllerEvent>>();
	
	private GenListener<CalMainViewEvent> mainViewListener = new GenListener<CalMainViewEvent>() {

		@Override
		public void fireEvent(CalMainViewEvent e) {
			CalMainViewEvent.Command command = e.getCommand();
			DCalendarApp app = DCalendarApp.getApp();
			if(command == CalMainViewEvent.Command.EXIT) {
				app.exitApp();
				view.dispose();
			}
			else if(command == CalMainViewEvent.Command.LOGOUT) {
				APIHandler.resetCookie();
				BaseLoginView loginView = app.getViewManager().getLoginView();
				app.switchController(new LoginController(loginView));
				view.dispose();
			}
			else if(command == CalMainViewEvent.Command.MANUAL_SCHEDULE) {
				//TODO: adapt to App scheduler
			}
			
		}
		
	};
	private GenListener<CalMainModelEvent> modelListener = new GenListener<CalMainModelEvent>() {

		@Override
		public void fireEvent(CalMainModelEvent e) {
			CalMainModelEvent.Command command = e.getCommand();
			if(command == CalMainModelEvent.Command.UPDATE
					|| command == CalMainModelEvent.Command.UPDATE_ONLY_DAY) {
				User user = DCalendarApp.getApp().getCurrentUser();
				
				long today = model.getSelectedDayStamp();
				
				CalMainControllerEvent ev = new CalMainControllerEvent(this, CalMainControllerEvent.Command.UPDATE_INFO);
				ev.setSelectedDay(today);
				aAppt.setMonthStart(today);
				ev.setUsername(user.getUsername());
				fireList(nListener, ev);
			}
			
		}
		
	};
	
	CalMainController(BaseCalMainView view) {
		setView(view);
		ViewManager manager = DCalendarApp.getApp().getViewManager();
		
		//TODO: adapt to Timemachine
		model = new CalMainModel();
		model.addModelEventListener(modelListener);
		long today = model.getSelectedDayStamp();
		aAppt = new AppointmentCollection(today);
		aAppt.load();
		
		monthController = new CalMonthController(manager.getCalMonthView(), model, aAppt);
		this.view.setCalMonthView(monthController.getView());
		
		// appt list
		ApptListController apptListController = new ApptListController(new PrimApptListView());
		this.view.setApptListView(apptListController.getView());
		
		User user = DCalendarApp.getApp().getCurrentUser();
		
		CalMainControllerEvent e = new CalMainControllerEvent(this, CalMainControllerEvent.Command.UPDATE_INFO);
		e.setSelectedDay(today);
		e.setUsername(user.getUsername());
		
		
		//Force trigger a model update
		model.setSelectedDayStamp(today);
		//fireList(nListener, e);
	}
	
	
	@Override
	public void start() {
		CalMainControllerEvent e = new CalMainControllerEvent(this, CalMainControllerEvent.Command.START);
		fireList(nListener, e);
	}

	public void addCalMainControllerEventListener(GenListener<CalMainControllerEvent> listener) {
		nListener.add(listener);
	}

	public BaseCalMainView getView() {
		return view;
	}

	public void setView(BaseCalMainView view) {
		this.view = view;

		this.view.addCalMainEventListener(mainViewListener);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addCalMainControllerEventListener(view);
	}

	public CalMonthController getMonthController() {
		return monthController;
	}

	public void setMonthController(CalMonthController monthController) {
		this.monthController = monthController;
	}

	public CalMainModel getModel() {
		return model;
	}

	public void setModel(CalMainModel model) {
		this.model = model;
	}

	public AppointmentCollection getaAppt() {
		return aAppt;
	}

	public void setaAppt(AppointmentCollection aAppt) {
		this.aAppt = aAppt;
	}
	
}