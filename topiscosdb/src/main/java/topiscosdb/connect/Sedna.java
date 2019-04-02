package topiscosdb.connect;

import ru.ispras.sedna.driver.SednaConnection;

public class Sedna {

	static SednaConnection conn;
	public Sedna() {
		// TODO Auto-generated constructor stub
		conn = getConnection("localhost", "testando", "SYSTEM", "MANAGER");
	}
	
	public SednaConnection getConnection(String url,
			String DBName,
			String user,
			String password){
		return null;
	}
	
	public static void close(){
		
	}
}
