package example;

import java.util.ArrayList;

public class ChargerSensor extends Thread  {
	@Override
	public void run() {
		//ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		// Sensor sensor=new Sensor();
		 for (int i=0; i < 10 ; i++) {
			 try {
				Thread.sleep(1000);
				 System.out.println("thread 2");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 10s
		 }
		
	}
}
