package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hkust.cse.calendar.gui.domainModel.CalMainModel;
import hkust.cse.calendar.gui.domainModel.CalMainModel.CalMainModelEvent;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView.MonthSelectorViewEvent;
import hkust.cse.calendar.utils.DateTimeHelper;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class MonthSelectorController extends EventSource implements Controller
{
	private CalMainModel model;
	private BaseMonthSelectorView view;
	private List<GenListener<MonthSelectorControllerEvent>> aListener = new ArrayList<GenListener<MonthSelectorControllerEvent>>();
	private GenListener<MonthSelectorViewEvent> apptListViewListener = new GenListener<MonthSelectorViewEvent>() {

		@Override
		public void fireEvent(MonthSelectorViewEvent e) {
			MonthSelectorViewEvent.Command command = e.getCommand();
			switch(command) {
			case UPDATE:
				model.setYearMonth(e.getYear(), e.getMonth());
				break;
			case UPDATE_YEAR:
				model.setYear(e.getYear());
				break;
			case UPDATE_MONTH:
				model.setMonth(e.getMonth());
				break;
			case PREV_YEAR:
				model.setYear(model.getYear() - 1);
				break;
			case NEXT_YEAR:
				model.setYear(model.getYear() + 1);
				break;
			default:
				break;
			}
			
		}
		
	};
	
	private GenListener<CalMainModelEvent> modelListener = new GenListener<CalMainModelEvent>() {

		@Override
		public void fireEvent(CalMainModelEvent e) {
			CalMainModelEvent.Command command = e.getCommand();
			if(command == CalMainModelEvent.Command.UPDATE) {
				//Change the view to the month
				long newStamp = model.getSelectedDayStamp();
				MonthSelectorControllerEvent ev = new MonthSelectorControllerEvent(this, MonthSelectorControllerEvent.Command.UPDATE);
				ev.setSelectedDay(newStamp);
				fireList(aListener, ev);
				
			}
		}
		
	};
	public MonthSelectorController(BaseMonthSelectorView view, CalMainModel model) {
		setView(view);
		setModel(model);
	}
	
	public BaseMonthSelectorView getView() {
		return view;
	}
	
	public void setView(BaseMonthSelectorView view) {
		this.view = view;
		this.view.addMonthSelectorEventListener(apptListViewListener);
		addMonthSelectorControllerEventListener(view);
	}
	
	public CalMainModel getModel() {
		return model;
	}
	
	public void setModel(CalMainModel model) {
		this.model = model;
		this.model.addModelEventListener(modelListener);
	}
	
	public void addMonthSelectorControllerEventListener(GenListener<MonthSelectorControllerEvent> listener) {
		this.aListener.add(listener);
	}
	@Override
	public void start() {
		
	}

}
