package test.manager.done.simple;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestAccordo extends SuperTest{

	
	/**
	 * Chiama tutti i metodi di test
	 */
	public void test(){
		
		super.initAll();
		AccordoManager am = new AccordoManager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		AccordoBean ab = null;
		
		System.out.println("------- Load Test For Accordo BEGIN------------");
		try{
			
			testLoad(am, ab, opNumber);
			testAnnulla(am, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Accordo END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(AccordoManager am, AccordoBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		ab = am.loadOne(super.getLong(1333), super.getDate("17/03/2009 12.07.13"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1333).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.loadOne(super.getLong(1333), super.getDate("17/03/2009 12.07.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(1334).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));		
	}

	/**
	 * Metodo dedicato al test dei Load
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testAnnulla(AccordoManager am, int opNumber) throws SQLException{
		
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		esito = am.annulla(super.getLong(1333), super.getDate("17/03/2009 12.07.00"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 11;
		esito = am.annulla("2", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 11;
		esito = am.annulla(new Long(1334).longValue(),"test");	
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		esito = am.annulla(super.getLong(1333), super.getDate("21/09/2009 11.45.48"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 8;
		esito = am.annulla("1", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 9;
		esito = am.annulla(new Long(1333).longValue(),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		TestAccordo ta = new TestAccordo();
		ta.test();

	}
}
