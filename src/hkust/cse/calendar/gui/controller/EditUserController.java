package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.api.welcome.EditUserAPI;
import hkust.cse.calendar.api.welcome.SignUpAPI;
import hkust.cse.calendar.gui.view.base.BaseEditUserView;
import hkust.cse.calendar.gui.view.base.BaseEditUserView.EditUserViewEvent;
import hkust.cse.calendar.gui.view.base.BaseEditUserView;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class EditUserController extends EventSource implements Controller {
	private BaseEditUserView view;
	private User user;
	private List<GenListener<EditUserControllerEvent>> aListener = new ArrayList<GenListener<EditUserControllerEvent>>();
	private GenListener<EditUserViewEvent> createUserViewListener = new GenListener<EditUserViewEvent>() {

		@Override
		public void fireEvent(EditUserViewEvent e) {
			switch(e.getCommand()) {
			case EDIT:
				User u = e.getUser();
				if(u.getUsername().length() == 0) {
					JOptionPane.showMessageDialog(view, "Please fill in username", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(u.getEmail().length() == 0) {
					JOptionPane.showMessageDialog(view, "Please fill in email", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(u.getFullname().length() == 0) {
					JOptionPane.showMessageDialog(view, "Please fill in full name", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				EditUserAPI api = new EditUserAPI(user, u, e.getPassword());
				api.addDoneListener(new GenListener<APIRequestEvent>() {

					@Override
					public void fireEvent(APIRequestEvent e) {
						JSONObject json =  e.getJSON();
						try {
							int rtnCode = json.getInt("rtnCode");
							if(rtnCode == 200) {
								JOptionPane.showMessageDialog(view, "User changed.");
								User u = new User(json.getJSONObject("user"));
								DCalendarApp.getApp().setCurrentUser(u);
								view.dispose();
							}
							else if(rtnCode == 201) {
								JOptionPane.showMessageDialog(view, "Username already exists!", "Duplicate Username", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch(JSONException ex) {
							
						}
					}
				
				});
				Thread thrd = new Thread(new APIHandler(api));
				thrd.start();
				break;
			case CLOSE:
				view.dispose();
				break;
			}
			
		}
		
	};
	
	public EditUserController(User u, BaseEditUserView view) {
		setView(view);
		this.user = u;
	}
	
	public BaseEditUserView getView() {
		return view;
	}
	
	public void setView(BaseEditUserView view) {
		this.view = view;
		this.view.addEditUserEventListener(createUserViewListener);
		addEditUserControllerEventListener(view);
	}
	
	public void addEditUserControllerEventListener(GenListener<EditUserControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		EditUserControllerEvent ev = new EditUserControllerEvent(this, EditUserControllerEvent.Command.START);
		ev.setUser(user);
		fireList(aListener, ev);
	}

}
