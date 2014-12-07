package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.gui.controller.DetailsControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Venue;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class PrimDetailsView extends BaseDetailsView implements ActionListener {
	private JButton exitBut;
	private JTextArea area;
	private JButton acceptBut;
	private JButton rejectBut;
	
	final static private String[] aFrequency = {"Once", "Daily", "Weekly", "Monthly"};

	public PrimDetailsView() {
		paintContent();
		this.setSize(500, 350);
		this.setLocationRelativeTo(null);
		pack();

	}

	public void paintContent() {
		setTitle("Information");

		Container content = getContentPane();
		
		JScrollPane panel = new JScrollPane();
		Border border = new TitledBorder(null, "Information");
		panel.setBorder(border);

		area = new JTextArea(25, 40);
//		area.setPreferredSize(new Dimension(400, 300));

		panel.getViewport().add(area);

		exitBut = new JButton("Exit");
		exitBut.addActionListener(this);
		
		acceptBut = new JButton("Accept");
		acceptBut.addActionListener(this);
		
		rejectBut = new JButton("Reject");
		rejectBut.addActionListener(this);

		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

		p2.add(acceptBut);
		p2.add(Box.createRigidArea(new Dimension(20, 1)));
		p2.add(rejectBut);
		
		p2.add(Box.createGlue());
		
		p2.add(exitBut);

		content.add("Center", panel);
		content.add("South", p2);
		
		this.setVisible(false);
		

	}

	public void displayText(Appointment appt, Map<Long, Venue> aVenue) {
		setTitle(appt.getName());
		
		Date startTime = new Date(appt.getStartTime());
		Date endTime = new Date(appt.getEndTime());
		long stamp = appt.getLastDay();
		String endDay;
		if(stamp == Long.MAX_VALUE) {
			endDay = "forever";
		}
		else {
			endDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date(stamp));
		}
		long reminderAhead = appt.getReminderAhead() / (60 * 1000);
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String time = format.format(startTime) + "-" + format.format(endTime);

		area.setText("Appointment Information \n");
		area.append("Title: " + appt.getName() + "\n");
		area.append("Time: " + time + "\n");
		area.append("Start From: " + new SimpleDateFormat("yyyy-MM-dd").format(startTime) + "\n");
		area.append("Repeat: " + aFrequency[appt.getFrequency()] + " till " + endDay + "\n");
		area.append("Reminder: " + (reminderAhead == 0 ? "No": (reminderAhead + " minutes ahead")) + "\n");
		area.append("Venue: " + aVenue.get(appt.getVenueId()) + "\n");
		area.append("Initiated By: " + appt.getInitiator().getUsername() + "\n");
		if(appt.isJoint()) {
			String s = appt.getaAccepted().toString();
			area.append("Accepted By: " + s.substring(1, s.length() - 1) + "\n");
			s = appt.getaRejected().toString();
			area.append("Rejected By: " + s.substring(1, s.length() - 1) + "\n");
			s = appt.getaWaiting().toString();
			area.append("Pending: " + s.substring(1, s.length() - 1) + "\n");
		}
		area.append("\nDescription: \n" + appt.getInfo());
		area.setEditable(false);
		
		if(appt.isJoint() && appt.getaWaiting().contains(DCalendarApp.getApp().getCurrentUser())) {
			acceptBut.setEnabled(true);
			rejectBut.setEnabled(true);
		}
		else {
			acceptBut.setEnabled(false);
			rejectBut.setEnabled(false);
		}
	}

	@Override
	public void fireEvent(DetailsControllerEvent e) {
		DetailsControllerEvent.Command command = e.getCommand();
		if(command == DetailsControllerEvent.Command.START) {
			setVisible(true);
			this.setLocationRelativeTo(null);
		}
		else if(command == DetailsControllerEvent.Command.UPDATE_TEXT) {
			displayText(e.getAppt(), e.getaVenue());
		}
	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == exitBut) {
			DetailsViewEvent ev = new DetailsViewEvent(this, DetailsViewEvent.Command.EXIT);
			triggerDetailsViewEvent(ev);
		}
		else if(e.getSource() == acceptBut) {
			DetailsViewEvent ev = new DetailsViewEvent(this, DetailsViewEvent.Command.ACCEPT);
			triggerDetailsViewEvent(ev);
		}
		else if(e.getSource() == rejectBut) {
			DetailsViewEvent ev = new DetailsViewEvent(this, DetailsViewEvent.Command.REJECT);
			triggerDetailsViewEvent(ev);
		}
	}

}
