package topiscosdb.connect;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import ru.ispras.sedna.driver.DriverException;

public class CrossSearch {

	ArrayList<String> sedna, restdb;
	
	public CrossSearch() {
		Scanner sc = new Scanner(System.in);
		int op;
		try {
			sedna = Sedna.getResults();
			
			IntStream.range(0, sedna.size())
					.forEach(i ->
						System.out.printf("%d=%s\t", i,sedna.get(i))
					);

			System.out.printf("\n==Selecione uma regiao==>%2s","");
			op = sc.nextInt();
			System.out.println(sedna.get(op));
			RestDB.getCountries(sedna.get(op)+"a");
			
			
		} catch (DriverException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new CrossSearch();
	}
}
