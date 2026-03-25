package it.avlp.simog.garamanager.lotto;

public class DocumentoBean {
	
	private int id_documento;
	private int id_lotto;
	private String nomeDocumento;
	private String documento;
	
	
	
	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public int getId_documento() {
		return id_documento;
	}
	
	public void setId_documento(int id_documento) {
		this.id_documento = id_documento;
	}
	
	public int getId_lotto() {
		return id_lotto;
	}
	
	public void setId_lotto(int id_lotto) {
		this.id_lotto = id_lotto;
	}
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}	

}
