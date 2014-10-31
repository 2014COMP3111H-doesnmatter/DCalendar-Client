package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hkust.cse.calendar.collection.AppointmentCollection;
import hkust.cse.calendar.collection.AppointmentCollection.ListAppointmentQuery;
import hkust.cse.calendar.collection.AppointmentCollection.RemoveAppointmentQuery;
import hkust.cse.calendar.collection.BaseCollection.CollectionEvent;
import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.domainModel.CalMainModel.CalMainModelEvent;
import hkust.cse.calendar.gui.view.PrimDetailsView;
import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseApptListView.ApptListViewEvent;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class ApptListController extends EventSource implements Controller
{

	private BaseApptListView view;
	private AppointmentCollection aAppt;
	private CalMainModel model;
	private List<GenListener<ApptListControllerEvent>> aListener = new ArrayList<GenListener<ApptListControllerEvent>>();
	private GenListener<ApptListViewEvent> apptListViewListener = new GenListener<ApptListViewEvent>() {

		@Override
		public void fireEvent(ApptListViewEvent e) {
			
			switch(e.getCommand()) {
			case DELETE_APPOITNMENT:
				int n = JOptionPane.showConfirmDialog(null, "Delete Appointment " + e.appt + " ?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.NO_OPTION) break;
				aAppt.removeAppt(e.appt, new GenListener<RemoveAppointmentQuery>() {
					@Override
					public void fireEvent(RemoveAppointmentQuery e) {
						
					}
					
				});
				break;
			case DESCRIB_APPOINTMENT:
				DetailsController dom = new DetailsController(new PrimDetailsView());
				dom.setAppt(e.appt);
				dom.start();
			}
		}
		
	};
	private GenListener<CollectionEvent> collectionListener = new GenListener<CollectionEvent>() {

		@Override
		public void fireEvent(CollectionEvent e) {
			switch(e.getCommand()) {
			case INFO_UPDATE:
				update();
			}
			
		}
		
	};
	private GenListener<CalMainModelEvent> modelListener = new GenListener<CalMainModelEvent>() {

		@Override
		public void fireEvent(CalMainModelEvent e) {
			switch(e.getCommand()) {
			case UPDATE:
			case UPDATE_ONLY_DAY:
				update();
			}
			
		}
		
	};
	public ApptListController(BaseApptListView view, CalMainModel model, AppointmentCollection aAppt)
	{
		this.model = model;
		this.aAppt = aAppt;
		model.addModelEventListener(modelListener);
		aAppt.addColEventListener(collectionListener);
		setView(view);
		
	}
	public BaseApptListView getView() {
		return view;
	}
	private void setView(BaseApptListView view) {
		this.view = view;
		this.addApptListControllerEventListener(view);
		view.addApptListViewEventListener(apptListViewListener);
	}
	public void addApptListControllerEventListener(GenListener<ApptListControllerEvent> listener) {
		this.aListener.add(listener);
	}
	@Override
	public void start() {
		ApptListControllerEvent e = new ApptListControllerEvent(this, ApptListControllerEvent.Command.START);
		fireList(aListener, e);
		update();
		

	}
	private void update() {
		
		this.aAppt.getEventInDay(model.getSelectedDayStamp(), new GenListener<ListAppointmentQuery>() {

			@Override
			public void fireEvent(ListAppointmentQuery e) {
				switch(e.getCommand()) {
				case OK:
					
					Appointment[] aAppt = e.getaAppt().toArray(new Appointment[0]);
					ApptListControllerEvent viewEvent = new ApptListControllerEvent(ApptListController.this);
					viewEvent.setCommand(ApptListControllerEvent.Command.SET_APPOINTMENT);;
					viewEvent.aAppt = aAppt;
					fireList(aListener, viewEvent);
					
				}
				
			}
			
		});
	}

}
