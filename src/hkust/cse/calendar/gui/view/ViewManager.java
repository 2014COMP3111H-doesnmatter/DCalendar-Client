package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;

public interface ViewManager {
	
	BaseCalMainView getCalMainView();
	
	BaseCalMonthView getCalMonthView();
	
	BaseDetailsView getDetailsView();
	
	BaseLoginView getLoginView();
	
	BaseApptListView getApptListView();
	
	BaseMonthSelectorView getMonthSelectorView();
}