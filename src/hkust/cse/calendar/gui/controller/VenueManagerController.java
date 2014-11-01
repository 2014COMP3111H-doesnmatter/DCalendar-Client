package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView.VenueManagerViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class VenueManagerController extends EventSource implements Controller {
	private BaseVenueManagerView view;
	private List<GenListener<VenueManagerControllerEvent>> aListener = new ArrayList<GenListener<VenueManagerControllerEvent>>();
	private GenListener<VenueManagerViewEvent> venueManagerViewListener = new GenListener<VenueManagerViewEvent>() {

		@Override
		public void fireEvent(VenueManagerViewEvent e) {
			// TODO Auto-generated method stub
			
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
		this.view.addVenueManagerEventListener(venueManagerViewListener);
		addVenueManagerControllerEventListener(view);
		// TODO Auto-generated method stub
	}
	
	public void addVenueManagerControllerEventListener(GenListener<VenueManagerControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		
	}

}
