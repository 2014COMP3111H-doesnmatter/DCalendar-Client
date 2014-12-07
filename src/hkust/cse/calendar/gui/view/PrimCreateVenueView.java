package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.CreateVenueControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseCreateVenueView;
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

public class PrimCreateVenueView extends BaseCreateVenueView implements ActionListener {
	JTextField venuenameF, capacityF;
	JPasswordField passwordF;
	
	JButton signBtn, cancelBtn;
	
	public PrimCreateVenueView() {
		Container contentPane;
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				CreateVenueViewEvent ev = new CreateVenueViewEvent(this);
				ev.setCommand(CreateVenueViewEvent.Command.CLOSE);
				triggerCreateVenueViewEvent(ev);
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
		
		signBtn = new JButton("Create");
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
		CreateVenueViewEvent ev = new CreateVenueViewEvent(this);
		if(obj == signBtn) {
			ev.setCommand(CreateVenueViewEvent.Command.CREATE);
			Venue u = new Venue();
			u.setName(venuenameF.getText());
			u.setCapacity(Integer.parseInt(capacityF.getText()));
			ev.setVenue(u);
			triggerCreateVenueViewEvent(ev);
		}
		else if(obj == cancelBtn) {
			ev.setCommand(CreateVenueViewEvent.Command.CLOSE);
			triggerCreateVenueViewEvent(ev);
		}
	}

	@Override
	public void fireEvent(CreateVenueControllerEvent e) {
		CreateVenueControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			this.setVisible(true);
		}

	}
	
}
