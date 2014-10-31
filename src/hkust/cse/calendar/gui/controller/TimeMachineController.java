package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.domainModel.CalMainModel.CalMainModelEvent;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;
import hkust.cse.calendar.model.TimeMachine;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class TimeMachineController extends EventSource implements Controller
{
	private TimeMachine model;
	private BaseTimeMachineView view;
	private List<GenListener<TimeMachineControllerEvent>> aListener = new ArrayList<GenListener<TimeMachineControllerEvent>>();
	private GenListener<TimeMachineViewEvent> timeMachineViewListener = new GenListener<TimeMachineViewEvent>() {

		@Override
		public void fireEvent(TimeMachineViewEvent e) {
			TimeMachineViewEvent.Command command = e.getCommand();
			TimeMachineControllerEvent ev = new TimeMachineControllerEvent(this);
			// TODO Auto-generated method stub
			if(command == TimeMachineViewEvent.Command.GETTIME) {
				long timestamp = model.getTime().getTime();
				ev.setCommand(TimeMachineControllerEvent.Command.GETTIMEPENDING);
			}
			
			else if(command == TimeMachineViewEvent.Command.SETTIME) {
				model.setTime(new Date().getTime());
				
				ev.setCommand(TimeMachineControllerEvent.Command.SETTIMEPENDING);
				
				fireList(aListener,ev);
			}
			
			else if(command == TimeMachineViewEvent.Command.DONE) {
				view.dispose();
			}
			
		}
		
	};
	
	private GenListener<TimeMachineViewEvent> modelListener = new GenListener<TimeMachineViewEvent>() {

		@Override
		public void fireEvent(TimeMachineViewEvent e) {
			TimeMachineViewEvent.Command command = e.getCommand();
			if(command == TimeMachineViewEvent.Command.GETTIME) {
				long timeStamp = model.getTime().getTime();
				TimeMachineControllerEvent ev = new TimeMachineControllerEvent(this, TimeMachineControllerEvent.Command.GETTIMEPENDING);
				fireList(aListener, ev);
				
			}
			if(command == TimeMachineViewEvent.Command.SETTIME) {
				model.setTime(new Date().getTime());
				TimeMachineControllerEvent ev = new TimeMachineControllerEvent(this, TimeMachineControllerEvent.Command.SETTIMEPENDING);
				fireList(aListener, ev);
				
			}
		}
	};
	
	
	public TimeMachineController(BaseTimeMachineView view) {	

		setView(view);
		setModel(model);
	}
	
	public BaseTimeMachineView getView() {
		return view;
	}
	private void setView(BaseTimeMachineView view) {
		this.view = view;
		this.view.addTimeMachineEventListener(timeMachineViewListener);
		addTimeMachineControllerEventListener(view);
	}
	public TimeMachine getModel() {
		return model;
	}
	
	public void setModel(TimeMachine model) {
		this.model = model;
		//this.model.addModelEventListener(modelListener);
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

