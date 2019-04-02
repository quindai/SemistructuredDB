package topiscosdb.connect;

import ru.ispras.sedna.driver.DatabaseManager;
import ru.ispras.sedna.driver.DriverException;
import ru.ispras.sedna.driver.SednaConnection;
import ru.ispras.sedna.driver.SednaSerializedResult;
import ru.ispras.sedna.driver.SednaStatement;

//https://www.sedna.org/progguide/ProgGuidesu1.html
/**
 * 
 * @author quindai
 * before run this example start db
 * on terminal:
 * > se_gov
 * > se_cdb testando
 * > se_sm testando
 */
public class Sedna {

	static SednaConnection conn = null;
	public Sedna() {
		try {
			conn = DatabaseManager.getConnection("localhost", "testando", "SYSTEM", "MANAGER");
			
			//inicia transacao
			conn.begin();
			
			//cria statement
			SednaStatement st = conn.createStatement();
			
			//carrega xml no bd
			System.out.println("Carregando dados...");
			boolean res;
			
			//remove documento se existir
			System.out.println("Removendo documento ...");
            res = st.execute("DROP DOCUMENT 'region'");
            if(!res) {
            	System.out.println("Documento removido com sucesso!");
            } else
            	System.out.println("Nada pra remover, continuando execucao!");
            
			res = st.execute("LOAD 'src/main/resources/region.xml' 'region'");
			
			if(!res) {
					System.out.println("Document 'region.xml' carregado com sucesso.");
					System.out.println("==================================================\n");
			}
			
			System.out.println("Executando uma busca");
			res = st.execute("doc('region')/*/*");
			
			//se re eh true statement nao eh update, podemos usar objeto serializado
			if(res) printQueryResults(st);
			
		    conn.commit(); 
		} catch (DriverException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public SednaConnection getConnection() {
		return conn;
	}
	
	public static void close(){
		if(conn != null)
			try {
				conn.close();
			} catch (DriverException e) {
				e.printStackTrace();
			}
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
		new Sedna();
	}
}
