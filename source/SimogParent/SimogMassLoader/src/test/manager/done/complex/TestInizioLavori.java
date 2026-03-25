package test.manager.done.complex;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;

import test.manager.SuperTest;

public class TestInizioLavori extends SuperTest {

	public void test(){
		super.initAll();
		InizioLavoriManager am = new InizioLavoriManager(dbm.getCurrentActiveConnection(),logger);
		ResponsabileInizioManager em = new ResponsabileInizioManager(dbm.getCurrentActiveConnection(),logger);
		PosizAggiudManager em1 = new PosizAggiudManager(dbm.getCurrentActiveConnection(),logger);
		
		int opNumber = 0;
		InizioLavoriBean ab = null;
		
		System.out.println("------- Load Test For Variante BEGIN------------");
		try{
			
			//testLoad(am, ab, opNumber);
			testAnnulla(am, em, em1, ab, opNumber);
			
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
	public void testLoad(InizioLavoriManager am, InizioLavoriBean ab,int opNumber) throws SQLException{
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
		ab = am.loadByIdSimog(new Long(1486).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
//		System.out.println("\t------- Load Test Record NON esistenti ------------");
//		opNumber = 4;
//		ab = am.load(super.getLong(1929), super.getDate("17/03/2009 11.51.00"));
//		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
//		
//		opNumber = 5;
//		ab = am.loadByIdLocale("2", "1929");
//		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
//	
//		opNumber = 6;
//		ab = am.loadByIdSimog(new Long(1487).longValue());
//		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));		
	}

	/**
	 * Testa load + annulla dei vari manager collegati (1->n)
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testAnnulla(InizioLavoriManager am,
							ResponsabileInizioManager em, 
							PosizAggiudManager em1, 
							InizioLavoriBean ab, int opNumber) throws SQLException{
		
		/** NOTA: faccio il load per non dover ripetere i parametri **/
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		ab = am.load(super.getLong(1929), super.getDate("17/03/2009 11.51.00"));
		if(ab != null){
			esito = am.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori(),"test");
		}
		if(esito){
			if(em.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileInizioManager: Nessun Responsabile Trovato");
			}
			if(em1.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em1.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione PosizAggiudManager: Nessuna Posizione Aggiudicatario Trovata");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 11;
		ab = am.loadByIdLocale("2", "1929");
		esito = am.annulla(ab.getIdLocale(), String.valueOf(ab.getIdAggiudicazione()),"test");
		if(esito){
			if(em.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileInizioManager: Nessun Responsabile Trovato");
			}
			if(em1.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em1.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione PosizAggiudManager: Nessuna Posizione Aggiudicatario Trovata");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 11;
		ab = am.loadByIdSimog(new Long(1487).longValue());
		esito = am.annulla(ab.getIdInizioLavori(),"test");	
		if(esito){
			if(em.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileInizioManager: Nessun Responsabile Trovato");
			}
			if(em1.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em1.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione PosizAggiudManager: Nessuna Posizione Aggiudicatario Trovata");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		
		
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		ab = am.load(super.getLong(1929), super.getDate("17/03/2009 11.51.24"));
		esito = am.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori(),"test");
		if(esito){
			if(em.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileInizioManager: Nessun Responsabile Trovato");
			}
			if(em1.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em1.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione PosizAggiudManager: Nessuna Posizione Aggiudicatario Trovata");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 8;
		ab = am.loadByIdLocale("1", "1929");
		esito = am.annulla(ab.getIdLocale(), String.valueOf(ab.getIdAggiudicazione()),"test");
		if(esito){
			if(em.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileInizioManager: Nessun Responsabile Trovato");
			}
			if(em1.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em1.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione PosizAggiudManager: Nessuna Posizione Aggiudicatario Trovata");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 9;
		ab = am.loadByIdSimog(new Long(1486).longValue());
		esito = am.annulla(ab.getIdInizioLavori(),"test");
		if(esito){
			if(em.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileInizioManager: Nessun Responsabile Trovato");
			}
			if(em1.loadMany(ab.getIdInizioLavori(), ab.getDataInizioLavori(), false).size() > 0){
				esito = em1.annulla(ab.getIdInizioLavori(), ab.getDataInizioLavori());
			}else{
				System.out.println("\t\t\tEsito Operazione PosizAggiudManager: Nessuna Posizione Aggiudicatario Trovata");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}

	public static void main(String[] args) {
		TestInizioLavori ta = new TestInizioLavori();
		ta.test();

	}
}
