package hkust.cse.calendar.gui.controller;

import hkust.cse.calendar.gui.view.base.BaseNotificationItemView;

import java.util.EventObject;
import java.util.List;


public class DesktopNotificationControllerEvent extends EventObject {
	private List<BaseNotificationItemView> viewList;
	
	public DesktopNotificationControllerEvent(Object source) {
		super(source);
		
	}

	public List<BaseNotificationItemView> getViewList() {
		return viewList;
	}


	public void setViewList(List<BaseNotificationItemView> viewList) {
		this.viewList = viewList;
	}
	
}