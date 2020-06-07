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
			
			//tant que l'amateur ne ferme pas le gestionnaire 
			while(true) {
				//interaction avec l'amateur
				System.out.println(socketIn.readLine());
				System.out.println(socketIn.readLine());
				System.out.println(socketIn.readLine());
				System.out.println(socketIn.readLine());
				
				socketOut.println(clavier.next());

				msg = socketIn.readLine();
				System.out.println(msg);
				if(msg.contentEquals("fin de transmission, fermeture gestionnaire")) {
					clavier.close();
					s.close();
					return;
				}
				socketOut.println(clavier.next());
				msg = socketIn.readLine();
				System.out.println(msg);
				//lorsque la classe est disponible
				if(!msg.equals("Classe non disponible ou absente")){
					//interaction avec le service possible
					while(true) {
						socketOut.println(clavier.next());//interagit avec le service
						String reception = socketIn.readLine();
						//l'interaction se termine lorsque l'utilisateur écrit 'close'
						if(reception.contentEquals("close")) {
							break;
						}
						System.out.println(reception);
					}
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
