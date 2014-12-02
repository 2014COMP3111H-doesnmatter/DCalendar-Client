package hkust.cse.calendar.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.gui.view.PrimNotificationItemView;
import hkust.cse.calendar.gui.view.base.BaseNotificationContainerView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView.NotificationItemViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class DesktopNotificationController extends EventSource implements Controller {
	final static private int MAX_ITEM = 4;
	final static private int TTL_ITEM = 30 * 1000;
	
	static private DesktopNotificationController instance;
	private BaseNotificationContainerView view;
	private List<GenListener<DesktopNotificationControllerEvent>> aListener = new ArrayList<GenListener<DesktopNotificationControllerEvent>>();
	
	private List<BaseNotificationItemView> itemList = new ArrayList<BaseNotificationItemView>();
	
	public void pushNotification(String title, String content, String icon, GenListener<NotificationItemViewEvent> listener) {
		final BaseNotificationItemView v = new PrimNotificationItemView(title, content, icon);
		v.addNotificationItemEventListener(listener);
		v.addNotificationItemEventListener(new GenListener<NotificationItemViewEvent>() {

			@Override
			public void fireEvent(NotificationItemViewEvent e) {
				Iterator<BaseNotificationItemView> it;
				it = itemList.iterator();
				while(it.hasNext()) {
					BaseNotificationItemView tv = it.next();
					if(tv == e.getSource()) {
						it.remove();
						triggerUpdate();
						break;
					}
				}
				
			}
			
		});
		

		Timer timer = new Timer(TTL_ITEM, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Iterator<BaseNotificationItemView> it;
				it = itemList.iterator();
				while(it.hasNext()) {
					BaseNotificationItemView tv = it.next();
					if(tv == v) {
						it.remove();
						triggerUpdate();
						break;
					}
				}
				
			}
			
		});
		timer.setRepeats(false);
		timer.start();
		
		itemList.add(v);
		if(itemList.size() > MAX_ITEM) {
			itemList.remove(0);
		}
		
		triggerUpdate();
	}
	
	public void pushNotification(String title, String content, GenListener<NotificationItemViewEvent> listener) {
		pushNotification(title, content, null, listener);
	}
	
	static public DesktopNotificationController getInstance() {
		if(instance == null) {
			DCalendarApp app = DCalendarApp.getApp();
			instance = new DesktopNotificationController(app.getViewManager().getNotificationContainerView());
		}
		return instance;
	}
	
	private void triggerUpdate() {
		DesktopNotificationControllerEvent e = new DesktopNotificationControllerEvent(this);
		e.setViewList(itemList);
		
		fireList(aListener, e);
	}
	
	private DesktopNotificationController(BaseNotificationContainerView view) {
		setView(view);
	}
	
	public BaseNotificationContainerView getView() {
		return view;
	}
	
	public void setView(BaseNotificationContainerView view) {
		this.view = view;
		addNotificationControllerEventListener(view);
	}
	
	public void addNotificationControllerEventListener(GenListener<DesktopNotificationControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		
	}

}
