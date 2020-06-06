package clientama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Amateur {

	public static void main(String[] args) {
		Socket s = null;
		try{
			s = new Socket("localhost",4000);
			Scanner clavier = new Scanner(System.in);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter socketOut = new PrintWriter(s.getOutputStream(), true);
			String msg;
			
			while(true) {
			
				System.out.println(socketIn.readLine());
				System.out.println(socketIn.readLine());
				System.out.println(socketIn.readLine());
				System.out.println(socketIn.readLine());
				
				socketOut.println(clavier.next());

				msg = socketIn.readLine();
				System.out.println(msg);
				if(msg.contentEquals("fin de transmission, fermeture gestionnaire")) {
					s.close();
					return;
				}
				socketOut.println(clavier.next());
				msg = socketIn.readLine();
				System.out.println(msg);
				if(!msg.equals("Classe non disponible ou absente")){
					socketOut.println(clavier.next());//interagit avec le service
					System.out.println(socketIn.readLine());
					socketOut.println(clavier.next()); //ferme le service
					System.out.println(socketIn.readLine()); // QUE FAIRE QUAND LE SERVICE INVERSION FERME LA SOCKET CAR ERREUR
				}
				
			}
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
