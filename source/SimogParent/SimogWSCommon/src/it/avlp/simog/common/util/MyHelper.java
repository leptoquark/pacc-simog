package it.avlp.simog.common.util;

public class MyHelper {

		/***
		 *  Controlla se &egrave; null un oggetto
		 * @param o Object oggetto da testare
		 * @return boolean - true se oggetto null altrimenti false
		 */
		public static boolean isNull(Object o){			
			return (o == null);
		}

		/***
		 * Controlla se l'oggetto &egrave; null (primo parametro)ve in tal caso ritorna 
		 * l'oggetto di default (secondo parametro)
		 * @param o Object da controllare
		 * @param w Object da ritornare se null
		 * @return Object da controllare o oggetto da ritornare
		 */
		public static Object isNull(Object o, Object w){			
			return (o == null ? w : o);
		}
}
