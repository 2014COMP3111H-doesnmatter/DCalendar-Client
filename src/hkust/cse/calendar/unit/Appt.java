package hkust.cse.calendar.unit;

import java.io.Serializable;
import java.util.LinkedList;

public class Appt implements Serializable {

	private TimeSpan mTimeSpan;					// Include day, start time and end time of the appointments

	private String mTitle;						// The Title of the appointments

	private String mInfo;						// Store the content of the appointments description

	private int mApptID;						// The appointment id
	
	private int joinApptID;						// The join appointment id

	private boolean isjoint;					// The appointment is a joint appointment
	
	private LinkedList<Integer> attend;			// The Attendant list
	
	private LinkedList<Integer> reject;			// The reject list
	
	private LinkedList<Integer> waiting;			// The waiting list
	
	public Appt() {								// A default constructor used to set all the attribute to default values
		mApptID = 0;
		mTimeSpan = null;
		mTitle = "Untitled";
		mInfo = "";
		isjoint = false;
		attend = new LinkedList<Integer>();
		reject = new LinkedList<Integer>();
		waiting = new LinkedList<Integer>();
		joinApptID = -1;
	}

	// Getter of the mTimeSpan
	public TimeSpan TimeSpan() {
		return mTimeSpan;
	}
	
	// Getter of the appointment title
	public String getTitle() {
		return mTitle;
	}

	// Getter of appointment description
	public String getInfo() {
		return mInfo;
	}

	// Getter of the appointment id
	public int getID() {
		return mApptID;
	}
	
	// Getter of the join appointment id
	public int getJoinID(){
		return joinApptID;
	}

	public void setJoinID(int joinID){
		this.joinApptID = joinID;
	}
	// Getter of the attend LinkedList<String>
	public LinkedList<Integer> getAttendList(){
		return attend;
	}
	
	// Getter of the reject LinkedList<String>
	public LinkedList<Integer> getRejectList(){
		return reject;
	}
	
	// Getter of the waiting LinkedList<String>
	public LinkedList<Integer> getWaitingList(){
		return waiting;
	}
	
	public LinkedList<Integer> getAllPeople(){
		
		LinkedList<Integer> allList = new LinkedList<Integer>();
		allList.addAll(attend);
		allList.addAll(reject);
		allList.addAll(waiting);
		return allList;
	}
	
	public void addAttendant(Integer addID){
		if (attend == null)
			attend = new LinkedList<Integer>();
		attend.add(addID);
	}
	
	public void addReject(int addID){
		if (reject == null)
			reject = new LinkedList<Integer>();
		reject.add(addID);
	}
	
	public void addWaiting(Integer addID){
		if (waiting == null)
			waiting = new LinkedList<Integer>();
		waiting.add(addID);
	}
	
	public void setWaitingList(LinkedList<Integer> waitingList){
		waiting = waitingList;
	}
	
	public void setWaitingList(String[] waitingList){
		/*
		LinkedList<Integer> tempLinkedList = new LinkedList<Integer>();
		if (waitingList !=null){
			for (int a=0; a<waitingList.length; a++){
				tempLinkedList.add(waitingList[a].trim());
			}
		}
		waiting = tempLinkedList;*/
	}
	
	public void setRejectList(LinkedList<Integer> rejectLinkedList) {
		//reject = rejectLinkedList;
	}
	
	public void setRejectList(String[] rejectList){
		/*
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (rejectList !=null){
			for (int a=0; a<rejectList.length; a++){
				tempLinkedList.add(rejectList[a].trim());
			}
		}
		reject = tempLinkedList;*/
	}
	
	public void setAttendList(LinkedList<String> attendLinkedList) {
		//attend = attendLinkedList;
	}
	
	public void setAttendList(String[] attendList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (attendList !=null){
			for (int a=0; a<attendList.length; a++){
				tempLinkedList.add(attendList[a].trim());
			}
		}
		//attend = tempLinkedList;
	}
	// Getter of the appointment title
	public String toString() {
		return mTitle;
	}

	// Setter of the appointment title
	public void setTitle(String t) {
		mTitle = t;
	}

	// Setter of the appointment description
	public void setInfo(String in) {
		mInfo = in;
	}

	// Setter of the mTimeSpan
	public void setTimeSpan(TimeSpan d) {
		mTimeSpan = d;
	}

	// Setter if the appointment id
	public void setID(int id) {
		mApptID = id;
	}
	
	// check whether this is a joint appointment
	public boolean isJoint(){
		return isjoint;
	}

	// setter of the isJoint
	public void setJoint(boolean isjoint){
		this.isjoint = isjoint;
	}



}
