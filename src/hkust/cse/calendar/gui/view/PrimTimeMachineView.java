package hkust.cse.calendar.gui.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hkust.cse.calendar.gui.controller.TimeMachineControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView;
import hkust.cse.calendar.gui.view.base.BaseTimeMachineView.TimeMachineViewEvent;

public class PrimTimeMachineView extends BaseTimeMachineView implements	ActionListener
{
	private JTextField year;
	private JTextField month;
	private JTextField day;
	private JTextField hour;
	private JTextField minute;
	private JTextField second;
	private JButton setTimeButton;
	private JButton doneButton;
	
	
	public PrimTimeMachineView()
	{
		setTitle("Time Machine");
		
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
		messPanel.add(new JLabel("Please set your time."));
		top.add(messPanel);
		
		JPanel yearPanel = new JPanel();
		yearPanel.add(new JLabel("Year:"));
		year = new JTextField(15);
		//TODO: remove this default
		year.setText("year");
		yearPanel.add(year);
		top.add(yearPanel);
		
		JPanel monthPanel = new JPanel();
		monthPanel.add(new JLabel("Month:"));
		month = new JTextField(15);
		//TODO: remove this default
		month.setText("month");
		monthPanel.add(month);
		top.add(monthPanel);
		
		JPanel dayPanel = new JPanel();
		dayPanel.add(new JLabel("Day:"));
		day = new JTextField(15);
		//TODO: remove this default
		day.setText("day");
		dayPanel.add(day);
		top.add(dayPanel);
		
		JPanel hourPanel = new JPanel();
		hourPanel.add(new JLabel("Hour:"));
		hour = new JTextField(15);
		//TODO: remove this default
		hour.setText("hour");
		hourPanel.add(hour);
		top.add(hourPanel);
		
		JPanel minutePanel = new JPanel();
		minutePanel.add(new JLabel("Minute:"));
		minute = new JTextField(15);
		//TODO: remove this default
		minute.setText("minute");
		minutePanel.add(minute);
		top.add(minutePanel);
		
		JPanel secondPanel = new JPanel();
		secondPanel.add(new JLabel("Second:"));
		second = new JTextField(15);
		//TODO: remove this default
		second.setText("second");
		secondPanel.add(second);
		top.add(secondPanel);
		
		JPanel setTimePanel = new JPanel();
		setTimeButton = new JButton("Set");
		setTimeButton.addActionListener(this);
		setTimePanel.add(setTimeButton);
		top.add(setTimePanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		butPanel.add(doneButton);
		
		contentPane.add("South", butPanel);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		TimeMachineViewEvent ev = new TimeMachineViewEvent(this);
		if(e.getSource() == setTimeButton)
		{
			ev.setCommand(TimeMachineViewEvent.Command.SETTIME);
			String timeInput = year.getText()+"."+month.getText()+"."+day.getText()+" "
								+hour.getText()+":"+minute.getText()+":"+second.getText();
			Date d = null;
			try {
				d = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.ENGLISH).parse(timeInput);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Oops! Cannot parse your information. Please retry with valid numbers.", 
						"Parsing Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			long timestamp = d.getTime();
			ev.setTime(timestamp);			
			triggerTimeMachineViewEvent(ev);
		}
		
		else if(e.getSource() == doneButton)
		{
			ev.setCommand(TimeMachineViewEvent.Command.DONE);
			triggerTimeMachineViewEvent(ev);
		}

	}

	@Override
	public void fireEvent(TimeMachineControllerEvent e) {
		TimeMachineControllerEvent.Command command = e.getCommand();
		if(command == TimeMachineControllerEvent.Command.START) {
			setVisible(true);
		}
		else if(command == TimeMachineControllerEvent.Command.SETTIMEPENDING) {
			setTimeButton.setEnabled(false);
			setTimeButton.setText("Setting...");
		}
		else if(command == TimeMachineControllerEvent.Command.SETTIMECOMPLETE) {
			setTimeButton.setEnabled(true);
			setTimeButton.setText("Setting Complete. Reset.");
		}
	}
}
