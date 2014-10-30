package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView.MonthSelectorViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class MonthSelectorController extends EventSource implements Controller
{

	private BaseMonthSelectorView view;
	private List<GenListener<MonthSelectorControllerEvent>> aListener = new ArrayList<GenListener<MonthSelectorControllerEvent>>();
	private GenListener<MonthSelectorViewEvent> apptListViewListener = new GenListener<MonthSelectorViewEvent>() {

		@Override
		public void fireEvent(MonthSelectorViewEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public MonthSelectorController(BaseMonthSelectorView view)
	{
		setView(view);
	}
	public BaseMonthSelectorView getView() {
		return view;
	}
	private void setView(BaseMonthSelectorView view) {
		this.view = view;
		// TODO Auto-generated method stub
	}
	public void addMonthSelectorControllerEventListener(GenListener<MonthSelectorControllerEvent> listener) {
		this.aListener.add(listener);
	}
	@Override
	public void start() {
		
	}

}
