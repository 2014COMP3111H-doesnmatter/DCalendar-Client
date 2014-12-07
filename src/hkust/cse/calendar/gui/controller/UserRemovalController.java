package hkust.cse.calendar.gui.controller;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.api.venue.ConfirmVenueRemovalAPI;
import hkust.cse.calendar.api.welcome.ConfirmUserRemovalAPI;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class UserRemovalController implements Controller {
	private User user;

	public UserRemovalController(User user) {
		this.user = user;
	}
	
	@Override
	public void start() {
		String message = "You are to be removed by administrator.\n" +
				"All you appointments are to be removed also.\n" +
				"You will be log out upon confirmation.";
		int n = JOptionPane.showConfirmDialog(null, message, "User Removal", JOptionPane.OK_CANCEL_OPTION);
		
		if(n == 0) {
			ConfirmUserRemovalAPI api = new ConfirmUserRemovalAPI(user);
			api.addDoneListener(new GenListener<APIRequestEvent>() {

				@Override
				public void fireEvent(APIRequestEvent e) {
					JSONObject json = e.getJSON();
					try {
						int rtnCode = json.getInt("rtnCode");
						if(rtnCode == 200) {
							DCalendarApp app = DCalendarApp.getApp();
							APIHandler.resetCookie();
							BaseLoginView loginView = app.getViewManager().getLoginView();
							app.switchController(new LoginController(loginView));
							app.getCurrentUser().logout();
							CalMainController.getInstance().getView().dispose();
						}
					}
					catch(JSONException ex) {
						ex.printStackTrace();
					}
					
				}
				
			});
			Thread thrd = new Thread(new APIHandler(api));
			thrd.start();
		}
	}
	
	
}