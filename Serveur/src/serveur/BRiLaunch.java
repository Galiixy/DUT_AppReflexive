package serveur;
import java.io.IOException;

public class BRiLaunch {

    private static final int PORT_PROG = 3000;
    private static final int PORT_AMA = 4000;

    public static void main(String[] args) throws IOException {

        System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
        System.out.println("Les programmeurs se connectent au port PORT_PROGRAMMEUR");
        System.out.println("Les amateurs se connectent au port PORT_AMATEUR");

        new Thread(new ServeurBRi(PORT_PROG, bri.ServiceProgrammeur.class)).start();
        new Thread(new ServeurBRi(PORT_AMA, bri.ServiceAmateur.class)).start();
    }
}