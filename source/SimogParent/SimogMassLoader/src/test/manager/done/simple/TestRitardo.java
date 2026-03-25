package test.manager.done.simple;

import it.avcp.simog.managers.r129.R129Manager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestRitardo extends SuperTest{

	
	public void test(){
		super.initAll();
		R129Manager am = new R129Manager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		R129Bean ab = null;
		
		System.out.println("------- Load Test For Ritardo BEGIN------------");
		try{
			
			testLoad(am, ab, opNumber);
			testAnnulla(am, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Ritardo END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(R129Manager am, R129Bean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		ab = am.loadOne(super.getLong(432), super.getDate("17/03/2009 10.41.42"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "1928");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(432).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.loadOne(super.getLong(432), super.getDate("17/03/2009 10.41.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(433).longValue());
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
	public void testAnnulla(R129Manager am, int opNumber) throws SQLException{
		
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		esito = am.annulla(super.getLong(432), super.getDate("17/03/2009 10.41.00"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 11;
		esito = am.annulla("2", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 11;
		esito = am.annulla(new Long(433).longValue(),"test");	
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		esito = am.annulla(super.getLong(432), super.getDate("17/03/2009 10.41.42"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 8;
		esito = am.annulla("1", "1928","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 9;
		esito = am.annulla(new Long(432).longValue(),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		TestRitardo ta = new TestRitardo();
		ta.test();

	}
}
