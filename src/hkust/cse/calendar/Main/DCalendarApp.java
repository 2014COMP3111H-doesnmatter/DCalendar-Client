package hkust.cse.calendar.Main;

import hkust.cse.calendar.gui.controller.Controller;
import hkust.cse.calendar.gui.controller.LoginController;
import hkust.cse.calendar.gui.view.FancyCalMainView;
import hkust.cse.calendar.gui.view.FancyLoginView;
import hkust.cse.calendar.gui.view.PrimApptListView;
import hkust.cse.calendar.gui.view.PrimApptSchedulerView;
import hkust.cse.calendar.gui.view.PrimCalMainView;
import hkust.cse.calendar.gui.view.PrimCalMonthView;
import hkust.cse.calendar.gui.view.PrimDetailsView;
import hkust.cse.calendar.gui.view.PrimLoginView;
import hkust.cse.calendar.gui.view.PrimMonthSelectorView;
import hkust.cse.calendar.gui.view.PrimNotificationContainerView;
import hkust.cse.calendar.gui.view.ViewManager;
import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseApptSchedulerView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;
import hkust.cse.calendar.gui.view.base.BaseNotificationContainerView;
import hkust.cse.calendar.model.User;

public class DCalendarApp {
	static private DCalendarApp app;
	private Controller activeController;
	private boolean exit = false;
	private boolean switchControl;
	
	private User currentUser;
	private ViewManager viewManager;
	
	public DCalendarApp() {
		if(app != null) {
			throw new RuntimeException("Only One instance allowed");
		}
		app = this;
		viewManager = new ViewManager() {

			@Override
			public BaseCalMainView getCalMainView() {
				return new FancyCalMainView();
			}

			@Override
			public BaseCalMonthView getCalMonthView() {
				return new PrimCalMonthView();
			}

			@Override
			public BaseDetailsView getDetailsView() {
				return new PrimDetailsView();
			}

			@Override
			public BaseLoginView getLoginView() {
				return new FancyLoginView();
			}

			@Override
			public BaseApptListView getApptListView() {
				return new PrimApptListView();
			}

			@Override
			public BaseMonthSelectorView getMonthSelectorView() {
				return new PrimMonthSelectorView();
			}

			@Override
			public BaseApptSchedulerView getApptSchedulerView() {
				return new PrimApptSchedulerView();
			}

			@Override
			public BaseNotificationContainerView getNotificationContainerView() {
				return new PrimNotificationContainerView();
			}
			
		};
	}
	
	static public DCalendarApp getApp() {
		return app;
	}

	public Controller getActiveController() {
		return activeController;
	}

	public void setActiveController(Controller activeController) {
		this.activeController = activeController;
	}
	
	public void exitApp() {
		exit = true;
	}
	
	public void switchController(Controller controller) {
		switchControl = true;
		setActiveController(controller);
	}
	
	public void start() {
		activeController = new LoginController(viewManager.getLoginView());
		do {
			switchControl = false;
			activeController.start();
			
			do {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while(!switchControl && !exit);
		} while(!exit);
	}
	
	public static void main(String[] args) {
		Debug.main();
		DCalendarApp instance = new DCalendarApp();
		instance.start();
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ViewManager getViewManager() {
		return viewManager;
	}

	public void setViewManager(ViewManager viewManager) {
		this.viewManager = viewManager;
	}
}