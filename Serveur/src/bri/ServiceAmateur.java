package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.registry.Registry;

import serveur.ServeurBRi;

public class ServiceAmateur implements ServiceBRi{
	private final Socket socket;
	
	public ServiceAmateur(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void runBRi() {
		System.out.println("Service Programmeur");
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			//variables
			String classeName;
			String fileDirURL;
			URLClassLoader urlcl;
			
			while(true) {
				System.out.println("Service Amateur");
				socketOut.println("Bienvenue dans votre gestionnaire dynamique d'amateur, voici les services que vous pouvez utiliser :");
				socketOut.println(ServiceRegistry.toStringue()); 
				
				socketOut.println("1 : Choisir un service"); 
				socketOut.println("2 : Fermer le gestionnaire");
				
				switch (socketIn.readLine()){
				case "1" : 
					socketOut.println("écrire le service que vous souhaitez utiliser avec la forme package.classe"); 
					String msg = socketIn.readLine();
					Class<? extends ServiceBRi> classe = ServiceRegistry.getServiceClass(msg);
					if (classe == null) {
						socketOut.println("Classe non disponible ou absente"); 
					}
					else {
						try {
							try {
								Constructor <?> constr = classe.getDeclaredConstructor(java.net.Socket.class);
								socketOut.println("Lancement du service"); 
								constr.newInstance(this.socket);
							} catch (NoSuchMethodException | SecurityException |IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case "2" : //fermer le gestionnaire
					socketOut.println("fin de transmission, fermeture gestionnaire");
					System.out.println("Déconnexion de l'amateur");
					socket.close();
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
