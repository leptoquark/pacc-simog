/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.ticket;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Collaborazioni {
	@XmlElement(name = "collaborazione")
	private List<Collaborazione> collaborazione;

	/**
	 * @return the collaborazione
	 */
	public List<Collaborazione> getCollaborazione() {
		return collaborazione;
	}

	/**
	 * @param collaborazione the collaborazione to set
	 */
	public void setCollaborazione(List<Collaborazione> collaborazione) {
		this.collaborazione = collaborazione;
	}
}