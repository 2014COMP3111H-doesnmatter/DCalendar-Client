package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.api.appointment.AcceptAPI;
import hkust.cse.calendar.api.appointment.RejectAPI;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.gui.view.base.BaseDetailsView.DetailsViewEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class DetailsController 
extends EventSource implements Controller {
	private BaseDetailsView view;
	private Appointment appt;
	private VenueCollection aVenue;
	
	private List<GenListener<DetailsControllerEvent>> nListener = new ArrayList<GenListener<DetailsControllerEvent>>();
	private GenListener<DetailsViewEvent> detailsViewListener = new GenListener<DetailsViewEvent>() {

		@Override
		public void fireEvent(DetailsViewEvent e) {
			DetailsViewEvent.Command command = e.getCommand();
			switch(command) {
			case ACCEPT:
				AcceptAPI accAPI = new AcceptAPI(appt);
				accAPI.addDoneListener(new GenListener<APIRequestEvent>() {

					@Override
					public void fireEvent(APIRequestEvent e) {
						JSONObject json = e.getJSON();
						try {
							int rtnCode = json.getInt("rtnCode");
							if(rtnCode == 200) {
								view.dispose();
							}
							else if(rtnCode == 406) {
								JOptionPane.showMessageDialog(view,
										"This appointment is conflict with your schedule",
										"Illegal time", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch(JSONException ex) {
							
						}
						
					}
					
				});
				Thread accthrd = new Thread(new APIHandler(accAPI));
				accthrd.start();
				break;
			case REJECT:
				RejectAPI rejAPI = new RejectAPI(appt);
				rejAPI.addDoneListener(new GenListener<APIRequestEvent>() {

					@Override
					public void fireEvent(APIRequestEvent e) {
						JSONObject json = e.getJSON();
						try {
							int rtnCode = json.getInt("rtnCode");
							if(rtnCode == 200) {
								view.dispose();
							}
						}
						catch(JSONException ex) {
							
						}
					}
					
				});
				Thread rejthrd = new Thread(new APIHandler(rejAPI));
				rejthrd.start();
				break;
			case EXIT:
				view.dispose();
				break;
			}
			
		}
		
	};
	
	public DetailsController(BaseDetailsView view) {
		setView(view);
		this.aVenue = VenueCollection.getInstance();
	}
	
	public void addDetailsEventListener(GenListener<DetailsControllerEvent> listener) {
		nListener.add(listener);
	}
	
	public BaseDetailsView getView() {
		return view;
	}
	public void setView(BaseDetailsView view) {
		this.view = view;
		this.view.addDetailsEventListener(detailsViewListener);
		this.addDetailsEventListener(view);
	}
	
	public Appointment getAppt() {
		return appt;
	}
	public void setAppt(Appointment appt) {
		this.appt = appt;
		DetailsControllerEvent e = new DetailsControllerEvent(this, DetailsControllerEvent.Command.UPDATE_TEXT);
		e.setAppt(appt);
		e.setaVenue(aVenue.getVenueList());
		fireList(nListener, e);
	}
	
	@Override
	public void start() {
		DetailsControllerEvent e = new DetailsControllerEvent(this, DetailsControllerEvent.Command.START);
		e.setAppt(appt);
		e.setaVenue(aVenue.getVenueList());
		fireList(nListener, e);
	}
}