package bri;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.ValidationException;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les amateurs et les programmeurs,

	// Les programmeurs ajoutent des services (addService) 
	// Les amateurs demandent des services (getServiceClass)

	// Un Vector pour la gestion (partielle) de laconcurrence est pratique
	static {
		servicesClasses = new Vector<Class<? extends ServiceBRi>>();
	}
	private static List<Class<? extends ServiceBRi>> servicesClasses;

	public static void addService(Class<? extends ServiceBRi> runnableClass) throws ValidationException {
		
	}

	// une m�thode de validation renvoie void et l�ve une exception si non validation
	// surtout pas de retour boolean !
	private static void validation(Class<? extends ServiceBRi> classe) throws ValidationException {
	}

	public static Class<? extends ServiceBRi> getServiceClass(int numService) {
			return null;
	}
	
// toStringue liste les activit�s pr�sentes
	public static String toStringue() {
		return null;
	}
}