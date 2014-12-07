package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hkust.cse.calendar.api.welcome.SignUpAPI;
import hkust.cse.calendar.gui.view.base.BaseCreateUserView;
import hkust.cse.calendar.gui.view.base.BaseCreateUserView.CreateUserViewEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

public class CreateUserController extends EventSource implements Controller {
	private BaseCreateUserView view;
	private List<GenListener<CreateUserControllerEvent>> aListener = new ArrayList<GenListener<CreateUserControllerEvent>>();
	private GenListener<CreateUserViewEvent> createUserViewListener = new GenListener<CreateUserViewEvent>() {

		@Override
		public void fireEvent(CreateUserViewEvent e) {
			switch(e.getCommand()) {
			case CREATE:
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
				if(e.getPassword().length() == 0) {
					JOptionPane.showMessageDialog(view, "Please fill in password", "Missing Field", JOptionPane.ERROR_MESSAGE);
					break;
				}
				SignUpAPI api = new SignUpAPI(u, e.getPassword());
				api.addDoneListener(new GenListener<APIRequestEvent>() {

					@Override
					public void fireEvent(APIRequestEvent e) {
						JSONObject json =  e.getJSON();
						try {
							int rtnCode = json.getInt("rtnCode");
							if(rtnCode == 200) {
								JOptionPane.showMessageDialog(view, "User created");
								view.dispose();
							}
							else if(rtnCode == 201) {
								JOptionPane.showMessageDialog(view, "User already exists!", "Duplicate User", JOptionPane.ERROR_MESSAGE);
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
	
	public CreateUserController(BaseCreateUserView view) {
		setView(view);
	}
	
	public BaseCreateUserView getView() {
		return view;
	}
	
	public void setView(BaseCreateUserView view) {
		this.view = view;
		this.view.addCreateUserEventListener(createUserViewListener);
		addCreateUserControllerEventListener(view);
	}
	
	public void addCreateUserControllerEventListener(GenListener<CreateUserControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {
		fireList(aListener, new CreateUserControllerEvent(this, CreateUserControllerEvent.Command.START));
	}

}
