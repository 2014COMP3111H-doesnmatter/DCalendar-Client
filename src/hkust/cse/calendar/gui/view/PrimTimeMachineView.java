package hkust.cse.calendar.gui.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JFormattedTextField;

import hkust.cse.calendar.gui.controller.TimeMachineControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;

public class PrimTimeMachineView extends BaseTimeMachineView implements	ActionListener
{
//	private JTextField year;
//	private JTextField month;
//	private JTextField day;
//	private JTextField hour;
//	private JTextField minute;
//	private JTextField second;
	private JFormattedTextField date;
	private JButton getTimeButton;
	private JButton setTimeButton;
	private JButton doneButton;

	public PrimTimeMachineView()
	{
		setTitle("Time Machine");
//		JLabel p = new JLabel("PrimTimeMachineView");
//		this.add(p);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				TimeMachineViewEvent ev = new TimeMachineViewEvent(this);
				ev.setCommand(TimeMachineViewEvent.Command.DONE);
				triggerTimeMachineViewEvent(ev);
			}
		});
		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Get your time or fill in a new time"));
		top.add(messPanel);
		
//		JPanel yearPanel = new JPanel();
//		yearPanel.add(new JLabel("Year:"));
//		year = new JTextField(15);
//		//TODO: remove this default
//		year.setText("year");
//		yearPanel.add(year);
//		top.add(year);
//		
//		JPanel monthPanel = new JPanel();
//		monthPanel.add(new JLabel("Month:"));
//		month = new JTextField(15);
//		//TODO: remove this default
//		month.setText("month");
//		monthPanel.add(month);
//		top.add(month);
//		
//		JPanel dayPanel = new JPanel();
//		dayPanel.add(new JLabel("Day:"));
//		day = new JTextField(15);
//		//TODO: remove this default
//		day.setText("day");
//		dayPanel.add(day);
//		top.add(day);
		
		
//		JPanel Panel = new JPanel();
//		pwPanel.add(new JLabel("Password:  "));
//		password = new JPasswordField(15);
//		//TODO: remove this default
//		password.setText("122333");
//		password.addActionListener(this);
//		pwPanel.add(password);
//		top.add(pwPanel);
//		
//		JPanel signupPanel = new JPanel();
//		signupPanel.add(new JLabel("If you don't have an account, please sign up:"));
//		signupButton = new JButton("Sign up now");
//		signupButton.addActionListener(this);
//		signupPanel.add(signupButton);
//		top.add(signupPanel);
//		
//		contentPane.add("North", top);
//		
//		JPanel butPanel = new JPanel();
//		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
//
//		button = new JButton("Log in");
//		button.addActionListener(this);
//		butPanel.add(button);
//		
//		closeButton = new JButton("Close program");
//		closeButton.addActionListener(this);
//		butPanel.add(closeButton);
//		
//		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
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
			this.setVisible(true);
		}

	}
	
	

}
