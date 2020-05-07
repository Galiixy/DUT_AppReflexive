package bri;

import java.net.Socket;

public class ServiceProgrammeur implements ServiceBRi {
	private final Socket socket;
	
	public ServiceProgrammeur(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void runBRi() {
		// TODO Auto-generated method stub
		
	}
}
