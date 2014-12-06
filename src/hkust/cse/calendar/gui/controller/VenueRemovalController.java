package hkust.cse.calendar.gui.controller;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.api.venue.ConfirmVenueRemovalAPI;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class VenueRemovalController implements Controller {
	private Venue venue;

	public VenueRemovalController(Venue venue) {
		this.venue = venue;
	}
	
	@Override
	public void start() {
		String message = String.format("Venue %s is to be removed.\n" +
				"All appointment at this venue will be removed also.\n" +
				"Please adjust your appointments if you need to keep them.", venue.getName());
		int n = JOptionPane.showConfirmDialog(null, message, "Venue Removal", JOptionPane.OK_CANCEL_OPTION);
		
		if(n == 0) {
			ConfirmVenueRemovalAPI api = new ConfirmVenueRemovalAPI(venue);
			Thread thrd = new Thread(new APIHandler(api));
			thrd.start();
		}
	}
	
	
}