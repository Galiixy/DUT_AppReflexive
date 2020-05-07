import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.ServerSocket;
import java.net.Socket;

import bri.ServiceAmateur;
import bri.ServiceBRi;
import bri.ServiceProgrammeur;

public class ServeurBRi implements Runnable {

	private Class<? extends ServiceBRi> serviceClass;
	private Constructor<? extends ServiceBRi> constr;
	private ServerSocket listen_socket;
	private Thread thread;
	
	public ServeurBRi(int port, Class<? extends ServiceBRi> serviceClass) {
		try {
			this.listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		this.serviceClass = serviceClass;
		try {
			this.constr = serviceClass.getDeclaredConstructor(java.net.Socket.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if (!Modifier.isPublic(constr.getModifiers()))
			try {
				throw new NoSuchMethodException("Le constructeur n'est pas public");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
	}

	public void start(){
		this.thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Serveur lancé");
		
		try {
			while(true) {
				Socket client_socket = listen_socket.accept(); //on prend le client
				//ServiceBRi service = serviceClass.newInstance();
			}
		}
		catch (IOException e) { }
		finally {
			this.close();
		}
		
		System.err.println("Serveur arreté ");
	}
	
	public void close() {
		try {this.listen_socket.close();} catch (IOException e) {}
	}

}
