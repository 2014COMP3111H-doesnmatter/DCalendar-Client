package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.{placeholder}ControllerEvent;
import hkust.cse.calendar.gui.view.base.Base{placeholder}View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class Prim{placeholder}View extends Base{placeholder}View implements ActionListener {
	public Prim{placeholder}View() {
		JLabel p = new JLabel("Prim{placeholder}View");
		this.add(p);
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireEvent({placeholder}ControllerEvent e) {
		{placeholder}ControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			// TODO Auto-generated method stub
		}

	}
	
}
