package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.gui.view.PrimEditUserView;
import hkust.cse.calendar.gui.view.base.BaseUserManagerView;
import hkust.cse.calendar.gui.view.base.BaseUserManagerView.UserManagerViewEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.User.UserListQuery;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class UserManagerController extends EventSource implements Controller {
	private BaseUserManagerView view;
	private List<GenListener<UserManagerControllerEvent>> aListener = new ArrayList<GenListener<UserManagerControllerEvent>>();
	private GenListener<UserManagerViewEvent> userManagerViewListener = new GenListener<UserManagerViewEvent>() {

		@Override
		public void fireEvent(UserManagerViewEvent e) {
			User u;
			switch(e.getCommand()) {
			case EDIT:
				u = e.getUser();

				EditUserController userCtrl = new EditUserController(u, new PrimEditUserView());
				userCtrl.start();
				break;
			case DELETE:
				u = e.getUser();
				int n = JOptionPane.showConfirmDialog(view, "Remove User " + u + " ?\n" + 
				"Deletion will finish after target user confirms.",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION) {
					u.remove(null);
				}
				break;
			case CLOSE:
				view.dispose();
				break;
			}
			
		}
		
	};
	
	public UserManagerController(BaseUserManagerView view) {
		setView(view);
	}
	
	public BaseUserManagerView getView() {
		return view;
	}
	
	public void setView(BaseUserManagerView view) {
		this.view = view;
		this.view.addUserManagerEventListener(userManagerViewListener);
		addUserManagerControllerEventListener(view);
	}
	
	public void addUserManagerControllerEventListener(GenListener<UserManagerControllerEvent> listener) {
		this.aListener.add(listener);
	}
	
	@Override
	public void start() {

		User.listUser(new GenListener<UserListQuery>() {

			@Override
			public void fireEvent(UserListQuery e) {
				if(e.getRtnValue() == UserListQuery.RtnValue.LIST_OK) {
					UserManagerControllerEvent ev = new UserManagerControllerEvent(UserManagerController.this);
					ev.setCommand(UserManagerControllerEvent.Command.INFO_UPDATE);
					ev.setaUser(e.getaUser());
					fireList(aListener, ev);
				}
			}
			
		});
		
		UserManagerControllerEvent e = new UserManagerControllerEvent(this);
		e.setCommand(UserManagerControllerEvent.Command.START);
		fireList(aListener, e);
	}

}
