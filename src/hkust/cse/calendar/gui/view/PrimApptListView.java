package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.AppCellRenderer;
import hkust.cse.calendar.gui.AppList;
import hkust.cse.calendar.gui.controller.ApptListControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseApptListView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class PrimApptListView extends BaseApptListView implements
		ActionListener
{
	private static Font f = new Font("Helvetica", Font.BOLD + Font.ITALIC, 11);
	private static Font f1 = new Font("Helvetica", Font.ITALIC, 11);
	private static final String[] names = { "Time", "Appointments", "Status", "Time",
			"Appointments", "Status" };
	public static int SMALLEST_DURATION = 15;
	private static final long serialVersionUID = 1L;
	public static int OFFSET = 8;
	public static int ROWNUM = 20;
	public final static int COLORED_TITLE = 1;
	public final static int COLORED = 2;
	public final static int NOT_COLORED = 0;
	private int[][] cellCMD = new int[20][2];
	private Color[][] cellColor = new Color[20][2];
	
	private int currentRow = 0;
	private int currentCol = 0;
	private final Object[][] data = new Object[20][6];
	private JPopupMenu pop;
	private JTable tableView;
	private DragHelper dragHelper;
	private class DragHelper {
		public int pressRow;
		public int pressCol;
		public int releaseRow;
		public int releaseCol;
		private void pressResponse(MouseEvent e) {
			pressRow = tableView.getSelectedRow();
			pressCol = tableView.getSelectedColumn();
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
				pop.show(e.getComponent(), e.getX(), e.getY());
		}
		private void releaseResponse(MouseEvent e) {
			
			releaseRow = tableView.getSelectedRow();
			releaseCol = tableView.getSelectedColumn();
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
				pop.show(e.getComponent(), e.getX(), e.getY());
		}
		private void calculateDrag(MouseEvent e){
			
			if(releaseRow==pressRow){		
				currentRow = tableView.getSelectedRow()+tableView.getSelectedRowCount()-1;			
			}else{
				currentRow = releaseRow;
				
			}
			
			if(releaseCol==pressCol){			
				currentCol = tableView.getSelectedColumn()+tableView.getSelectedColumnCount()-1;
			}else{
				currentCol = releaseCol;
			}
			
		}
	}
	
	public PrimApptListView()
	{
		setLayout(new BorderLayout());
		currentRow = 0;
		currentCol = 0;
		
		TitledBorder b = BorderFactory
				.createTitledBorder("Appointment Contents");
		b.setTitleColor(new Color(102, 0, 51));
		b.setTitleFont(f);
		setBorder(b);
		this.pop = this.createContextMenu();
		
		getDataArray(data);
		TableModel dataModel = prepareTableModel();
		tableView = new JTable(dataModel) {
			public TableCellRenderer getCellRenderer(int row, int col) {
				if (col == 0 || col == 3)
					return new AppCellRenderer(new Object(), true, row, col, 1,
							null);
				else if (col == 1) {

					return new AppCellRenderer(new Object(), false, row, col,
							cellCMD[row][0], cellColor[row][0]);

				} else if (col == 4) {
					return new AppCellRenderer(new Object(), false, row, col,
							cellCMD[row][1], cellColor[row][1]);
				} else
					return new AppCellRenderer(new Object(), false, row, col,
							1, null);

			}
		};
		
		tableView.setAutoResizeMode(tableView.AUTO_RESIZE_ALL_COLUMNS);
		tableView.setRowHeight(20);
		tableView.setRowSelectionAllowed(false);
		TableColumn c = null;
		c = tableView.getColumnModel().getColumn(0);
		c.setPreferredWidth(60);
		c = tableView.getColumnModel().getColumn(3);
		c.setPreferredWidth(60);
		c = tableView.getColumnModel().getColumn(1);
		c.setPreferredWidth(215);
		c = tableView.getColumnModel().getColumn(4);
		c.setPreferredWidth(215);
		c = tableView.getColumnModel().getColumn(2);
		c.setPreferredWidth(60);
		c = tableView.getColumnModel().getColumn(5);
		c.setPreferredWidth(60);
		JTableHeader h = tableView.getTableHeader();
		h.setResizingAllowed(true);
		h.setReorderingAllowed(false);
		
		tableView.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				dragHelper.pressResponse(e);
			}

			public void mouseReleased(MouseEvent e) {
				dragHelper.releaseResponse(e);
				if(e.getButton()==1) dragHelper.calculateDrag(e);
			}
		});
		
		JScrollPane scrollpane = new JScrollPane(tableView);
		scrollpane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		scrollpane.setPreferredSize(new Dimension(695, 300));
		add(scrollpane, BorderLayout.CENTER);
		setVisible(true);
		setSize(600, 300);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireEvent(ApptListControllerEvent e) {
		ApptListControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			
		}

	}
	
	private JPopupMenu createContextMenu() {
		
		JPopupMenu pop = new JPopupMenu();
		pop.setFont(f1);
		
		JMenuItem mi;
		mi = (JMenuItem) pop.add(new JMenuItem("New"));

		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				onNewAppointment();
			}
		});

		mi = (JMenuItem) pop.add(new JMenuItem("Delete"));

		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDeleteAppointment();
			}
		});

		mi = (JMenuItem) pop.add(new JMenuItem("Edit"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onEditAppointment();
			}
		});
		pop.add(new JPopupMenu.Separator());
		JMenuItem j = new JMenuItem("Details");
		j.setFont(f);
		pop.add(j);

		j.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDescribAppointment();
			}
		});
		
		return pop;
	}
	
	private void onNewAppointment() {
		ApptListViewEvent e = new ApptListViewEvent(this, ApptListViewEvent.Command.NEW_APPOINTMENT);
		this.triggerApptListViewEvent(e);
	}
	private void onDeleteAppointment() {
		ApptListViewEvent e = new ApptListViewEvent(this, ApptListViewEvent.Command.DELETE_APPOITNMENT);
		this.triggerApptListViewEvent(e);
	}
	private void onEditAppointment() {
		ApptListViewEvent e = new ApptListViewEvent(this, ApptListViewEvent.Command.EDIT_APPOINTMENT);
		this.triggerApptListViewEvent(e);
	}
	private void onDescribAppointment() {
		ApptListViewEvent e = new ApptListViewEvent(this, ApptListViewEvent.Command.DESCRIB_APPOINTMENT);
		this.triggerApptListViewEvent(e);
	}
	
	public TableModel prepareTableModel() {

		TableModel dataModel = new AbstractTableModel() {

			public int getColumnCount() {
				return names.length;
			}

			public int getRowCount() {
				return 20;
			}

			public Object getValueAt(int row, int col) {
				return data[row][col];
			}

			public String getColumnName(int column) {
				return names[column];
			}

			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public void setValueAt(Object aValue, int row, int column) {
				data[row][column] = aValue;
			}
		};
		return dataModel;
	}

	public void getDataArray(Object[][] data) {
		int tam = 480;
		int tpm = 60;
		String s;
		String am = new String("AM");
		String pm = new String("PM");

		int i;
		for (i = 0; i < ROWNUM; i++) {
			if (tam % 60 == 0)
				data[i][0] = (tam / 60) + ":" + "00" + am;
			else
				data[i][0] = (tam / 60) + ":" + (tam % 60) + am;
			tam = tam + SMALLEST_DURATION;
			cellCMD[i][0] = NOT_COLORED;
			cellCMD[i][1] = NOT_COLORED;
			cellColor[i][0] = Color.white;
			cellColor[i][1] = Color.white;
		}
		for (i = 0; i < ROWNUM; i++) {
			if (tpm % 60 == 0)
				data[i][3] = (tpm / 60) + ":" + "00" + pm;
			else
				data[i][3] = (tpm / 60) + ":" + (tpm % 60) + pm;
			tpm = tpm + SMALLEST_DURATION;
		}

	}
	
}

