package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.collection.AppointmentCollection;
import hkust.cse.calendar.collection.NotificationCollection;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.domainModel.CalMainModel.CalMainModelEvent;
import hkust.cse.calendar.gui.view.PrimApptListView;
import hkust.cse.calendar.gui.view.PrimApptSchedulerView;
import hkust.cse.calendar.gui.view.PrimCalMonthView;
import hkust.cse.calendar.gui.view.PrimEditUserView;
import hkust.cse.calendar.gui.view.PrimTimeMachineView;
import hkust.cse.calendar.gui.view.PrimVenueManagerView;
import hkust.cse.calendar.gui.view.ViewManager;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView.CalMainViewEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Notification;
import hkust.cse.calendar.model.TimeMachine;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;
import hkust.cse.calendar.utils.network.APIHandler;

public class CalMainController 
extends EventSource implements Controller {
	private BaseCalMainView view;
	private CalMonthController monthController;
	private ApptListController apptListController;
	private MonthSelectorController monthSelectorController;
	private CalMainModel model;
	private AppointmentCollection aAppt;
	private VenueCollection aVenue;
	private TimeMachine timeMachine;
	private NotificationController notifyController;
	private NotificationCollection aNotification;
	private DesktopNotificationController desktopCtrler;
	
	private boolean isStarted = false;
	private boolean isVenueLoaded = false;
	private boolean isTimeMachineLoaded = false;
	
	static private CalMainController instance;
	
	private List<GenListener<CalMainControllerEvent>> nListener = new ArrayList<GenListener<CalMainControllerEvent>>();
	
	private GenListener<CalMainViewEvent> mainViewListener = new GenListener<CalMainViewEvent>() {

		@Override
		public void fireEvent(CalMainViewEvent e) {
			CalMainViewEvent.Command command = e.getCommand();
			DCalendarApp app = DCalendarApp.getApp();
			switch(command) {
			case EXIT:
				app.exitApp();
				view.dispose();
				break;
			case LOGOUT:
				APIHandler.resetCookie();
				BaseLoginView loginView = app.getViewManager().getLoginView();
				app.switchController(new LoginController(loginView));
				view.dispose();
				break;
			case MANUAL_SCHEDULE:
				ApptSchedulerController schedulerCont = new ApptSchedulerController(new PrimApptSchedulerView(), null);
				schedulerCont.start();
				break;
			case TIME_MACHINE:
				TimeMachineController controller = new TimeMachineController(new PrimTimeMachineView());
				controller.start();
				break;
			case VENUE:
				VenueManagerController venueCtrl = new VenueManagerController(new PrimVenueManagerView());
				venueCtrl.start();
				break;
			case CHANGE_PROFILE:
				EditUserController userCtrl = new EditUserController(app.getCurrentUser(), new PrimEditUserView());
				userCtrl.start();
				List<Notification> aConcern = filterConcerned();
				User user = DCalendarApp.getApp().getCurrentUser();
				
				long today = model.getSelectedDayStamp();
				
				CalMainControllerEvent ev = new CalMainControllerEvent(this, CalMainControllerEvent.Command.UPDATE_INFO);
				ev.setSelectedDay(today);
				ev.setUser(user);
				ev.setaNotification(aConcern);
				fireList(nListener, ev);
				break;
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
				ev.setUser(user);
				ev.setaNotification(filterConcerned());
				fireList(nListener, ev);
			}
			
		}
		
	};
	private GenListener<UpdatableEvent> notificationListener = new GenListener<UpdatableEvent>() {

		@Override
		public void fireEvent(UpdatableEvent e) {
			int i;
			switch(e.getCommand()) {
			case INFO_UPDATE:
				List<Notification> aConcern = filterConcerned();
				User user = DCalendarApp.getApp().getCurrentUser();
				
				long today = model.getSelectedDayStamp();
				
				CalMainControllerEvent ev = new CalMainControllerEvent(this, CalMainControllerEvent.Command.UPDATE_INFO);
				ev.setSelectedDay(today);
				ev.setUser(user);
				ev.setaNotification(aConcern);
				fireList(nListener, ev);
				break;
			}
		}
		
	};
	
	CalMainController(BaseCalMainView view) {
		setView(view);
		ViewManager manager = DCalendarApp.getApp().getViewManager();
		
		model = new CalMainModel();
		model.addModelEventListener(modelListener);
		aVenue = new VenueCollection();
		aVenue.addColEventListener(new GenListener<UpdatableEvent>() {

			@Override
			public void fireEvent(UpdatableEvent e) {
				isVenueLoaded = true;
				if(isStarted)
					start();
			}
			
		});
		aVenue.load();
		
		timeMachine = new TimeMachine();
		timeMachine.addColEventListener(new GenListener<UpdatableEvent>() {

			@Override
			public void fireEvent(UpdatableEvent e) {
				isTimeMachineLoaded = true;
				if(isStarted)
					start();
			}
			
		});
		timeMachine.load();
		
		aAppt = new AppointmentCollection(0);
		aNotification = new NotificationCollection();
		aNotification.addColEventListener(notificationListener);

		desktopCtrler = new DesktopNotificationController(manager.getNotificationContainerView());
		
		// month view
		monthController = new CalMonthController(manager.getCalMonthView());
		this.view.setCalMonthView(monthController.getView());
		
		// appt list
		apptListController = new ApptListController(manager.getApptListView());
		this.view.setApptListView(apptListController.getView());
		
		// month selector
		monthSelectorController = new MonthSelectorController(manager.getMonthSelectorView());
		this.view.setMonthSelectView(monthSelectorController.getView());
		
		instance = this;
	}
	
	static public CalMainController getInstance() {
		return instance;
	}
	
	private List<Notification> filterConcerned() {
		int i;
		List<Notification> aNew = NotificationCollection.getInstance().getaNotification();
		List<Notification> aConcern = new ArrayList<Notification>();
		for(i = 0; i < aNew.size(); i++) {
			Notification n = aNew.get(i);
			switch(n.getType()) {
			case "VenueRemovalInitiated":
			case "UserRemovalInitiated":
			case "JointAppointmentInitiated":
				aConcern.add(n);
				break;
			case "VenueRemovalFinalized":
				aVenue.load();
				break;
			}
		}
		return aConcern;
	}
	
	
	@Override
	public void start() {
		isStarted = true;
		if(!isVenueLoaded || !isTimeMachineLoaded) {
			return;
		}
		isStarted = false;
		
		//Force trigger a model update
		model.setSelectedDay(timeMachine.getNow());
		notifyController = new NotificationController();
		notifyController.start();
		desktopCtrler.start();
		aNotification.load();
		
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
