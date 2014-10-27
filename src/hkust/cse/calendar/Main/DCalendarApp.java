package hkust.cse.calendar.Main;

import hkust.cse.calendar.gui.controller.Controller;
import hkust.cse.calendar.gui.controller.LoginController;
import hkust.cse.calendar.gui.view.PrimLoginView;
import hkust.cse.calendar.model.User;

public class DCalendarApp {
	static private DCalendarApp app;
	private Controller activeController;
	private boolean exit = false;
	private boolean switchControl;
	
	private User currentUser;
	
	public DCalendarApp() {
		if(app != null) {
			throw new RuntimeException("Only One instance allowed");
		}
		app = this;
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
		activeController = new LoginController(new PrimLoginView());
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
}