package serveur;
public class BDD_Programmeur {
	private String login;
	private String password;
	private String urlFTP;
	
	public BDD_Programmeur(String login, String password, String urlFTP) {
		super();
		this.login = login;
		this.password = password;
		this.urlFTP = urlFTP;
	}
	
	public boolean connect(String login, String password) {
		if(this.login.equals(login) && this.password.equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	public void setUrlFTP(String urlFTP) {
		this.urlFTP = urlFTP;
	}

	public String getUrlFTP() {
		return urlFTP;
	}
}
