package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.gui.view.base.Base{placeholder}View;
import hkust.cse.calendar.gui.view.base.Base{placeholder}View.{placeholder}ViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class {placeholder}Controller extends EventSource implements Controller
{

	private Base{placeholder}View view;
	private List<GenListener<{placeholder}ControllerEvent>> aListener = new ArrayList<GenListener<{placeholder}ControllerEvent>>();
	private GenListener<{placeholder}ViewEvent> apptListViewListener = new GenListener<{placeholder}ViewEvent>() {

		@Override
		public void fireEvent({placeholder}ViewEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public {placeholder}Controller(Base{placeholder}View view)
	{
		setView(view);
	}
	public Base{placeholder}View getView() {
		return view;
	}
	private void setView(Base{placeholder}View view) {
		this.view = view;
		// TODO Auto-generated method stub
	}
	public void add{placeholder}ControllerEventListener(GenListener<{placeholder}ControllerEvent> listener) {
		this.aListener.add(listener);
	}
	@Override
	public void start() {
		
	}

}
