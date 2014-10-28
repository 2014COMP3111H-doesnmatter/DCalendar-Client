package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.gui.view.base.BaseDetailsView.DetailsViewEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class DetailsController 
extends EventSource implements Controller {
	BaseDetailsView view;
	private List<GenListener<DetailsControllerEvent>> nListener = new ArrayList<GenListener<DetailsControllerEvent>>();
	private GenListener<DetailsViewEvent> detailsViewListener = new GenListener<DetailsViewEvent>() {

		@Override
		public void fireEvent(DetailsViewEvent e) {
			
		}
		
	};
	
	public DetailsController(BaseDetailsView view) {
		this.view = view;
		this.view.addDetailsEventListener(detailsViewListener);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addDetailsEventListener(view);
	}
	
	public void addDetailsEventListener(GenListener<DetailsControllerEvent> listener) {
		nListener.add(listener);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}