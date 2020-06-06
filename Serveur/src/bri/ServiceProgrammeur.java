package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.bind.ValidationException;

import serveur.ServeurBRi;

public class ServiceProgrammeur implements ServiceBRi {
	private final Socket socket;
	
	public ServiceProgrammeur(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("resource")
	@Override
	public void runBRi() {
		System.out.println("Service Programmeur");
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			System.out.println("Connexion du programmeur");
			socketOut.println("Saisir votre login");
			String login = socketIn.readLine();
			socketOut.println("Saisir votre mdp");
			String password = socketIn.readLine();
			
			//connexion
			if(ServeurBRi.programmeurs.containsKey(login)) {
				if(!ServeurBRi.programmeurs.get(login).connect(login, password)) {
					socketOut.println("mot de passe errone");
					socket.close();
				}
			}
			else {
				socketOut.println("login inconnu !");
				socket.close();
			}
			
			//variables
			String classeName;
			String fileDirURL;
			URLClassLoader urlcl;
			
			while(true) {
				System.out.println("Service programmeur");
				socketOut.println("Bienvenue dans votre gestionnaire dynamique de programmeur");
				socketOut.println("1 : fournir un service"); //URL CLASS LOADER FTP
				
				socketOut.println("2 : mettre à jour un service"); 
				/*URL CLASS LOADER PEUT ETRE INDEPENDANT DUN AUTRE URL CLASS LOADER DONC FAIRE UN NEW CLASS LOADER, 
				 * LA NOUVELLE VERSION VA REMPLACER LANCIENNE puis prendre service registry mettre à jour la référence */
				
				socketOut.println("3 : déclarer un changement d'adresse serveur ftp");
				socketOut.println("4 : fermer le gestionnaire");
				
				switch (socketIn.readLine()){
				case "1" : //ajouter service
					socketOut.println("saisir l'adresse du service :");
					// lancer le serveur FTP avant 
					fileDirURL = ServeurBRi.programmeurs.get(login).getUrlFTP(); //ftp://localhost:2121/classes/
					urlcl =new URLClassLoader(new URL[] 
							{ new URL(fileDirURL)});
					
					classeName =socketIn.readLine();
					try {
						Class<?> classe = urlcl.loadClass(classeName);
						System.out.println("Classe chargée : " + classe.getClass());
						
						Class<? extends ServiceBRi> classeChargée = (Class<? extends ServiceBRi>) classe;
								//asSubclass(ServiceBRi.class);
						
						ServiceRegistry.addService(classeChargée);
						socketOut.println("classe chargée et ajouté");
					} catch (Throwable e) {
						socketOut.println("la classe ne respecte pas les conditions BRi");
						e.printStackTrace();
					}
					break;
				case "2" : //mettre à jour un service
					socketOut.println("saisir l'adresse du service mis à jour:");
					// lancer le serveur FTP avant 
					fileDirURL = ServeurBRi.programmeurs.get(login).getUrlFTP(); 
					urlcl =new URLClassLoader(new URL[] 
							{ new URL(fileDirURL)});
					
					classeName =socketIn.readLine();
					try {
						Class<?> classe = urlcl.loadClass(classeName);
						System.out.println("Classe chargée : " + classe.getClass());
						
						Class<? extends ServiceBRi> classeChargée = (Class<? extends ServiceBRi>) classe;

						ServiceRegistry.updateService(classeChargée);
						socketOut.println("classe mis à jour");
					} catch (Throwable e) {
						socketOut.println("la classe ne respecte pas les conditions BRi");
						e.printStackTrace();
					}
					break;
				case "3" : //déclarer un changement d'adresse serveur ftp
					socketOut.println("saisir l'adresse FTP :");
					ServeurBRi.programmeurs.get(login).setUrlFTP(socketIn.readLine());
					System.out.println("Changement d'adresse : "+ ServeurBRi.programmeurs.get(login).getUrlFTP());
					socketOut.println("changement d'adresse effectué");
					break;
				case "4" : //fermer le gestionnaire
					socketOut.println("fin de transmission, fermeture gestionnaire");
					System.out.println("Déconnexion du programmeur");
					socket.close();
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
