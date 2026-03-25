/**
 * AllegatoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.avlp.simog.beans;


public class AllegatoType {
    private byte[] documento;
    private String tipoDocumento;
    
    private String nomeFile;  // attribute
    private String note;  // attribute

    public AllegatoType() {}

    public AllegatoType(
		String tipoDoc,
		byte[] documento,
		String nomeFile,
		String note) {
		this.documento = documento;
		this.nomeFile = nomeFile;
		this.note = note;
		this.tipoDocumento = tipoDoc;
    }


    /**
     * Gets the documento value for this AllegatoType.
     * 
     * @return documento
     */
    public byte[] getDocumento() {
        return documento;
    }


    /**
     * Sets the documento value for this AllegatoType.
     * 
     * @param documento
     */
    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }


    /**
     * Gets the nomeFile value for this AllegatoType.
     * 
     * @return nomeFile
     */
    public String getNomeFile() {
        return nomeFile;
    }


    /**
     * Sets the nomeFile value for this AllegatoType.
     * 
     * @param nomeFile
     */
    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
