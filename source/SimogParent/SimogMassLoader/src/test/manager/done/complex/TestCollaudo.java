package test.manager.done.complex;

import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestCollaudo extends SuperTest{

	
	public void test(){
		super.initAll();
		CollaudoManager am = new CollaudoManager(dbm.getCurrentActiveConnection(),logger);
		ResponsabileCollManager em = new ResponsabileCollManager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		CollaudoBean ab = null;
		
		System.out.println("------- Load Test For Variante BEGIN------------");
		try{
			
			testLoad(am, ab, opNumber);
			testAnnulla(am, em, ab, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Variante END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(CollaudoManager am, CollaudoBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		// ATTENZIONE BY DATI AGGIUDICAZIONE.
		ab = am.load(super.getLong(1929), super.getDate("17/03/2009 11.51.24"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1312).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.load(super.getLong(1929), super.getDate("17/03/2009 11.51.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "1929");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(1313).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));		
	}

	/**
	 * Testa load + annulla dei vari manager collegati (1->n)
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testAnnulla(CollaudoManager am,ResponsabileCollManager em, CollaudoBean ab, int opNumber) throws SQLException{
		
		/** NOTA: faccio il load per non dover ripetere i parametri **/
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		ab = am.load(super.getLong(1333), super.getDate("17/03/2009 11.51.00"));
		if(ab != null){
			esito = am.annulla(ab.getIdCollaudo(), ab.getDataIniColl(),"test");
		}
		if(esito){
			esito = em.annulla(ab.getIdCollaudo(), ab.getDataIniColl());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 11;
		ab = am.loadByIdLocale("2", "1929");
		esito = am.annulla(ab.getIdLocale(), String.valueOf(ab.getIdAggiudicazione()),"test");
		if(esito){
			esito = em.annulla(ab.getIdCollaudo(), ab.getDataIniColl());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 11;
		ab = am.loadByIdSimog(new Long(1313).longValue());
		esito = am.annulla(ab.getIdCollaudo(),"test");	
		if(esito){
			esito = em.annulla(ab.getIdCollaudo(), ab.getDataIniColl());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		
		
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		ab = am.load(super.getLong(1928), super.getDate("16/03/2009 15.14.04"));
		esito = am.annulla(ab.getIdCollaudo(), ab.getDataIniColl(),"test");
		if(esito){
			esito = em.annulla(ab.getIdCollaudo(), ab.getDataIniColl());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 8;
		ab = am.loadByIdLocale("1", "1928");
		esito = am.annulla(ab.getIdLocale(), String.valueOf(ab.getIdAggiudicazione()),"test");
		if(esito){
			esito = em.annulla(ab.getIdCollaudo(), ab.getDataIniColl());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 9;
		ab = am.loadByIdSimog(new Long(1311).longValue());
		esito = am.annulla(ab.getIdCollaudo(),"test");
		if(esito){
			esito = em.annulla(ab.getIdCollaudo(), ab.getDataIniColl());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}

	public static void main(String[] args) {
		TestCollaudo ta = new TestCollaudo();
		ta.test();

	}
}
