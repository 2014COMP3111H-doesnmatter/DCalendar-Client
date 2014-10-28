package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.DetailsControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseDetailsView;
import hkust.cse.calendar.model.Appointment;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class PrimDetailsView extends BaseDetailsView implements ActionListener {
	private JButton exitBut;
	private JTextArea area;

	public PrimDetailsView() {
		paintContent();
		this.setSize(500, 350);
		pack();

	}

	public void paintContent() {

		Container content = getContentPane();
		
		JScrollPane panel = new JScrollPane();
		Border border = new TitledBorder(null, "Information");
		panel.setBorder(border);

		area = new JTextArea(25, 40);
//		area.setPreferredSize(new Dimension(400, 300));

		panel.getViewport().add(area);

		exitBut = new JButton("Exit");
		exitBut.addActionListener(this);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));

		p2.add(exitBut);

		content.add("Center", panel);
		content.add("South", p2);
		
		this.setVisible(false);

	}

	public void displayText(Appointment appt) {
		Date startTime = new Date(appt.getStartTime());
		Date endTime = new Date(appt.getEndTime());
		
		SimpleDateFormat format = new SimpleDateFormat("kk:mm");
		String time = format.format(startTime) + "-" + format.format(endTime);

		area.setText("Appointment Information \n");
		area.append("Title: " + appt.getName() + "\n");
		area.append("Time: " + time + "\n");
		area.append("\n\nDescription: \n" + appt.getInfo());
		area.setEditable(false);
	}

	@Override
	public void fireEvent(DetailsControllerEvent e) {
		DetailsControllerEvent.Command command = e.getCommand();
		if(command == DetailsControllerEvent.Command.START) {
			setVisible(true);
		}
		else if(command == DetailsControllerEvent.Command.UPDATE_TEXT) {
			displayText(e.getAppt());
		}
	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == exitBut) {
			DetailsViewEvent ev = new DetailsViewEvent(this, DetailsViewEvent.Command.EXIT);
			triggerDetailsViewEvent(ev);
		}
	}

}
