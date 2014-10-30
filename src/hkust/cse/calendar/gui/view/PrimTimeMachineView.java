package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.TimeMachineControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class PrimTimeMachineView extends BaseTimeMachineView implements
		ActionListener
{

	public PrimTimeMachineView()
	{
		JLabel p = new JLabel("PrimTimeMachineView");
		this.add(p);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireEvent(TimeMachineControllerEvent e) {
		TimeMachineControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			
		}

	}
	
	

}
