package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;

public interface ViewManager {
	
	BaseCalMainView getCalMainView();
	
	BaseCalMonthView getCalMonthView();
	
	BaseDetailsView getDetailsView();
	
	BaseLoginView getLoginView();
}