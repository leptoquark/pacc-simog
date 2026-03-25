package test.manager.done.simple;

import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestAvanzamento extends SuperTest{

	
	public void test(){
		super.initAll();
		AvanzamentoManager am = new AvanzamentoManager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		AvanzamentoBean ab = null;
		
		System.out.println("------- Load Test For Avanzamento BEGIN------------");
		try{
			
			testLoad(am, ab, opNumber);
			testAnnulla(am, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Avanzamento END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(AvanzamentoManager am, AvanzamentoBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		// ATTENZIONE QUESTO E' PER ID_AGGIUDICAZIONE E DATA_INIZIO_AGGIUDICAZIONE (PERCHE ?)
		ab = am.loadOne(super.getLong(1494), super.getDate("17/03/2009 12.08.57"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1494).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.loadOne(super.getLong(1494), super.getDate("17/03/2009 12.08.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(1495).longValue());
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
	public void testAnnulla(AvanzamentoManager am, int opNumber) throws SQLException{
		
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		esito = am.annulla(super.getLong(1494), super.getDate("17/03/2009 12.08.00"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 11;
		esito = am.annulla("2", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 11;
		esito = am.annulla(new Long(1495).longValue(),"test");	
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		esito = am.annulla(super.getLong(1494), super.getDate("17/03/2009 12.08.57"),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		opNumber = 8;
		esito = am.annulla("1", "1929","test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		opNumber = 9;
		esito = am.annulla(new Long(1494).longValue(),"test");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		TestAvanzamento ta = new TestAvanzamento();
		ta.test();

	}
}
