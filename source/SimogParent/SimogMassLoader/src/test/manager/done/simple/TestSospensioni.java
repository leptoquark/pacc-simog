package test.manager.done.simple;

import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestSospensioni extends SuperTest{

	
	public void test(){
		super.initAll();
		SospensioniManager am = new SospensioniManager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		SospensioniBean ab = null;
		
		System.out.println("------- Load Test For Sospensioni BEGIN------------");
		try{
			
			testLoad(am, ab, opNumber);
			testAnnulla(am, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Sospensioni END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(SospensioniManager am, SospensioniBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		ab = am.loadOne(super.getLong(1338), super.getDate("17/03/2009 12.08.01"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1338).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.loadOne(super.getLong(1338), super.getDate("17/03/2009 12.08.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(1339).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));		
	}

	/**
	 * Metodo dedicato al test dei Load
	 *  
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testAnnulla(SospensioniManager am, int opNumber) throws SQLException{
		
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		esito = am.annulla(super.getLong(1338), super.getDate("17/03/2009 12.08.00"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 11;
		esito = am.annulla("2", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 11;
		esito = am.annulla(new Long(1339).longValue(),"test");	
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		esito = am.annulla(super.getLong(1338), super.getDate("17/03/2009 12.08.01"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 8;
		esito = am.annulla("1", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 9;
		esito = am.annulla(new Long(1338).longValue(),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		TestSospensioni ta = new TestSospensioni();
		ta.test();

	}
}
