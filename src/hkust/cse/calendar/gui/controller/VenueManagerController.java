package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView.VenueManagerViewEvent;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.Updatable.UpdatableEvent;

public class VenueManagerController extends EventSource implements Controller {
	private BaseVenueManagerView view;
	private String[] aName;
	private List<GenListener<VenueManagerControllerEvent>> aListener = new ArrayList<GenListener<VenueManagerControllerEvent>>();
	private GenListener<VenueManagerViewEvent> venueManagerViewListener = new GenListener<VenueManagerViewEvent>() {

		@Override
		public void fireEvent(VenueManagerViewEvent e) {
			switch(e.getCommand()) {
			case ADD_VENUE:
				if(venueExists(e.name)) return;
				VenueCollection.getInstance().addVenue(e.name);
			}
		}
		
	};
	
	private GenListener<UpdatableEvent> venueListListener = new GenListener<UpdatableEvent> () {

		@Override
		public void fireEvent(UpdatableEvent e) {
			switch(e.getCommand()) {
			case INFO_UPDATE:
				refresh();
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
		this.view.addVenueManagerEventListener(venueManagerViewListener);
		addVenueManagerControllerEventListener(view);
		VenueCollection.getInstance().addColEventListener(venueListListener);
	}
	
	public void addVenueManagerControllerEventListener(GenListener<VenueManagerControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		refresh();
	}
	
	private void refresh() {
		Map<Long,Venue> venues = VenueCollection.getInstance().getVenueList();
		String[] aVenueName = new String[venues.size()];
		Iterator<Entry<Long, Venue>> itr = venues.entrySet().iterator();
		for(int i=0;itr.hasNext();i++) {
			Entry<Long, Venue> pair = itr.next();
			aVenueName[i] = pair.getValue().getName();
		}
		this.aName = aVenueName;
		
		VenueManagerControllerEvent e = new VenueManagerControllerEvent(this);
		e.aVenueName = aVenueName;
		e.setCommand(VenueManagerControllerEvent.Command.REFRESH);
		fireList(aListener, e);
	}
	
	private boolean venueExists(String name) {
		for(String iName:this.aName) {
			if(iName.equals(name)) return true;
		}
		return false;
	}

}
