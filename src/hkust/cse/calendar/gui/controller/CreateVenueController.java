package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.api.welcome.SignUpAPI;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.collection.VenueCollection.VenueQuery;
import hkust.cse.calendar.gui.view.base.BaseCreateVenueView;
import hkust.cse.calendar.gui.view.base.BaseCreateVenueView.CreateVenueViewEvent;
import hkust.cse.calendar.gui.view.base.BaseCreateVenueView;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class CreateVenueController extends EventSource implements Controller {
	private BaseCreateVenueView view;
	private List<GenListener<CreateVenueControllerEvent>> aListener = new ArrayList<GenListener<CreateVenueControllerEvent>>();
	private GenListener<CreateVenueViewEvent> createVenueViewListener = new GenListener<CreateVenueViewEvent>() {

		@Override
		public void fireEvent(CreateVenueViewEvent e) {
			switch(e.getCommand()) {
			case CREATE:
				Venue u = e.getVenue();
				if(u.getName().length() == 0) {
					JOptionPane.showMessageDialog(view, "Please fill in Venue name", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(u.getCapacity() <= 0) {
					JOptionPane.showMessageDialog(view, "Please fill in capacity", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				VenueCollection.getInstance().addVenue(u, new GenListener<VenueQuery>() {

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
	
	public CreateVenueController(BaseCreateVenueView view) {
		setView(view);
	}
	
	public BaseCreateVenueView getView() {
		return view;
	}
	
	public void setView(BaseCreateVenueView view) {
		this.view = view;
		this.view.addCreateVenueEventListener(createVenueViewListener);
		addCreateVenueControllerEventListener(view);
	}
	
	public void addCreateVenueControllerEventListener(GenListener<CreateVenueControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		CreateVenueControllerEvent ev = new CreateVenueControllerEvent(this, CreateVenueControllerEvent.Command.START);
		fireList(aListener, ev);
	}

}
