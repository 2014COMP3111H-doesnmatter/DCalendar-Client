package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.VenueManagerControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class PrimVenueManagerView extends BaseVenueManagerView implements ActionListener {
	public PrimVenueManagerView() {
		JLabel p = new JLabel("PrimVenueManagerView");
		this.add(p);
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireEvent(VenueManagerControllerEvent e) {
		VenueManagerControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			// TODO Auto-generated method stub
		}

	}
	
}
