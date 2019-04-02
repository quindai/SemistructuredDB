package topiscosdb.connect;

import java.io.BufferedReader;
import java.util.ArrayList;

import ru.ispras.sedna.driver.DriverException;

public class CrossSearch {

	ArrayList<String> sedna, restdb;
	
	public CrossSearch() {
		try {
			sedna = Sedna.getResults();
		} catch (DriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(sedna);
		
	}
	
	public static void main(String[] args) {
		new CrossSearch();
	}
}