class AppCellRenderer extends DefaultTableCellRenderer {
	private int r;
	private int c;

	// public final static int EARLIEST_TIME = 480;
	//
	// public final static int LATEST_TIME = 1050;
	//
	// public final static int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31,
	// 30,
	// 31, 30, 31 };

	public AppCellRenderer(Object value, boolean override, int row, int col,
			int colorCMD, Color currColor) {

		Font f1 = new Font("Helvetica", Font.ITALIC, 11);
		if (override) {
			if (row % 2 == 0)
				setBackground(new Color(153, 204, 255));
			else
				setBackground(new Color(204, 204, 255));
			setForeground(Color.black);

		}
		if (col == 2 || col == 5)
			setFont(f1);
		if (col != 0 && col != 3)
			setHorizontalAlignment(SwingConstants.LEFT);
		else
			setHorizontalAlignment(SwingConstants.RIGHT);
		if (col == 1 || col == 4) {
			if (colorCMD == AppList.COLORED_TITLE) {
				setBackground(currColor);
				setForeground(Color.black);
			} else if (colorCMD == AppList.COLORED) {
				setBackground(currColor);
				setForeground(currColor);
			}

		}
		setVerticalAlignment(SwingConstants.TOP);
	}

	public int row() {
		return r;
	}

	public int col() {
		return c;
	}
}
