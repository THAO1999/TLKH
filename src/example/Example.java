package example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Example {

    public static void main(String[] args) throws FileNotFoundException {
    	ArrayList<Sensor> sensors; 
    	MC mc;
    	// read file by Scaner
    	Scanner scanner = new Scanner(new File("data.txt"));
    	mc= readFileDataIntoMC(scanner); 
    	sensors=readFileDataIntoSenser(scanner);
    	 
    }
 // get data of Sensers from file data
    public static ArrayList<Sensor> readFileDataIntoSenser(Scanner scanner) {
    	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    	 String line = scanner.nextLine();
    	 while (scanner.hasNextLine()) {
    		  Sensor sensor=new Sensor();
		 	    line = scanner.nextLine();
		 	    String str[] = line.split(" ");
		 	  	sensor.setoX(Float.parseFloat( str[0]));
		 	    sensor.setoY(Float.parseFloat( str[1]));
		 	    sensor.setP(Float.parseFloat( str[2]));
		 	    sensor.setEnergy(Float.parseFloat( str[3]));
		 	    sensors.add(sensor); 
		 }
//       for (int i = 0; i < sensors.size(); i++) {
//     	 System.out.println(sensors.get(i).getoX());
//       }
		 scanner.close();
         return sensors;
    }
    
    // get data of MC from file data
    public static MC readFileDataIntoMC(Scanner scanner) {
    			MC mc=new MC();
    			String line = scanner.nextLine();
		 	  	String str[] = line.split(" ");
		 	  	mc.setoX(Float.parseFloat( str[0]));
		 	  	mc.setoY(Float.parseFloat( str[1]));

         return mc;
    }
}