package example;

import java.util.ArrayList;

public class ChargerSensor extends Thread  {
	@Override
	public void run() {
		ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
		 Sensor sensor=new Sensor();
		 for (int i=0; i < 100 ; i++) {
			 try {
				if(sensor.sensors.size()>5) {
					sensorList=calculateLatency(sensor.sensors); // tính độ trễ
					 System.out.println(sensorList.size());
					sensor.sensors.clear();
					calculateWaitingTime(sensorList);
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 10s
		 }
	}
	// tính thời gian tối đa mà sensor tồn tại
	public ArrayList<Sensor> calculateLatency(ArrayList<Sensor> sensors) {
		 for (int i=0; i < sensors.size() ; i++) {
			 long calculationStartTimeLatency = System.currentTimeMillis( );
			 long energyConsumptionTime= (long) (sensors.get(i).getEnergy() / sensors.get(i).getP());
			 long time=(long) ((sensors.get(i).getdateRequest()-calculationStartTimeLatency)*0.001);
			 long latency= energyConsumptionTime + time;
			 System.out.println(" thread 2 time:"+sensors.get(i).getdateRequest()+" - "+calculationStartTimeLatency+ "=: "+time+"s");
			 System.out.println(" thread 2 dateLatency:"+sensors.get(i).getEnergy()+" / "+sensors.get(i).getP()+ " = "+latency +"với: "+time+"s");
			 if(latency<0) {
				 sensors.remove(i);
			 }
		 }
		 return sensors;
		 
	}
	// tính thời gian chờ ngắn nhất
	public void calculateWaitingTime(ArrayList<Sensor> sensorList) {
		 System.out.println(sensorList.size());
		 for (int i=0; i < sensorList.size() ; i++) {
			
		 }
		
		 
	}
}
