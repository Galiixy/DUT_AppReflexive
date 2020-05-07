package clientprog;

import java.io.IOException;
import java.net.Socket;

public class programmeur {

	public static void main(String[] args) {
		Socket s = null;
		try{
			s = new Socket("localhost",3000);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally { //fermeture de la socket du serveur
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
