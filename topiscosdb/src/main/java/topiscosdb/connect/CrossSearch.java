package topiscosdb.connect;

import java.util.ArrayList;
import java.util.Scanner;

import ru.ispras.sedna.driver.DriverException;

public class CrossSearch {

	ArrayList<String> sedna, restdb;
	
	public CrossSearch() {
		Scanner sc = new Scanner(System.in);
		int op;
		try {
			
			sedna = Sedna.getResults();
		} catch (DriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("==Selecione uma regiao==>%4s","");
		op = sc.nextInt();
		System.out.println(sedna);
	}
	
	public static void main(String[] args) {
		new CrossSearch();
	}
}
