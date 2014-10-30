package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.MonthSelectorControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class PrimMonthSelectorView extends BaseMonthSelectorView implements
		ActionListener
{

	public PrimMonthSelectorView()
	{
		JLabel p = new JLabel("PrimMonthSelectorView");
		this.add(p);
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireEvent(MonthSelectorControllerEvent e) {
		MonthSelectorControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			
		}

	}
	
	

}
