package it.avlp.simog.beans.aggiudicazione.luogo;

/****************************************************************************
 * <code>public class <b>NutsBean</b> </code><br><br>
 * La classe permette la creazione dell'oggetto NutsBean. Prevede i seguenti parametri:
 * <ul>
 * <li>int : livello
 * <li>String : idNuts
 * <li>String : descrizione
 * </ul>
 * ed i relativi get e set associati. 
 *
 */
public class NutsBean implements java.io.Serializable{
	private int livello;
	private String idNuts;
	private String descrizione;
	
	public int getLivello() {
		return livello;
	}
	public void setLivello(int livello) {
		this.livello = livello;
	}
	public String getIdNuts() {
		return idNuts;
	}
	public void setIdNuts(String idNuts) {
		this.idNuts = idNuts;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
