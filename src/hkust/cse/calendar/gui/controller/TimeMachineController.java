package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;
import hkust.cse.calendar.model.TimeMachine;
import hkust.cse.calendar.model.TimeMachine.SetTime;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class TimeMachineController extends EventSource implements Controller
{
//	private TimeMachine model;
	private BaseTimeMachineView view;
	private List<GenListener<TimeMachineControllerEvent>> aListener = new ArrayList<GenListener<TimeMachineControllerEvent>>();
	private GenListener<TimeMachineViewEvent> timeMachineViewListener = new GenListener<TimeMachineViewEvent>() {

		@Override
		public void fireEvent(TimeMachineViewEvent e) {
			TimeMachineViewEvent.Command command = e.getCommand();
			TimeMachineControllerEvent ev = new TimeMachineControllerEvent(this);
			if(command == TimeMachineViewEvent.Command.SETTIME) {
				long timestamp = e.getTime();
				TimeMachine.getInstance().setTime(timestamp, setTimeListener);
				ev.setCommand(TimeMachineControllerEvent.Command.SETTIMEPENDING);
				fireList(aListener,ev);
			}
			
			else if(command == TimeMachineViewEvent.Command.DONE) {
				view.dispose();
			}
		}
	};
	
	private GenListener<SetTime> setTimeListener = new GenListener<SetTime>() {
		@Override
		public void fireEvent(SetTime qry) {
			TimeMachineControllerEvent ev = new TimeMachineControllerEvent(this);
			SetTime.RtnValue rtnVal = qry.getRtnValue();
			if(rtnVal == SetTime.RtnValue.COMPLETE) {
				view.dispose();
				//ev.setCommand(TimeMachineControllerEvent.Command.SETTIMECOMPLETE);
				//fireList(aListener,ev);
			}
		}
	};
	
	
	public TimeMachineController(BaseTimeMachineView view) {	

		setView(view);
	}
	
	public BaseTimeMachineView getView() {
		return view;
	}
	private void setView(BaseTimeMachineView view) {
		this.view = view;
		this.view.addTimeMachineEventListener(timeMachineViewListener);
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

