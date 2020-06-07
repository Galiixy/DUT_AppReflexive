package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.bind.ValidationException;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les amateurs et les programmeurs,

	private static HashMap<String,Class<? extends ServiceBRi>> servicesClasses;
	
	// Un Vector pour la gestion (partielle) de laconcurrence est pratique
	static {
		servicesClasses = new HashMap<String,Class<? extends ServiceBRi>>();
	}
	
	// Les programmeurs ajoutent des services (addService) 
	// Les amateurs demandent des services (getServiceClass)
	public static void addService(Class<? extends ServiceBRi> runnableClass) throws ValidationException {
		/*
		 * impl�menter l'interface bri.ServiceBRi
			ne pas �tre abstract
			�tre publique
			avoir un constructeur public (Socket) sans exception
			avoir un attribut Socket private final
			avoir une m�thode public static String toStringue() sans exception
		 * 
		 * 
		 */
		try {
			validation(runnableClass); 
		}
		catch(ValidationException e) {
			 e.printStackTrace();
			 throw new ValidationException("On ne peut l'impl�menter! ");
		}
		
		//ajout dans le registry du service
		System.out.println("classe ajout�e :" +runnableClass.getCanonicalName());
		
		synchronized(servicesClasses) {
			servicesClasses.put(runnableClass.getCanonicalName(), runnableClass);
		}
		
	}

	// une m�thode de validation renvoie void et l�ve une exception si non validation
	// surtout pas de retour boolean !
	private static void validation(Class<? extends ServiceBRi> classe) throws ValidationException {
		//impl�menter l'interface bri.ServiceBRi		
		if (!Arrays.asList(classe.getInterfaces()).contains(bri.ServiceBRi.class)) {
			throw new ValidationException("Pas d'implementation de l'interface bri.ServiceBRi");
		}
		
		//ne pas �tre abstract
		int modifier = classe.getModifiers();	
		if (Modifier.isAbstract(modifier)) {
			throw new ValidationException("la classe ne doit pas etre abstraite");
		}
		
		//�tre publique
		if (!Modifier.isPublic(modifier)) {
			throw new ValidationException("la classe doit etre public");
		}
		
		//avoir un constructeur public (Socket) sans exception
		try {
			Constructor<? extends ServiceBRi> constr = classe.getDeclaredConstructor(java.net.Socket.class);
			modifier = constr.getModifiers();
			if (!Modifier.isPublic(modifier)) {
				throw new ValidationException("le constructeur doit etre public");
			}
			if(constr.getExceptionTypes().length!=0) {
				throw new ValidationException("le constructeur ne doit pas avoir d'exceptions");
			}
			
		} catch (NoSuchMethodException | SecurityException e) {
			throw new ValidationException("la classe doit avoir un constructeur avec socket en param�tres");
		}
		
		//avoir un attribut Socket private final
		try {
			Field field = classe.getDeclaredField("socket");
			modifier = field.getModifiers();
			if(!Modifier.isFinal(modifier)||!Modifier.isPrivate(modifier)) {
				throw new ValidationException("la classe doit avoir un attribut socket etant private et final");
			}
		} catch (NoSuchFieldException | SecurityException e) {
			throw new ValidationException("la classe doit avoir un attribut socket");
		}
		
		//avoir une m�thode public static String toStringue() sans exception
		try {
			Method method = classe.getDeclaredMethod("toStringue");
			modifier = method.getModifiers();
			
			if (!Modifier.isPublic(modifier)) {
				throw new ValidationException("la methode toStringue doit etre public");
			}
			
			if (!Modifier.isStatic(modifier)) {
				throw new ValidationException("la methode toStringue doit etre static");
			}
			
			if(method.getExceptionTypes().length!=0) {
				throw new ValidationException("la methode toStringue ne doit pas avoir d'exceptions");
			}
			
			if(!method.getReturnType().equals(String.class)) {
				throw new ValidationException("la methode toStringue doit retourner une valeur String");
			}
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new ValidationException("la classe doit avoir une methode public static string toStringue sans exception");
		}
	}

	public static Class<? extends ServiceBRi> getServiceClass(String classe) {	
		return servicesClasses.get(classe);
	}
	
	public static void updateService(Class<? extends ServiceBRi> runnableClass) throws ValidationException {
		if (servicesClasses.containsKey(runnableClass.getCanonicalName())) {
			try {
				validation(runnableClass);
			}
			catch(ValidationException e) {
				 e.printStackTrace();
				 throw new ValidationException("On ne peut l'impl�menter! ");
			}
			System.out.println("classe " + runnableClass.getCanonicalName() + " mis � jour");
			synchronized(servicesClasses) {
				servicesClasses.remove(runnableClass.getCanonicalName());
				servicesClasses.put(runnableClass.getCanonicalName(),runnableClass);
			}
		}
		else {
			throw new ValidationException("on ne peut pas mettre � jour un service qui n'existe pas !");
		}
		
	}
// toStringue liste les activit�s pr�sentes
	public static String toStringue() {
		return servicesClasses.toString();
	}
}