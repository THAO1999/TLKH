package example;

import java.util.Scanner;

public class MC {
	private int ID;
	private Float oX;
	private Float oY;
	private Float P;
	private Float energy;
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
    // get data of MC from file data
    public static MC readFileDataIntoMC(Scanner scanner) {
    			MC mc=new MC();
    			String line = scanner.nextLine();
		 	  	String str[] = line.split(" ");
		 	  	mc.setoX(Float.parseFloat( str[0]));
		 	  	mc.setoY(Float.parseFloat( str[1]));
		 	//   System.out.println(mc.getoX());
         return mc;
    }
}
