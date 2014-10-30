package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.ApptListControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseApptListView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class PrimApptListView extends BaseApptListView implements
		ActionListener
{

	public PrimApptListView()
	{
		JLabel p = new JLabel("PrimApptListView");
		this.add(p);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireEvent(ApptListControllerEvent e) {
		ApptListControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			
		}

	}
	
	

}
