package example;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Scanner;
public class Sensor extends Thread {
	private int ID;
	private Float oX;
	private Float oY;
	private Float P;
	private Float energy;
	private int sentRequest;
	private long dateRequest;
	public static  ArrayList<Sensor> sensors; // làm hơi củ chuối
	  public Sensor() {
		  sensors=new ArrayList<Sensor>();
	  }
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		this.ID = iD;
	}
	public Float getoX() {
		return oX;
	}
	public void setoX(Float oX) {
		this.oX = oX;
	}
	public Float getoY() {
		return oY;
	}
	public void setoY(Float oY) {
		this.oY = oY;
	}
	public Float getP() {
		return P;
	}
	public void setP(Float p) {
		this.P = p;
	}
	public Float getEnergy() {
		return energy;
	}
	public void setEnergy(Float energy) {
		this.energy = energy;
	}
	public int getSentRequest() {
		return sentRequest;
	}
	public void setSentRequest(int dateRequest) {
		this.sentRequest = dateRequest;
	}
	
	public long getdateRequest() {
		return dateRequest;
	}
	public void setdateRequest(long dateRequest) {
		this.dateRequest = dateRequest;
	}

	@Override
	public void run() {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File("data.txt"));
			sensors=readFileDataIntoSenser(scanner);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
    	for(;i<100;i++) {
    		    try {
    		    	 Thread.sleep(1000); // 1s
    		    	calculateRemainingEnergyOfSensorNode(sensors);
    		    	
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
    	}
    	
	}
	
// get data of Sensers from file data
 public static ArrayList<Sensor> readFileDataIntoSenser(Scanner scanner) {
 	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
 	int i=1;
 	 String line = scanner.nextLine();
 	 while (scanner.hasNextLine()) {
 		        Sensor sensor=new Sensor();
 		         line = scanner.nextLine();
		 	    String str[] = line.split(" ");
		 	    sensor.setID(i);
		 	  	sensor.setoX(Float.parseFloat( str[0]));
		 	    sensor.setoY(Float.parseFloat( str[1]));
		 	    sensor.setP(Float.parseFloat( str[2]));
		 	    sensor.setEnergy(Float.parseFloat( str[3]));
		 	    sensor.setSentRequest(0);
		 	    sensors.add(sensor); 
		 	    i++;
		 }
		 scanner.close();
      return sensors;
 }
 
 // tính năng lượng còn lại của sensor
 public static void calculateRemainingEnergyOfSensorNode(ArrayList<Sensor> sensors) {
 	 LocalTime myObj = LocalTime.now();
 	int i=0, length=sensors.size(); 
 	 System.out.println("thread 1: "+ myObj);
 	Float ergyConsumption,remainingEnergy; // khởi tao năng lương tiêu thụ , năng lương còn lại
   for (; i < length ; i++) {
 	  if(sensors.get(i).getSentRequest()==0) // kiểm tra xem sensor đã gửi yêu cầu hay chưa, nếu chưa gửi yc thì tính năng lượng
 	  {
 	 ergyConsumption =sensors.get(i).getP() * 10000; // năng lượng tiêu thụ trong 10000s
 	 remainingEnergy=sensors.get(i).getEnergy() - ergyConsumption;  // năng lương còn lại
 	 sensors.get(i).setEnergy(remainingEnergy); // lưu năng lượng còn lại
 	 if(remainingEnergy < 4320) { // nếu năng lượng nhỏ hơn ngưỡng
 		 sensors.get(i).setSentRequest(1); // đánh dấu sensor đã gửi yêu cầu
 		 long dateRequest = System.currentTimeMillis( );
 		 sensors.get(i).setdateRequest(dateRequest);// thời điểm sensor gửi yc sạc
 		 requiresRecharging(sensors.get(i));   
 	 }
   }
   }
 }
 // sensor gửi yêu cầu sạc đến MC
 public static  void  requiresRecharging(Sensor sensor) {
	 sensors.add(sensor);  // add  into list sensor waiting for charging
	 System.out.println("thread 1: "+ sensor.getEnergy() + "ID:"+sensor.getID());

 }
}
