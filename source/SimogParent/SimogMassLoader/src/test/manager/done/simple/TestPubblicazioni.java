package test.manager.done.simple;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestPubblicazioni extends SuperTest{

	
	public void test(){
		super.initAll();
		PubblicazioneManager am = new PubblicazioneManager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		PubblicazioneBean ab = null;
		
		System.out.println("------- Load Test For Pubblicazioni BEGIN------------");
		try{
			
			testLoad(am, ab, opNumber);
			testAnnulla(am, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Pubblicazioni END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(PubblicazioneManager am, PubblicazioneBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		ab = am.getPubblicazione(super.getLong(1862), super.getDate("07/05/2009 15.56.10"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "0164905");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1862).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.getPubblicazione(super.getLong(1862), super.getDate("07/05/2009 15.56.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "0164905");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(1865).longValue());
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
	public void testAnnulla(PubblicazioneManager am, int opNumber) throws SQLException{
		
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		esito = am.annulla(super.getLong(1862), super.getDate("07/05/2009 15.56.00"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 11;
		esito = am.annulla("2", "0164905","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 11;
		esito = am.annulla(new Long(1865).longValue(),"test");	
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		esito = am.annulla(super.getLong(1862), super.getDate("07/05/2009 15.56.10"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 8;
		esito = am.annulla("1", "0164905","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 9;
		esito = am.annulla(new Long(1862).longValue(),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		TestPubblicazioni ta = new TestPubblicazioni();
		ta.test();

	}
}
