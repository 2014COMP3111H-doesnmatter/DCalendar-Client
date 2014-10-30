package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class TimeMachineController extends EventSource implements Controller
{

	private BaseTimeMachineView view;
	private List<GenListener<TimeMachineControllerEvent>> aListener = new ArrayList<GenListener<TimeMachineControllerEvent>>();
	private GenListener<TimeMachineViewEvent> apptListViewListener = new GenListener<TimeMachineViewEvent>() {

		@Override
		public void fireEvent(TimeMachineViewEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public TimeMachineController(BaseTimeMachineView view)
	{
		setView(view);
	}
	public BaseTimeMachineView getView() {
		return view;
	}
	private void setView(BaseTimeMachineView view) {
		this.view = view;
		addTimeMachineControllerEventListener(view);
		
	}
	public void addTimeMachineControllerEventListener(GenListener<TimeMachineControllerEvent> listener) {
		this.aListener.add(listener);
	}
	@Override
	public void start() {
		TimeMachineControllerEvent e = new TimeMachineControllerEvent(this, TimeMachineControllerEvent.Command.START);
		fireList(aListener, e);
	}
}

