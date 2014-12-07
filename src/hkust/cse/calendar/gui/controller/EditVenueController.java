package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.api.venue.EditAPI;
import hkust.cse.calendar.api.welcome.SignUpAPI;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.collection.VenueCollection.VenueQuery;
import hkust.cse.calendar.gui.view.base.BaseEditVenueView;
import hkust.cse.calendar.gui.view.base.BaseEditVenueView.EditVenueViewEvent;
import hkust.cse.calendar.gui.view.base.BaseEditVenueView;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class EditVenueController extends EventSource implements Controller {
	private BaseEditVenueView view;
	private Venue venue;
	private List<GenListener<EditVenueControllerEvent>> aListener = new ArrayList<GenListener<EditVenueControllerEvent>>();
	private GenListener<EditVenueViewEvent> createVenueViewListener = new GenListener<EditVenueViewEvent>() {

		@Override
		public void fireEvent(EditVenueViewEvent e) {
			switch(e.getCommand()) {
			case EDIT:
				Venue u = e.getVenue();
				if(u.getName().length() == 0) {
					JOptionPane.showMessageDialog(view, "Please fill in Venue name", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(u.getCapacity() <= 0) {
					JOptionPane.showMessageDialog(view, "Please fill in capacity", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				VenueCollection.getInstance().editVenue(venue, u, new GenListener<VenueQuery>() {

					@Override
					public void fireEvent(VenueQuery e) {
						switch(e.getRtnValue()) {
						case OK:
							view.dispose();
							break;
						case DUPLICATE_ERR:
							JOptionPane.showMessageDialog(view, "Venue already exists", "Duplicate Venue", JOptionPane.ERROR_MESSAGE);
							break;
						}
					}
				});
				break;
			case CLOSE:
				view.dispose();
				break;
			}
			
		}
		
	};
	
	public EditVenueController(Venue u, BaseEditVenueView view) {
		setView(view);
		this.venue = u;
	}
	
	public BaseEditVenueView getView() {
		return view;
	}
	
	public void setView(BaseEditVenueView view) {
		this.view = view;
		this.view.addEditVenueEventListener(createVenueViewListener);
		addEditVenueControllerEventListener(view);
	}
	
	public void addEditVenueControllerEventListener(GenListener<EditVenueControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		EditVenueControllerEvent ev = new EditVenueControllerEvent(this, EditVenueControllerEvent.Command.START);
		ev.setVenue(venue);
		fireList(aListener, ev);
	}

}
