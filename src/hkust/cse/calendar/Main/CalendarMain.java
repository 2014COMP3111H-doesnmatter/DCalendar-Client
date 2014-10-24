//main method, code starts here
package hkust.cse.calendar.Main;


import javax.swing.UIManager;

import hkust.cse.calendar.gui.controller.LoginController;
import hkust.cse.calendar.gui.view.PrimLoginView;


public class CalendarMain {
	public static boolean logOut = false;
	
	public static void main(String[] args) {
		Debug.main();
		while(true){
			logOut = false;
			try{
		//	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}catch(Exception e){
				
			}
			LoginController loginCtrl = new LoginController(new PrimLoginView());
			loginCtrl.start();
			while(logOut == false){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
		