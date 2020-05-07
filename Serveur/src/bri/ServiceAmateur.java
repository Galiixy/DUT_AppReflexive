package bri;

import java.net.Socket;

public class ServiceAmateur implements ServiceBRi{
	private final Socket socket;
	
	public ServiceAmateur(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void runBRi() {
		// TODO Auto-generated method stub
		
	}
	
}
