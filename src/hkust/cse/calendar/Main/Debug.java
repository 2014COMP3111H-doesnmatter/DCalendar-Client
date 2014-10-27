package hkust.cse.calendar.Main;

import hkust.cse.calendar.api.welcome.GetActiveUserAPI;
import hkust.cse.calendar.api.welcome.LoginAPI;
import hkust.cse.calendar.utils.GenListener;
import hkust.cse.calendar.utils.network.APIHandler;
import hkust.cse.calendar.utils.network.APIRequestEvent;

/**
 * 
 * @name Debug
 * A class only for debug purpose
 */
public class Debug {
	static public void main() {
		try {
			LoginAPI api1 = new LoginAPI("cirah", "122333");
			final GetActiveUserAPI api2 = new GetActiveUserAPI();
			api1.addDoneListener(new GenListener<APIRequestEvent>() {
				@Override
				public void fireEvent(APIRequestEvent e) {
					System.out.println(e.getJSON().toString());
					Thread thrd2 = new Thread(new APIHandler(api2));
					thrd2.start();
				}
			});
			api2.addDoneListener(new GenListener<APIRequestEvent>() {
				@Override
				public void fireEvent(APIRequestEvent e) {
					System.out.println(e.getJSON().toString());
				}
			});
			Thread thrd1 = new Thread(new APIHandler(api1));
			thrd1.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}