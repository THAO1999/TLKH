package example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class Main extends Thread {

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		ArrayList<Sensor> sensors;
		MC mc = new MC();
		// read file by Scaner
		Scanner scanner = new Scanner(new File("data.txt"));
		mc = mc.readFileDataIntoMC(scanner);
		Sensor sensor = new Sensor();
		sensor.start();// chạy luồng 1
		ChargerSensor chargerSensor = new ChargerSensor();
		chargerSensor.start();// chạy luồn 2

	}

}