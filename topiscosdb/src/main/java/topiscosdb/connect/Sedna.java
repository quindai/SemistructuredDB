package topiscosdb.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.xquery.XQDataSource;

import ru.ispras.sedna.driver.DatabaseManager;
import ru.ispras.sedna.driver.DriverException;
import ru.ispras.sedna.driver.SednaConnection;
import ru.ispras.sedna.driver.SednaSerializedResult;
import ru.ispras.sedna.driver.SednaStatement;

//https://www.sedna.org/progguide/ProgGuidesu1.html
/**
 * @see XQJ example
 * @author quindai
 * before run this example start db
 * on terminal:
 * > se_gov
 * > se_cdb topicosbd
 * > se_sm topicosbd
 */
public class Sedna {

	SednaConnection conn;;
	private SednaStatement st;
	
	public Sedna() {
		XQDataSource ds;
		
	}
	
	private static SednaConnection getMConnection() throws DriverException {
		return DatabaseManager.getConnection("localhost", "topicosbd", "SYSTEM", "MANAGER");
	}
	
	public Sedna(boolean b) {
		try {
			SednaConnection conn = DatabaseManager.getConnection("localhost", "topicosbd", "SYSTEM", "MANAGER");
			
			//inicia transacao
			conn.begin();
			
			//cria statement
			//SednaStatement 
			st = conn.createStatement();
			
			//carrega xml no bd
			System.out.println("Carregando dados...");
			boolean res;
            
			res = st.execute("LOAD 'src/main/resources/region.xml' 'region'");
			
			if(!res) {
					System.out.println("Document 'region.xml' carregado com sucesso.");
					System.out.println("==================================================\n");
			}
			
			System.out.println("Executando uma busca");
			res = st.execute("doc('region')/*/*/(id_region)");
			
			//se re eh true statement nao eh update, podemos usar objeto serializado
			if(res) printQueryResults(st);
			
			//remove documento se existir
			System.out.println("Removendo documento ...");
            res = st.execute("DROP DOCUMENT 'region'");
            if(!res) {
            	System.out.println("Documento removido com sucesso!");
            } else
            	System.out.println("Nada pra remover, continuando execucao!");
            
		    conn.commit(); 
		} catch (DriverException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void close(){
		if(conn != null)
			try {
				conn.close();
			} catch (DriverException e) {
				e.printStackTrace();
			}
	}
	
	
	
	public static ArrayList<String> getResults() throws DriverException {
		SednaConnection conn = getMConnection();
		conn.begin();
		SednaStatement st = conn.createStatement();
		
		//carrega xml no bd
		System.out.println("Carregando dados...");
		boolean res; 
		res = st.execute("LOAD 'src/main/resources/region.xml' 'region'");
		if(!res) {
			System.out.println("Documento 'region.xml' carregado com sucesso.");
			System.out.println("================================================\n");
		}
		
		//get query region
		//res = st.execute("doc('region')/regions/*[id_region='sam']");
		
		res = st.execute("doc('region')/regions/*/id_region/text()");
		
		SednaSerializedResult pr = st.getSerializedResult();
		 ArrayList<String> itens = new ArrayList<>();
		 String item;
		 while ((item = pr.next()) != null) {
			 itens.add(item.trim());
		 }
		 
		//remove documento se existir
		 System.out.println("Liberando a memoria...");
         res = st.execute("DROP DOCUMENT 'region'");
         if(!res) {
         	System.out.println("Documento resgatado com sucesso!");
         } else
         	System.out.println("Nada para resgatar, continuando execucao!");
         
		 conn.commit();
		 conn.close();
		 return itens;
	}
	
	private void printQueryResults(SednaStatement st)
            throws DriverException {

        int count = 1;
        String item;
        System.out.println("Result:");
        SednaSerializedResult pr = st.getSerializedResult();
        while ((item = pr.next()) != null) {
            System.out.println(count + " item: " + item);
            System.out.println("==================================================");
            count++;
        }
    }
	public static void main(String[] args) {
		new Sedna(true);
	}
}
