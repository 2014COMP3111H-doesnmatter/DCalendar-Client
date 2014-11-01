package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.VenueManagerControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseVenueManagerView;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class PrimVenueManagerView extends BaseVenueManagerView {
	private JTextArea disp;
	private JTextField venueInput;
	private static final EmptyBorder MARGIN = new EmptyBorder(3, 3, 3, 3);
	private static final CompoundBorder DEFAULT_BORDER = new CompoundBorder(MARGIN, MARGIN);
	public PrimVenueManagerView() {
		this.setTitle("Manage Locations");
		
		
		JPanel mainP = new JPanel();
		
		mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
		mainP.setBorder(DEFAULT_BORDER);
		
		disp = new JTextArea(10, 31);
		mainP.add(disp);
		disp.setBackground(this.getBackground());
		disp.setEditable(false);
		
		venueInput = new JTextField(25);
		mainP.add(venueInput);
		venueInput.setUI(new HintTextFieldUI("   New Location Name", false, Color.LIGHT_GRAY));
		
		JPanel btnWrap = new JPanel();
		mainP.add(btnWrap);
		JButton addBtn = new JButton("Add");
		btnWrap.add(addBtn);
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd();
			}
			
		});

		this.getContentPane().add(mainP);
		pack();
		
		this.setVisible(true);
		
	}


	@Override
	public void fireEvent(VenueManagerControllerEvent e) {
		
		VenueManagerControllerEvent.Command command = e.getCommand();
		switch(command) {
		case REFRESH:
			this.venueInput.setText("");
			StringBuilder sb = new StringBuilder();
			String[] aName = e.aVenueName;
			for(String iName:aName) {
				sb.append(iName);
				sb.append("\n");
			}
			disp.setText(sb.toString());
			this.pack();
			break;
		}

	}
	
	private void onAdd() {
		VenueManagerViewEvent e = new VenueManagerViewEvent(this);
		e.setCommand(VenueManagerViewEvent.Command.ADD_VENUE);
		e.name = venueInput.getText();
		this.triggerVenueManagerViewEvent(e);
	}
	
}
