package it.avlp.simog.util;

import java.lang.reflect.Field;

public class FieldInspector {
	
	/********************************************************************************************
	 * Il metodo effettua il merge dei due array in ingresso
	 * 
	 * param o1 Field[]
	 * param o2 field[]
	 * return Field[]
	 */
	private static Field[] mergerFieldArray(Field[] o1, Field[] o2) {
		   Field[] o;
		   if(o1==null) o = o2;
		   else if(o2==null)  o = o1;
		   else {
			   o = new Field[o1.length+o2.length];
			   int c = 0;
			   for(int i = 0; i<o1.length;i++) o[c++] = o1[i];
			   for(int i = 0; i<o2.length;i++) o[c++] = o2[i];
		   }
			   
		   return o;
	  }
	
	/*************************************************************************************************
	 * Ottiene tutti i Field della Classe
	 * 
	 * @param clazz Class
	 * @return Field[]
	 */
	public static Field[] getAllFields(Class clazz){
		   
		if( !clazz.getSuperclass().getName().equalsIgnoreCase("java.lang.Object") )
			return mergerFieldArray(
					clazz.getDeclaredFields(),
					getAllFields(clazz.getSuperclass())
					);
		else
			return clazz.getDeclaredFields(); 
	}
	
	/*******************************************************************************************************
	 * Crea una stringa che descrive l'oggetto in ingresso
	 * 
	 * param oggetto Object
	 * return String
	 */
	private static String inspectArray(Object oggetto){
		
		Class type = oggetto.getClass().getComponentType();
		StringBuffer message = new StringBuffer();
		if(type == Boolean.TYPE){
			boolean[] booleanValues = (boolean[])oggetto;
	        for(int c=0; c<booleanValues.length;c++) 
	        	message.append( "{" + booleanValues[c] + "}");
		} else if(type == Integer.TYPE) {
			int[] intValues = (int[])oggetto;
	        for(int c=0; c<intValues.length;c++) 
	        	message.append( "{" + intValues[c] + "}");
		}else if(type == Long.TYPE) {
			long[] longValues = (long[])oggetto;
	        for(int c=0; c<longValues.length;c++) 
	        	message.append( "{" + longValues[c] + "}");
		} else if(type == Float.TYPE) {
			float[] floatValues = (float[])oggetto;
	        for(int c=0; c<floatValues.length;c++) 
	        	message.append( "{" + floatValues[c] + "}");
		} else if (type == Double.TYPE) {
			double[] doubleValues = (double[])oggetto;
	        for(int c=0; c<doubleValues.length;c++) 
	        	message.append( "{" + doubleValues[c] + "}");
		} else {
			// non primitive type .... I suppose
			Object[] values = (Object[])oggetto;
	        for(int c=0; c<values.length;c++) 
	        	message.append( "{" +  values[c] + "}");
		} 
        return message.toString();
	}
	
	/*****************************************************************************************************
	 * Genera una stringa che descrive l'oggetto in ingresso
	 * 
	 * @param oggetto Object
	 * @return String
	 */
	public static String inspect( Object oggetto ) {
		StringBuffer descriptor = new StringBuffer("");
		if( oggetto != null ) {
			//Class classOggetto = oggetto.getClass();
			
			Field[] field = getAllFields(oggetto.getClass());
			for( int i=0; i < field.length; i++ ) {
				field[i].setAccessible( true );
				descriptor.append("[").append( field[i].getName() ).append( "]=[" );
				Object value = "";
				try {
					if (field[i].getType().isArray())
						value = FieldInspector.inspectArray(field[i].get( oggetto ));
					else
					    value = field[i].get( oggetto );
				} catch( Exception e ) {
					value = "undefined"; 
				}
				descriptor.append( value ).append( "]," );
			}
		}
		return descriptor.toString();
	}
	
	public static void main(String[] a) {
		FieldInspector f=new FieldInspector();
		f.setA(new String[]{"a","d","g"});
		f.setI(new int[]{1,2,3});
		f.setD(new double[]{3.12, 6.23, 5.00});
		f.setB(new boolean[]{true, false});
		
//		System.out.println(
//		FieldInspector.inspect(f));
		
	}
	private int[] i;
	private String[] a;
    private double[] d;
	private boolean[] b; 
    
	public String[] getA() {
		return a;
	}

	public void setA(String[] a) {
		this.a = a;
	}

	public int[] getI() {
		return i;
	}

	public void setI(int[] i) {
		this.i = i;
	}

	public double[] getD() {
		return d;
	}

	public void setD(double[] d) {
		this.d = d;
	}

	public boolean[] getB() {
		return b;
	}

	public void setB(boolean[] b) {
		this.b = b;
	}
	
	
}
