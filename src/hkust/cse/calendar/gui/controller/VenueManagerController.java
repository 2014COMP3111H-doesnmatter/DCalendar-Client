package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.view.PrimCreateVenueView;
import hkust.cse.calendar.gui.view.PrimEditVenueView;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView.VenueManagerViewEvent;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;

public class VenueManagerController extends EventSource implements Controller {
	private BaseVenueManagerView view;
	private List<GenListener<VenueManagerControllerEvent>> aListener = new ArrayList<GenListener<VenueManagerControllerEvent>>();
	private GenListener<VenueManagerViewEvent> userManagerViewListener = new GenListener<VenueManagerViewEvent>() {

		@Override
		public void fireEvent(VenueManagerViewEvent e) {
			Venue u;
			switch(e.getCommand()) {
			case CREATE:
				CreateVenueController createCtrl = new CreateVenueController(new PrimCreateVenueView());
				createCtrl.start();
				break;
			case EDIT:
				u = e.getVenue();

				EditVenueController editCtrl = new EditVenueController(u, new PrimEditVenueView());
				editCtrl.start();
				break;
			case DELETE:
				u = e.getVenue();
				int n = JOptionPane.showConfirmDialog(view, "Remove Venue " + u + " ?\n" + 
				"Deletion will finish after affected user confirms.",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION) {
					VenueCollection.getInstance().removeVenue(u);
				}
				break;
			case CLOSE:
				view.dispose();
				view = null;
				break;
			}
			
		}
		
	};
	
	public VenueManagerController(BaseVenueManagerView view) {
		setView(view);
	}
	
	public BaseVenueManagerView getView() {
		return view;
	}
	
	public void setView(BaseVenueManagerView view) {
		this.view = view;
		this.view.addVenueManagerEventListener(userManagerViewListener);
		addVenueManagerControllerEventListener(view);
	}
	
	public void addVenueManagerControllerEventListener(GenListener<VenueManagerControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		
		VenueManagerControllerEvent ev = new VenueManagerControllerEvent(VenueManagerController.this);
		ev.setCommand(VenueManagerControllerEvent.Command.INFO_UPDATE);
		ev.setaVenue(new ArrayList<Venue>(VenueCollection.getInstance().getVenueList().values()));
		fireList(aListener, ev);
		
		VenueManagerControllerEvent e = new VenueManagerControllerEvent(this);
		e.setCommand(VenueManagerControllerEvent.Command.START);
		fireList(aListener, e);
		
		VenueCollection.getInstance().addColEventListener(new GenListener<UpdatableEvent>() {

			@Override
			public void fireEvent(UpdatableEvent e) {
				if(view == null) {
					return;
				}
				VenueManagerControllerEvent ev = new VenueManagerControllerEvent(VenueManagerController.this);
				ev.setCommand(VenueManagerControllerEvent.Command.INFO_UPDATE);
				ev.setaVenue(new ArrayList<Venue>(VenueCollection.getInstance().getVenueList().values()));
				fireList(aListener, ev);
			}
			
		});
	}

}
