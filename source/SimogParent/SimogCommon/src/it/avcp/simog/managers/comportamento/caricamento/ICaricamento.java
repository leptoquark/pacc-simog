package it.avcp.simog.managers.comportamento.caricamento;


/**
 * Tutte le interfaccie che estendono questa interfaccia hanno la funzione di caricamento (Load).
 * Tutte le interfaccie che estendono questa interfaccia dichiarano questi tre metodi(che sono per tipati
 * a seconda del manager,bean di riferimento):
 * 	- Object loadByIdLocale(String idLocale, String rifSimog) throws SQLException;
 *  - Object loadByIdSimog(long idSimog) throws SQLException;
 *  - void fillBean(ResultSet rs, Object bean) throws SQLException;
 * @author vletizia
 * 
 *
 */
public interface ICaricamento {

	/**
	 * Questo metodo serve alla valorizzazione centralizzata del bean associato al manager
	 * void fillBean(ResultSet rs, Object bean) throws SQLException;
	 * */
	
	/**
	 * Questo metodo serve al caricamento del bean con i dati idLocale (referenza assegnata da un sistema esterno)
	 * e il rifSimog nell'ambito del quale l'idLocale e' univoco
	 * NOTA: il bean ritornato da questo metodo e' al piu' vuoto, mai nullo anche qualora i riferimenti non siano validi
	 * Object loadByIdLocale(String idLocale, String rifSimog) throws SQLException;
	 * */
	
	/**
	 * Questo metodo serve al caricamento del bean tramite l'id del record nel sistema simog
	 * NOTA: il bean ritornato da questo metodo e' al piu' vuoto, mai nullo anche qualora i riferimenti non siano validi
	 * Object loadByIdSimog(long idSimog) throws SQLException;
	 * */
	
}
