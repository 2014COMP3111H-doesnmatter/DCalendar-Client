package hkust.cse.calendar.gui.controller;

import java.util.ArrayList;
import java.util.List;

import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseLoginView;
import hkust.cse.calendar.gui.view.base.BaseApptListView.ApptListViewEvent;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class ApptListController extends EventSource implements Controller
{

	private BaseApptListView view;
	private List<GenListener<ApptListControllerEvent>> aListener = new ArrayList<GenListener<ApptListControllerEvent>>();
	private GenListener<ApptListViewEvent> apptListViewListener = new GenListener<ApptListViewEvent>() {

		@Override
		public void fireEvent(ApptListViewEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public ApptListController(BaseApptListView view)
	{
		setView(view);
	}
	public BaseApptListView getView() {
		return view;
	}
	private void setView(BaseApptListView view) {
		this.view = view;
		
	}
	public void addApptListControllerEventListener(GenListener<ApptListControllerEvent> listener) {
		this.aListener.add(listener);
	}
	@Override
	public void start() {
		ApptListControllerEvent e = new ApptListControllerEvent(this, ApptListControllerEvent.Command.START);
		fireList(aListener, e);
	}

}
