package it.avlp.simog.util;




//stampa tutti i campi di una classe e i loro valori

import java.lang.reflect.Method;
import java.util.Collection;

@SuppressWarnings(value={"unchecked"}) 
public class ObjectIntrospector {

	private static String _tab = ""; // Gestisce le indentazioni delle props


	private static boolean isGetter(Method method){
		String methodName = method.getName();
		if(methodName.startsWith("get") && (methodName.length() > 3) && (!methodName.equals("getClass")) && (!methodName.equals("getStructure"))){
			return method.getParameterTypes().length == 0;
			 
		}
		else if( method.getReturnType().equals(boolean.class) && methodName.startsWith("is") && (methodName.length() > 3) && (!methodName.equals("getClass")) && (!methodName.equals("getStructure")))
		{
			return true;
		}
		else return false;
	}
	
	private static String getPropertyNameFromMethodName(Method method){
		String methodName = method.getName();
		String propertyName = method.getReturnType().equals(boolean.class) ? methodName.substring(2): methodName.substring(3);
		return (propertyName.length() > 1) ? propertyName.substring(0,1).toLowerCase() + propertyName.substring(1): propertyName;
		
	}
	
	/**Ritorna una stringa contenente tutti i campi di una classe
	 * @param baseClass  tipo di classe(in generale una classe astratta/interfaccia che estendono tutti i valueObject )
	 * @param obj un instanza della classe
	 * @return stringa con tutti i campi e i loro valori
	 */
	public static String propertiesInfo(Class baseClass, Object obj) {
		if (obj == null) {
			return null;
		}
		// E' la stringa che incapsula le proprieta (correttamente formattate)
		// del value object
		StringBuffer propertiesBuffer = new StringBuffer();
		Class voClass = obj.getClass();
		// E' il nome della classe value object.
		propertiesBuffer.append(_tab + "[" + voClass.getName() + "]:\r\n");
		// Indentazione
		_tab += "\t\t";
		// Vengono letti tutti i campi del vo
		Method[] methods = voClass.getMethods();
		
		// itero su tutti i metodi della classe...
		for (int i = 0; i < methods.length; i++) {
			Method currentMethod = methods[i];
			
			if(isGetter(currentMethod)){
				String propertyName = getPropertyNameFromMethodName(currentMethod);
				try {
					
					// Viene invocato il metodo
					Object returnObject = currentMethod.invoke(obj);
					if (returnObject == null) {
						// Se l'oeggetto restituito dal metodo getter non e un
						// ValueObject ne viene stampato il valore
						// attraverso il metodo toString di Object
						propertiesBuffer.append(_tab).append("[").append(currentMethod.getReturnType().getName()).append("]: ");
						propertiesBuffer.append(propertyName).append(" = ").append(returnObject).append("\r\n");
					} else if (baseClass.isInstance(returnObject)) {
						// Se l'oggetto restituito dal metodo getter e un
						// ValueObject viene richiamato ricorsivamente il metodo
						// di ispezione
						Object nestedVO = returnObject;
						propertiesBuffer.append(propertiesInfo(baseClass, nestedVO));
					} else if (returnObject.getClass().isArray()) {
						Class componentType = returnObject.getClass().getComponentType();
						if (componentType.isPrimitive()) {
							if (short.class.equals(componentType)) {
								
								short[] array = (short[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("short[" + j + "]: " + array[j]).append("\n");
								}
							} else if (byte.class.equals(componentType)) {
								byte[] array = (byte[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("byte[" + j + "]: " + array[j]).append("\n");
								}
							} else if (int.class.equals(componentType)) {
								int[] array = (int[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("int[" + j + "]: " + array[j]).append("\n");
								}
							} else if (long.class.equals(componentType)) {
								long[] array = (long[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("long[" + j + "]: " + array[j]).append("\n");
								}
							} else if (float.class.equals(componentType)) {
								float[] array = (float[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("float[" + j + "]: " + array[j]).append("\n");
								}
							} else if (double.class.equals(componentType)) {
								double[] array = (double[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("double[" + j + "]: " + array[j]).append("\n");
								}
							} else if (boolean.class.equals(componentType)) {
								boolean[] array = (boolean[]) returnObject;
								for (int j = 0; j < array.length; j++) {
									propertiesBuffer.append(_tab).append("\t").append("boolean[" + j + "]: " + array[j]).append("\n");
								}
							}

						} else {
							Object[] array = (Object[]) returnObject;
							for (int ind = 0; ind < array.length; ind++) {
								Object item = array[ind];
								if (baseClass.isInstance(item)) {
									propertiesBuffer.append(propertiesInfo(baseClass, item));
								}
							}

						}

					} else if (returnObject instanceof Collection) {
						Collection col = (Collection) returnObject;
						for(Object item: col){
							if (baseClass.isInstance(item)) {
								propertiesBuffer.append(propertiesInfo(baseClass, item));
							}
						}
					} else {
						//l'ogetto non estende/implementa baseClass, e probabilmente e un ogetto semplice...lo stampo
						propertiesBuffer.append(_tab).append("[").append(currentMethod.getReturnType().getName()).append("]: ");
						propertiesBuffer.append(propertyName).append(" = ").append(returnObject).append("\r\n");
					}
				} catch (Exception e) {
					
					propertiesBuffer.append(_tab).append("[").append(currentMethod.getReturnType().getName()).append("]: ");
					propertiesBuffer.append(propertyName).append(" = ").append(e.getMessage()).append("\r\n");
				}
			}
		}

		_tab = _tab.substring(0, _tab.length() - 2);
		return propertiesBuffer.toString();
	}

}
