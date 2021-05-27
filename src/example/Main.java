package example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime; 
import java.util.concurrent.TimeUnit;
public class Main extends Thread{

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
    	ArrayList<Sensor> sensors; 
    	MC mc=new MC();
    	Sensor sensor=new Sensor();
    	// read file by Scaner
    	Scanner scanner = new Scanner(new File("data.txt"));
    	mc= mc.readFileDataIntoMC(scanner); 
    	sensors=sensor.readFileDataIntoSenser(scanner);
    	int i=0;
    	for(;i<5;i++) {
    		    try {
                 calculateRemainingEnergyOfSensorNode(sensors);
                 Thread.sleep(10000); // 10s
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
    	}
    	
   }
    // tính năng lượng còn lại của sensor
    public static void calculateRemainingEnergyOfSensorNode(ArrayList<Sensor> sensors) {
    	 LocalTime myObj = LocalTime.now();
  	     System.out.println(myObj);
    	int i=0, length=sensors.size(); 
    	Float ergyConsumption,remainingEnergy; // khởi tao năng lương tiêu thụ , năng lương còn lại
      for (; i < length ; i++) {
    	  if(sensors.get(i).getSentRequest()==0) // kiểm tra xem sensor đã gửi yêu cầu hay chưa, nếu chưa gửi yc thì tính năng lượng
    	  {
    	 ergyConsumption =sensors.get(i).getP() * 100000; // năng lượng tiêu thụ trong 100000s
    	 remainingEnergy=sensors.get(i).getEnergy() - ergyConsumption;  // năng lương còn lại
    	 sensors.get(i).setEnergy(remainingEnergy); // lưu năng lượng còn lại
    	 if(remainingEnergy < 4320) { // nếu năng lượng nhỏ hơn ngưỡng
    		 requiresRecharging(sensors.get(i));   
    		 sensors.get(i).setSentRequest(1);
    	 }
      }
      }
    }
    // sensor gửi yêu cầu sạc đến MC
    public static void requiresRecharging(Sensor sensor) {
    	  System.out.println("sensorID: " + sensor.getID());
    }
    
}