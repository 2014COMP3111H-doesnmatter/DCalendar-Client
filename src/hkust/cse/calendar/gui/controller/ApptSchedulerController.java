package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.collection.AppointmentCollection;
import hkust.cse.calendar.collection.AppointmentCollection.AddAppointmentQuery;
import hkust.cse.calendar.collection.AppointmentCollection.EditAppointmentQuery;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.view.base.BaseApptSchedulerView;
import hkust.cse.calendar.gui.view.base.BaseApptSchedulerView.ApptSchedulerViewEvent;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class ApptSchedulerController extends EventSource implements Controller {
	private BaseApptSchedulerView view;
	private CalMainModel model;
	private AppointmentCollection aAppt;
	private VenueCollection aVenue;
	private Appointment currentAppt;
	
	private List<GenListener<ApptSchedulerControllerEvent>> aListener = new ArrayList<GenListener<ApptSchedulerControllerEvent>>();
	private GenListener<ApptSchedulerViewEvent> apptSchedulerViewListener = new GenListener<ApptSchedulerViewEvent>() {

		@Override
		public void fireEvent(ApptSchedulerViewEvent e) {
			ApptSchedulerViewEvent.Command command = e.getCommand();
			switch(command) {
			case SAVE:
				if(currentAppt.getId() == 0L) {
					//Create new
					aAppt.createAppt(currentAppt, new GenListener<AddAppointmentQuery>() {

						@Override
						public void fireEvent(AddAppointmentQuery e) {
							AddAppointmentQuery.Command command = e.getCommand();
							ApptSchedulerControllerEvent ev = new ApptSchedulerControllerEvent(this);
							switch(command) {
							case OK:
								view.dispose();
								break;
							case VENUE_NOT_FOUND:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("Venue is not found");
								ev.setErrTitle("Invalid Venue");
								break;
							case ILLEGAL_TIME:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText(toSentence(e.getExplain()));
								ev.setErrTitle("Invalid Time");
								break;
							case NETWORK_ERR:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("Try again later");
								ev.setErrTitle("Network Error");
								break;
							case UNKNOWN_ERR:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("Oops...");
								ev.setErrTitle("Unknown Error");
								break;
							}
							fireList(aListener, ev);
						}
						
					});
				}
				else {
					//Edit existing
					aAppt.editAppt(currentAppt, new GenListener<EditAppointmentQuery>() {

						@Override
						public void fireEvent(EditAppointmentQuery e) {
							EditAppointmentQuery.Command command = e.getCommand();
							ApptSchedulerControllerEvent ev = new ApptSchedulerControllerEvent(this);
							switch(command) {
							case OK:
								view.dispose();
								break;
							case VENUE_NOT_FOUND:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("Venue is not found");
								ev.setErrTitle("Invalid Venue");
								break;
							case ILLEGAL_TIME:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText(toSentence(e.getExplain()));
								ev.setErrTitle("Invalid Time");
								break;
							case NETWORK_ERR:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("Try again later");
								ev.setErrTitle("Network Error");
								break;
							case UNKNOWN_ERR:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("Oops...");
								ev.setErrTitle("Unknown Error");
								break;
							case APPT_NOT_FOUND:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("The appointment you are to edit is not found.");
								ev.setErrTitle("Invalid Appointment");
								break;
							case PERM_DENIED:
								ev.setCommand(ApptSchedulerControllerEvent.Command.PROMPT_ERR);
								ev.setErrText("You are not authorized to edit this.");
								ev.setErrTitle("Invalid Appointment");
								break;
							}
							fireList(aListener, ev);
						}
						
					});
				}
				ApptSchedulerControllerEvent ev = new ApptSchedulerControllerEvent(this, ApptSchedulerControllerEvent.Command.SAVE_PENDING);
				fireList(aListener, ev);
				break;
			case CLOSE:
				view.dispose();
			}
			
		}
		
	};
	
	public ApptSchedulerController(BaseApptSchedulerView view,
			AppointmentCollection aAppt, VenueCollection aVenue, Appointment currentAppt) {
		setView(view);
		setaAppt(aAppt);
		setCurrentAppt(currentAppt);
		setaVenue(aVenue);
	}
	
	static protected String toSentence(String words) {
		char[] sentence = words.toCharArray();
		sentence[0] = words.substring(0, 1).toUpperCase().charAt(0);
		words = String.valueOf(sentence) + '.';
		return words;
	}
	
	public BaseApptSchedulerView getView() {
		return view;
	}
	
	public void setView(BaseApptSchedulerView view) {
		this.view = view;
		this.view.addApptSchedulerEventListener(apptSchedulerViewListener);
		addApptSchedulerControllerEventListener(view);
		// TODO Auto-generated method stub
	}
	
	public void addApptSchedulerControllerEventListener(GenListener<ApptSchedulerControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		ApptSchedulerControllerEvent e = new ApptSchedulerControllerEvent(this, ApptSchedulerControllerEvent.Command.START);
		fireList(aListener, e);
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

	public Appointment getCurrentAppt() {
		return currentAppt;
	}

	public void setCurrentAppt(Appointment currentAppt) {
		if(currentAppt == null) {
			currentAppt = new Appointment();
		}
		this.currentAppt = currentAppt;
		this.view.setAppt(currentAppt);
	}

	public VenueCollection getaVenue() {
		return aVenue;
	}

	public void setaVenue(VenueCollection aVenue) {
		this.aVenue = aVenue;
		this.view.setaAvenue(aVenue.getVenueList());
	}

}
