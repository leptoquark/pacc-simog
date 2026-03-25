package it.avlp.simog.dbToXml;

import java.sql.Connection;

import org.apache.log4j.Logger;

/**
 * Interfaccia che definisce il contratto del caricamento dei dati
 * da db a xmlbean
 * 
 * **/
public interface DbToXml {
	
		/**
		 * Metodo per il recupero dell'oggetto xmlbean costruito con i valori
		 * passati nella richiesta di istanza
		 * 
		 * @return Object
		 * @throws Exception
		 */
		public Object getXmlBean()throws Exception;
		/* metodo che permette (una volta eseguito il metodo populate) diricavare direttamente la stringa xml*/
		/**
		 * Metodo per il recupero dei una stringa ricavata dalla invocazione
		 * della funzione toString() sull' xmlbean	se e' gia stato creato, altrimenti
		 * viene creato e invocato il suddetto metodo
		 * 
		 * @return String
		 * @throws Exception
		 */
		public String getXmlString()throws Exception;
		
		/**
		 * Classe statica per valorizzazione/instanziazione dell'oggetto
		 * che implementa questa interfaccia e il ritorno
		 * del tipo [xmlbean] wrapper
		 *
		 */
		public static class Loader{
			
			/**
			 * Metodo per recuperare l'istanza della classe che implementa questa intefaccia
			 * 
			 * @param conn
			 * @param logger
			 * @param cig
			 * @param schede
			 * @return XmlBeanManager
			 * @throws Exception
			 */
//per eventuale roolback
//			public static SchedaDocument getInstance(Connection conn,Logger logger,String cig,String schede)throws Exception{
//				return new XmlBeanManager(conn,logger,cig,schede).getXmlBean();
//			}
			public static XmlBeanManager getInstance(Connection conn,Logger logger,String cig,String schede)throws Exception{
				return new XmlBeanManager(conn,logger,cig,schede);
			}
//			public static String getXml(Connection conn,Logger logger,String cig,String schede)throws Exception{
//				return new XmlBeanManager(conn,logger,cig,schede).getXmlString();
//			}
			/**
			 * Metodo per il controllo dell'esistenza di gare per il codice regionale 
			 * di cui adminOr
			 * 
			 * @param conn Connection
			 * @param logger Logger
			 * @param adminOr String
			 * @param cig String
			 * @return boolean - esito della ricerca
			 * @throws Exception
			 */
			public static boolean verify(Connection conn,Logger logger,String adminOr,String cig) throws Exception{
				return XmlBeanManager.verify(conn,logger,adminOr,cig);
			}
		}
}
