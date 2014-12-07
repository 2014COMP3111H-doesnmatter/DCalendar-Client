package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.EditVenueControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseEditVenueView;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;
import hkust.cse.calendar.model.Venue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

public class PrimEditVenueView extends BaseEditVenueView implements ActionListener {
	JTextField venuenameF, capacityF;
	JPasswordField passwordF;
	
	JButton signBtn, cancelBtn;
	
	public PrimEditVenueView() {
		Container contentPane;
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				EditVenueViewEvent ev = new EditVenueViewEvent(this);
				ev.setCommand(EditVenueViewEvent.Command.CLOSE);
				triggerEditVenueViewEvent(ev);
			}
		});
		
		setTitle("Sign Up");
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(0, 1, 0, 10));
		top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		top.add(new JLabel("Please enter venue information"));

		Border defaultBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		venuenameF = new JTextField(15);
		venuenameF.setUI(new HintTextFieldUI("   Venue Name", false, Color.LIGHT_GRAY));
		venuenameF.setBorder(defaultBorder);
		top.add(venuenameF);

		capacityF = new JTextField(15);
		capacityF.setUI(new HintTextFieldUI("   Capacity", false, Color.LIGHT_GRAY));
		capacityF.setBorder(defaultBorder);
		top.add(capacityF);
		
		contentPane.add(top, BorderLayout.CENTER);
		

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.X_AXIS));
		
		signBtn = new JButton("Edit");
		signBtn.addActionListener(this);
		butPanel.add(signBtn);
		
		butPanel.add(Box.createGlue());
		

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		butPanel.add(cancelBtn);
		
		butPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		contentPane.add(butPanel, BorderLayout.SOUTH);
		
		
		pack();
		this.setVisible(false);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		EditVenueViewEvent ev = new EditVenueViewEvent(this);
		if(obj == signBtn) {
			ev.setCommand(EditVenueViewEvent.Command.EDIT);
			Venue u = new Venue();
			u.setName(venuenameF.getText());
			u.setCapacity(Integer.parseInt(capacityF.getText()));
			ev.setVenue(u);
			triggerEditVenueViewEvent(ev);
		}
		else if(obj == cancelBtn) {
			ev.setCommand(EditVenueViewEvent.Command.CLOSE);
			triggerEditVenueViewEvent(ev);
		}
	}

	@Override
	public void fireEvent(EditVenueControllerEvent e) {
		EditVenueControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			Venue u = e.getVenue();
			venuenameF.setText(u.getName());
			capacityF.setText(String.valueOf(u.getCapacity()));
			this.setVisible(true);
		}

	}
	
}
