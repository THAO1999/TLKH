package example;

import java.util.ArrayList;
import java.util.Scanner;

public class Sensor {
	private int ID;
	private Float oX;
	private Float oY;
	private Float P;
	private Float energy;
	private int sentRequest;
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
	public void setSentRequest(int iD) {
		this.sentRequest = iD;
	}

// get data of Sensers from file data
 public static ArrayList<Sensor> readFileDataIntoSenser(Scanner scanner) {
 	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
 	int i=1;
 	 while (scanner.hasNextLine()) {
 		        Sensor sensor=new Sensor();
 		        String line = scanner.nextLine();
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
}
