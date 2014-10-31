package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class TimeMachineController extends EventSource implements Controller
{
	private BaseTimeMachineView view;
	private List<GenListener<TimeMachineControllerEvent>> aListener = new ArrayList<GenListener<TimeMachineControllerEvent>>();
	private GenListener<TimeMachineViewEvent> timeMahcineViewListener = new GenListener<TimeMachineViewEvent>() {

		@Override
		public void fireEvent(TimeMachineViewEvent e) {
			TimeMachineViewEvent.Command command = e.getCommand();
			TimeMachineControllerEvent ev = new TimeMachineControllerEvent(this);
			// TODO Auto-generated method stub
			if(command == TimeMachineViewEvent.Command.SETTIME) {
				long timestamp = e.setTime();
				ev.setCommand(TimeMachineControllerEvent.Command.SETTIMEPENDING);
				fireList(aListener, ev);
			}
			
			else if(command == TimeMachineViewEvent.Command.GETTIME) {
				
			}
			
			else if(command == TimeMachineViewEvent.Command.DONE) {
				view.dispose();
			}
			
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

