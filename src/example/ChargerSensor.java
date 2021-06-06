package example;

import java.util.ArrayList;

public class ChargerSensor extends Thread {
	int count=0;
	int fail=0;
	ArrayList<Integer> index=new ArrayList<Integer>();

	@Override
	public void run() {
		Sensor sensor = new Sensor();
		boolean yes=false;
		// MC chờ trong khoảng thời gian 10000s
		for (int i = 0; i < 200; i++) {
			try {
				if (sensor.sensors.size() > 10) {
					yes=true;
				StartMC(sensor.sensors);
				}
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 10s
		}
		try {
			if(yes=false)
			StartMC(sensor.sensors);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("số sensor đã sạc: "+count);
		System.out.println("số sensor đã chết: "+fail);
	
	}
	public void StartMC(ArrayList<Sensor> sensors) throws InterruptedException {
		Sensor nextChargeSensor = null;
	
		MC mc = new MC();
		
		// tính khoảng cách MC
	
		while (sensors.size() > 0) {
			float min = (float) 568705591.00;
			// tính độ trễ
			ArrayList<Sensor> sensorList = calculateLatency(sensors);
			// xóa sensor có l<0 trong s
			int length=index.size();
			
			for(int i=0;i<length;i++) {
				int m=findSensor(sensors,index.get(i));
				sensors.remove(m);
				System.out.println("đã xóa: "+index.get(i));
			}
			index.clear();
			// z là tập các sensor được sạc
			ArrayList<Sensor> sensorsInZ = calculateWaitingTime(sensorList); 
			int index=0;// xác đinh vị trí sensor kế tiếp
			if (sensorsInZ.size() != 0) {
				for (int k = 0; k < sensorsInZ.size(); k++) {
					float distanceMC = calculateCoordinates(mc.getoX(), sensorsInZ.get(k).getoX()); 
					float distanceSensorK = calculateCoordinates(mc.getoY(),
							sensorsInZ.get(k).getoY());
					 // tính khoảng cách từ MC đến sensor k
					float calculateDistance = calculateDistance(distanceSensorK, distanceMC);
					// tính thời gian từ MC đến sensor k
					float timeMCtoSensor = (float) (calculateDistance / 5); 
					float chargeTime = chargeTime(timeMCtoSensor, sensorsInZ.get(k));
					if (min > chargeTime) {
						min = chargeTime;
						nextChargeSensor = sensorsInZ.get(k);
						index=k;
					}
				}
				// sạc xong thì xóa sensor đó đi
				if(calcultEngcyRemainingMC(nextChargeSensor)==1) {
					sensorsInZ.remove(index); // xóa sensor trong S
					int n=findSensor(sensors,nextChargeSensor.getID());
					sensors.remove(n); // xóa sensor trong S
				}
				else {
					float e=108000;
					mc.setEnergy(e);
					mc.setoX((float) 250.0);
					mc.setoY((float) 250.0);
				}
				System.out.println("----------------------------");
			}
			else {
				System.out.println("nguoc lai");
			}
	}
	}
	// tìm vi trí sensor trong S
	public int findSensor(ArrayList<Sensor> sensors, int id) {
		System.out.println("sensor ID cần xóa trong S: "+id);
		System.out.println("Tập S còn : "+ sensors.size());
	
		for (int k = 0; k < sensors.size(); k++) {
			if(sensors.get(k).getID()==id) {
				return k;
		}
		}
		return 1000;
	}
	// tính thời gian tối đa mà sensor tồn tại
	public ArrayList<Sensor> calculateLatency(ArrayList<Sensor> sensors) {
		ArrayList<Sensor> result = new ArrayList<Sensor>();
		for (int i = 0; i < sensors.size(); i++) {
			result.add(sensors.get(i));
		}
		for (int i = 0; i < result.size(); i++) {
			long latency = latency(result.get(i)); // tính độ trễ của sensor i
			if (latency < 0) {
				
				fail+=1;
				index.add(result.get(i).getID());
				System.out.println("độ trễ sensor "+result.get(i).getID()+":"+latency);
				result.remove(i);
			}
		}
		return result;
	}

	// tính năng lượng của MC
	public int calcultEngcyRemainingMC(Sensor sensor) throws InterruptedException {
		MC mc = new MC();
		// tính năng lượng còn lại của MC
		float distanceMC = calculateCoordinates(mc.getoX(), sensor.getoX());
		float distanceSensorI = calculateCoordinates(sensor.getoY(), mc.getoY());
		float calculateDistance = calculateDistance(distanceSensorI, distanceMC); // tính khoảng cách từ MC đến sensor i
		float timeMCtoSensor = (float) (calculateDistance / 5); // tính thời gian từ MC đến sensor i
		// năng lương cần sạc cho sensor
		float engcyNeedChargeSensor = 10800 - (sensor.getEnergy()-Math.abs(time(sensor))*sensor.getP() ) ; // tính năng lượng mà sensor cần sạc
		System.out.println("NL sensor cần sạc: "+engcyNeedChargeSensor);
		float remainEngcyMC = mc.getEnergy() - 5 * calculateDistance - engcyNeedChargeSensor;
		float distanceFromItoBS = calculateDistance(distanceSensorI, 0);
		float engcyMCFromItoBS=distanceFromItoBS*5;
		System.out.println("engcyMCFromItoBS: "+engcyMCFromItoBS);
		if(remainEngcyMC>engcyMCFromItoBS) {
			 count+=chargeSensor(sensor,mc,engcyNeedChargeSensor);
				return 1;
		}
		else {
			return 0;
		}
	}
	// sạc sensor
	public int chargeSensor(Sensor sensor,MC mc,float engcyNeedChargeSensor) throws InterruptedException {
		// xét vị trí cho MC
		mc.setoX(sensor.getoX());
		mc.setoY(sensor.getoY());
		mc.setEnergy(mc.getEnergy() - engcyNeedChargeSensor);
		float timeChargeSensor=engcyNeedChargeSensor/5;
		System.out.println("time sạc: "+timeChargeSensor);
		for(int i=0;i<timeChargeSensor;i++) {
			Thread.sleep(1);
		}
		return 1;
	}
	// tính thời gian từ lúc sensor gửi lên đến lúc bắt đầu tính độ trễ
	public long time(Sensor sensor) {
		long calculationStartTimeLatency = System.currentTimeMillis(); // thời gian tính độ trễ - giả sử 1000ms bằng 1000s thực tế
		long time = (long) (sensor.getdateRequest() - calculationStartTimeLatency); 
		return time;
	}
	
	// tính độ trễ
	public long latency(Sensor sensor) {
		long energyConsumptionTime = (long) ((sensor.getEnergy() -540 ) / sensor.getP()) +time(sensor);
		return energyConsumptionTime;
	}

	// tính thời gian chờ ngắn nhất
	public ArrayList<Sensor> calculateWaitingTime(ArrayList<Sensor> sensorList) {
		ArrayList<Sensor> z = new ArrayList<Sensor>();
		MC mc = new MC();
		float latency, waitingTime, timeChargeI;
		boolean ok = false;
		// tính tọa độ MC
		for (int i = 0; i < sensorList.size(); i++) {
			float distanceMC = calculateCoordinates(mc.getoX(), sensorList.get(i).getoX());
			float distanceSensorI = calculateCoordinates(mc.getoY(), sensorList.get(i).getoY());
			float calculateDistance = calculateDistance(distanceSensorI, distanceMC); // tính khoảng cách từ MC đến sensor i
			float timeMCtoSensoer = (float) (calculateDistance / 5); // tính thời gian từ MC đến sensor i
			for (int j = 0; j < sensorList.size(); j++) {
				// tính khoảng cách từ sensor i đến sensor j
				float distanceSenserI = calculateCoordinates(sensorList.get(i).getoX(), sensorList.get(j).getoX());
				float distanceSensorJ = calculateCoordinates(sensorList.get(i).getoY(), sensorList.get(j).getoY());
				float distanceSensorItoSensoerJ = calculateDistance(distanceSenserI, distanceSensorJ); // tính khoảng cách từ MC đến sensor i
				float timeSensorItoSensoerJ = (float) (distanceSensorItoSensoerJ / 5);// tính thời gian từ i đến sensor j
				timeChargeI = chargeTime(timeMCtoSensoer, sensorList.get(i));
				waitingTime = timeMCtoSensoer + timeChargeI + timeSensorItoSensoerJ;
				latency = latency(sensorList.get(j)); // tính độ trễ sensor j
				if (waitingTime < latency) {
					ok = true;
				}
			}
			if (ok) {
				z.add(sensorList.get(i));
			}
		}
		return z;
	}

	// tính tọa độ
	public float calculateCoordinates(float x1, float x2) {
		float distance = (x1 - x2) * (x1 - x2);
		return distance;
	}

	// tính khoảng cách từ X đến Y
	public float calculateDistance(float distanceX, float distanceY) {
		// tính khoảng cách từ MC đến sensor i
		float distanceSensorXtoSensoerY = (float) Math.sqrt(Math.abs(distanceX - distanceY)); 
		return distanceSensorXtoSensoerY;
	}

	// tính thời gian sạc
	public float chargeTime(float timeMCtoSensoer, Sensor sensor) {
		long startWaitingTime = System.currentTimeMillis();// thời điểm bắt đầu sạc
		// tính thời gian sạc cho sensor i
		float eMax = 10800;
		float timeChargeI = (float) (eMax - sensor.getEnergy()
				- (sensor.getP() * (startWaitingTime - sensor.getdateRequest() + timeMCtoSensoer)) / 5); // tốc độ sạc 5 J/s
		return timeChargeI;

	}

}
