package test.manager.done.complex;

import it.avcp.simog.managers.variante.EventiMotiviVariantiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestVariante extends SuperTest{

	
	/**
	 * Questo manager ha un referenza indiretta ad un'altro manager
	 * ovverosia c'e' un record su un'altra tabella che dipende da questo..
	 * 
	 * Il test e' duque piu' complesso..
	 */
	public void test(){
		super.initAll();
		VarianteManager am = new VarianteManager(dbm.getCurrentActiveConnection(),logger);
		EventiMotiviVariantiManager em = new EventiMotiviVariantiManager(dbm.getCurrentActiveConnection(),logger);
		int opNumber = 0;
		VarianteBean ab = null;
		
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
	public void testLoad(VarianteManager am, VarianteBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		ab = am.loadOne(super.getLong(1333), super.getDate("05/05/2009 14.10.22"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "478");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1333).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.loadOne(super.getLong(1333), super.getDate("05/05/2009 14.10.00"));
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "478");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(1334).longValue());
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
	public void testAnnulla(VarianteManager am,EventiMotiviVariantiManager em, VarianteBean ab, int opNumber) throws SQLException{
		
		/** NOTA: faccio il load per non dover ripetere i parametri **/
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		ab = am.loadOne(super.getLong(1333), super.getDate("05/05/2009 14.10.00"));
		if(ab != null){
			esito = am.annulla(ab.getIdVariante(), ab.getDataInizioVar(),"test");
		}
		if(esito){
			esito = em.annulla(ab.getIdVariante(), ab.getDataInizioVar());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 11;
		ab = am.loadByIdLocale("2", "478");
		esito = am.annulla(ab.getIdLocale(), String.valueOf(ab.getIdAggiudicazione()),"test");
		if(esito){
			esito = em.annulla(ab.getIdVariante(), ab.getDataInizioVar());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 11;
		ab = am.loadByIdSimog(new Long(1334).longValue());
		esito = am.annulla(ab.getIdVariante(),"test");	
		if(esito){
			esito = em.annulla(ab.getIdVariante(), ab.getDataInizioVar());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		
		
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		ab = am.loadOne(super.getLong(1333), super.getDate("05/05/2009 14.10.22"));
		esito = am.annulla(ab.getIdVariante(), ab.getDataInizioVar(),"test");
		if(esito){
			esito = em.annulla(ab.getIdVariante(), ab.getDataInizioVar());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 8;
		ab = am.loadByIdLocale("1", "478");
		esito = am.annulla(ab.getIdLocale(), String.valueOf(ab.getIdAggiudicazione()),"test");
		if(esito){
			esito = em.annulla(ab.getIdVariante(), ab.getDataInizioVar());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 9;
		ab = am.loadByIdSimog(new Long(1333).longValue());
		esito = am.annulla(ab.getIdVariante(),"test");
		if(esito){
			esito = em.annulla(ab.getIdVariante(), ab.getDataInizioVar());
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}

	public static void main(String[] args) {
		TestVariante ta = new TestVariante();
		ta.test();

	}
}
