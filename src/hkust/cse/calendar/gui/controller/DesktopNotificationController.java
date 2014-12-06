package hkust.cse.calendar.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.collection.NotificationCollection;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.view.PrimNotificationItemView;
import hkust.cse.calendar.gui.view.ViewManager;
import hkust.cse.calendar.gui.view.base.BaseNotificationContainerView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView.NotificationItemViewEvent;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Notification;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;

public class DesktopNotificationController extends EventSource implements Controller {
	final static private int MAX_ITEM = 4;
	final static private int TTL_ITEM = 30 * 1000;
	
	static private DesktopNotificationController instance;
	private BaseNotificationContainerView view;
	private List<GenListener<DesktopNotificationControllerEvent>> aListener = new ArrayList<GenListener<DesktopNotificationControllerEvent>>();
	
	private GenListener<UpdatableEvent> notifyListener = new GenListener<UpdatableEvent>() {

		@Override
		public void fireEvent(UpdatableEvent e) {
			int i;
			switch(e.getCommand()) {
			case INFO_UPDATE:
				List<Notification> aNew = (List<Notification>)e.getNewVal();
				for(i = 0; i < aNew.size(); i++) {
					pushNotification(aNew.get(i));
				}
				break;
			}
		}
		
	};
	
	private List<BaseNotificationItemView> itemList = new ArrayList<BaseNotificationItemView>();
	
	public void pushNotification(Notification n) {
		String message;
		switch(n.getType()) {
		case "ReminderArrived":
			final Appointment appt = (Appointment)n.getBody();

			message = appt.getName() + " is starting at ";
			message += new SimpleDateFormat("HH:mm").format(new Date(appt.getStartTime()));
			message += " in " + VenueCollection.getInstance().getVenue(appt.getVenueId()).getName() + ".";
			pushNotification("DCalendar notification", message, "bell.png", new GenListener<NotificationItemViewEvent>() {

						@Override
						public void fireEvent(NotificationItemViewEvent ev) {
							ViewManager viewManager = DCalendarApp.getApp().getViewManager();
							switch(ev.getCommand()) {
							case ACTIVATE:

								DetailsController dom = new DetailsController(viewManager.getDetailsView());
								dom.setAppt(appt);
								dom.start();
								break;
							}
							
						}
						
			});
			break;
		case "VenueRemovalInitiated":
			final Venue venue = (Venue)n.getBody();
			
			message = "Venue " + venue.getName() + "is to be removed. All appointmnet at the venue will be removed together.";
			pushNotification("Removal of Venue", message, "bell.png", new GenListener<NotificationItemViewEvent>() {

				@Override
				public void fireEvent(NotificationItemViewEvent ev) {
					switch(ev.getCommand()) {
					case ACTIVATE:
						break;
					}
					
				}
				
			});
			break;
		default:
		}
	}
	
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
	
	public DesktopNotificationController(BaseNotificationContainerView view) {
		instance = this;
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
		NotificationCollection.getInstance().addColEventListener(notifyListener);
	}

}
