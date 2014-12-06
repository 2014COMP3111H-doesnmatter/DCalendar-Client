package hkust.cse.calendar.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.api.appointment.ListByDayAPI;
import hkust.cse.calendar.collection.AppointmentCollection;
import hkust.cse.calendar.collection.NotificationCollection;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.view.PrimDetailsView;
import hkust.cse.calendar.gui.view.ViewManager;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView.NotificationItemViewEvent;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Notification;
import hkust.cse.calendar.model.TimeMachine;
import hkust.cse.calendar.utils.DateTimeHelper;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class NotificationController implements Controller, ActionListener {
	private List<Timer> aTimer = new ArrayList<Timer>();
	private List<Appointment> aNotifyObject = new ArrayList<Appointment>();
	private TimeMachine timeMachine;
	
	private GenListener<UpdatableEvent> updatableListener = new GenListener<UpdatableEvent>() {

		@Override
		public void fireEvent(UpdatableEvent e) {
			cancelAll();
			setupNotification();
		}
		
	};
	
	public NotificationController() {
		setTimeMachine(TimeMachine.getInstance());
	}
	
	
	private void cancelAll() {
		for(Timer timer: aTimer) {
			if(timer.isRunning()) timer.stop();
		}
		aTimer.clear();
	}
	
	private void setupNotification() {
		final long nowStamp = DateTimeHelper.getStartOfDay(timeMachine.getNow().getTime());
		final Date nowDate = timeMachine.getNow();
		ListByDayAPI api = new ListByDayAPI(nowStamp);
		final NotificationController that = this;
		api.addDoneListener(new GenListener<APIRequestEvent>() {

			@Override
			public void fireEvent(APIRequestEvent e) {
				JSONObject json = e.getJSON();
				try {
					int rtnCode = json.getInt("rtnCode");
					if(rtnCode == 200) {
						int i, size;
						aNotifyObject.clear();
						JSONArray aJson = json.getJSONArray("aAppointment");
						for(i = 0, size = aJson.length(); i < size; i++) {
							JSONObject apptJson = aJson.getJSONObject(i);
							Appointment appt = new Appointment(apptJson);
							aNotifyObject.add(appt);
							if(appt.getReminderAhead() <= 0) {
								continue;
							}
							long toNotify = appt.getStartTime() - appt.getReminderAhead();
							Date notifyD = new Date(toNotify);
							notifyD.setYear(nowDate.getYear());
							notifyD.setMonth(nowDate.getMonth());
							notifyD.setMonth(nowDate.getMonth());
							notifyD.setDate(nowDate.getDate());
							
							long delay = notifyD.getTime() - nowDate.getTime();
							if(delay > 0) {
								//Add timer
								Timer timer = new Timer((int) delay, that);
								timer.setRepeats(false);
								timer.setActionCommand(String.valueOf(i));
								aTimer.add(timer);
								timer.start();
							}
						}
					}
				} catch(JSONException ex) {
					ex.printStackTrace();
				}
				
			}
			
		});
		Thread thrd = new Thread(new APIHandler(api));
		thrd.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int idx;
		try {
			idx = Integer.parseInt(e.getActionCommand());
			final Appointment appt = aNotifyObject.get(idx);
			NotificationCollection.getInstance().pushNotification(new Notification("ReminderArrived", appt));
			
		} catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void start() {
		setupNotification();
	}


	public TimeMachine getTimeMachine() {
		return timeMachine;
	}


	public void setTimeMachine(TimeMachine timeMachine) {
		this.timeMachine = timeMachine;
		timeMachine.addColEventListener(updatableListener);
		AppointmentCollection.getInstance().addColEventListener(updatableListener);
	}
	
}