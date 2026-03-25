package it.avlp.simog.beans;

import it.avlp.simog.common.servlet.PSReq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

	/**
	 * Struttura dati requisiti
	 */

public class RequisitoGara {

    public class Documento {
       
      public Documento() {}
      
      public Documento(
             long codice_tipo_doc, 
             String descrizione_documento,
             String emettitore, 
             String fax, 
             String telefono, 
             String mail,
             String mail_pec) 
      {
         this.codice_tipo_doc = codice_tipo_doc;
         this.descrizione_documento = descrizione_documento;
         this.emettitore = emettitore;
         this.fax = fax;
         this.telefono = telefono;
         this.mail = mail;
         this.mail_pec = mail_pec;
      }
       
      private long codice_tipo_doc;
      private String codice;
      private String descrizione_documento;
      private String emettitore;
      private String fax;
      private String telefono;
      private String mail;
      private String mail_pec;
      
      private boolean obbligatorio;
       
      public long getCodice_tipo_doc() {
         return codice_tipo_doc;
      }
      public void setCodice_tipo_doc(long codice_tipo_doc) {
         this.codice_tipo_doc = codice_tipo_doc;
      }
      public String getCodice() {
         return codice;
      }
      public void setCodice(String codice) {
         this.codice = codice;
      }      
      public String getDescrizione_documento() {
         return descrizione_documento;
      }
      public void setDescrizione_documento(String descrizione_documento) {
         this.descrizione_documento = descrizione_documento;
      }
      public String getEmettitore() {
         return emettitore;
      }
      public void setEmettitore(String emettitore) {
         this.emettitore = emettitore;
      }
      public String getFax() {
         return fax;
      }
      public void setFax(String fax) {
         this.fax = fax;
      }
      public String getTelefono() {
         return telefono;
      }
      public void setTelefono(String telefono) {
         this.telefono = telefono;
      }
      public String getMail() {
         return mail;
      }
      public void setMail(String mail) {
         this.mail = mail;
      }
      public String getMail_pec() {
         return mail_pec;
      }
      public void setMail_pec(String mail_pec) {
         this.mail_pec = mail_pec;
      }
      
      public boolean isObbligatorio() {
         return obbligatorio;
      }
      public void setObbligatorio(boolean obbligatorio) {
         this.obbligatorio = obbligatorio;
      }

      @Override
      public boolean equals(Object obj) {
         
         boolean result = true;
         
         if( obj instanceof Documento ){
            
            Documento other = (Documento)obj;
            
            result = result && codice_tipo_doc == other.getCodice_tipo_doc();
//            result = result && codice.equalsIgnoreCase(other.getCodice());
            result = result && descrizione_documento.equalsIgnoreCase(other.getDescrizione_documento());
            result = result && emettitore.equalsIgnoreCase(other.getEmettitore());
            result = result && fax.equalsIgnoreCase(other.getFax());
            result = result && telefono.equalsIgnoreCase(other.getTelefono());
            result = result && mail.equalsIgnoreCase(other.getMail());
            result = result && mail_pec.equalsIgnoreCase(other.getMail_pec());
            
         } else {
            return false;
         }
         
         return result;
      }
      
      @Override
      public String toString() {
         return
         "[" + codice_tipo_doc
         + ", " + codice
         + ", " + descrizione_documento
         + ", " + emettitore
         + ", " + fax
         + ", " + telefono
         + ", " + mail
         + ", " + mail_pec
         + "]";
      }
       
    }
    
    private static final long serialVersionUID = 1L;
    
    public static String TIPO_USO_OB = "O";
    public static String TIPO_USO_OM = "M";
    public static String TIPO_USO_FA = "F";
    public static String TIPO_USO_AR = "AR";
    public static String TIPO_USO_AA = "AA";

    private long codice_requisito_gara;
    private long codice_dettaglio;
	private String codice;
	private String descrizione;
	private String valore;
	private String flag_esclusione;
	private String flag_comprova_offerta;
	private String flag_avvalimento;
	private String flag_bando_tipo;
	private String flag_riservatezza;
	
	private List<Long> lotti_associati = new ArrayList<Long>();
	private List<Documento> documenti = new LinkedList<RequisitoGara.Documento>();
	
	private String listaDocumentiString;
	private String tipoUso;
	
	public  RequisitoGara(){
	   super();
	}
	
    public  RequisitoGara(
         long codice_requisito_gara,
         long codice_dettaglio,
         String codice, 
         String descrizione,
         String valore, 
         String flag_esclusione, 
         String flag_comprova_offerta,
         String flag_avvalimento, 
         String flag_bando_tipo,
         String flag_riservatezza) 
    {
       this.codice_requisito_gara = codice_requisito_gara;
       this.codice_dettaglio = codice_dettaglio;
       this.codice = codice;
       this.descrizione = descrizione;
       this.valore = valore;
       this.flag_esclusione = flag_esclusione;
       this.flag_comprova_offerta = flag_comprova_offerta;
       this.flag_avvalimento = flag_avvalimento;
       this.flag_bando_tipo = flag_bando_tipo;
       this.flag_riservatezza = flag_riservatezza;
    }


    
   
   public long getCodice_requisito_gara() {
      return codice_requisito_gara;
   }

   public void setCodice_requisito_gara(long codice_requisito_gara) {
      this.codice_requisito_gara = codice_requisito_gara;
   }

   public long getCodice_dettaglio() {
      return codice_dettaglio;
   }

   public long getCodice_dettaglio_FE() {
      return PSReq.CODICE_REQUISITO_NON_CODIFICATO.equals(codice) ? codice_dettaglio + PSReq.MARKER_999 : codice_dettaglio;
   }

   public void setCodice_dettaglio(long codice_dettaglio) {
      this.codice_dettaglio = codice_dettaglio;
   }

   public String getCodice() {
      return codice;
   }

   public void setCodice(String codice) {
      this.codice = codice;
   }

   public String getDescrizione() {
      return descrizione;
   }
   
   public String getDescrizionePul() {
      return descrizione == null ? null : descrizione.replaceAll("'", "" + (char) 180);
   }

   public void setDescrizione(String descrizione) {
      this.descrizione = descrizione;
   }
   
   public String getValore() {
      return valore;
   }
   
   public void setValore(String valore) {
      this.valore = valore;
   }
   
   public String getFlag_esclusione() {
      return flag_esclusione;
   }
   
   public void setFlag_esclusione(String flag_esclusione) {
      this.flag_esclusione = flag_esclusione;
   }
   
   public String getFlag_comprova_offerta() {
      return flag_comprova_offerta;
   }
   
   public void setFlag_comprova_offerta(String flag_comprova_offerta) {
      this.flag_comprova_offerta = flag_comprova_offerta;
   }
   
   public String getFlag_avvalimento() {
      return flag_avvalimento;
   }
   
   public void setFlag_avvalimento(String flag_avvalimento) {
      this.flag_avvalimento = flag_avvalimento;
   }
   
   public String getFlag_bando_tipo() {
      return flag_bando_tipo;
   }
   
   public void setFlag_bando_tipo(String flag_bando_tipo) {
      this.flag_bando_tipo = flag_bando_tipo;
   }
   
   public String getFlag_riservatezza() {
      return flag_riservatezza;
   }
   
   public void setFlag_riservatezza(String flag_riservatezza) {
      this.flag_riservatezza = flag_riservatezza;
   }
   
   public List<Long> getLotti_associati() {
      return lotti_associati;
   }

   public void setLotti_associati(List<Long> lotti_associati) {
      this.lotti_associati = lotti_associati;
   }

   public List<Documento> getDocumenti() {
      return documenti;
   }
   
   public void setDocumenti(List<Documento> documenti) {
      this.documenti = documenti;
   }
   
   public String getListaDocumentiString() {
      return listaDocumentiString;
   }

   public void setListaDocumentiString(String listaDocumentiString) {
      this.listaDocumentiString = listaDocumentiString;
   }  
   
   public String getTipoUso() {
      return tipoUso;
   }

   public void setTipoUso(String tipoUso) {
      this.tipoUso = tipoUso;
   }

   
   public boolean isObbligatorio(){
      return !"F".equals(this.tipoUso);
   }
   
   @Override
   public boolean equals(Object obj) {
      
      boolean result = true;
      
      if( obj instanceof RequisitoGara  ){
         
         RequisitoGara other = (RequisitoGara)obj;
         
         result = result && (codice_dettaglio == other.getCodice_dettaglio());
         
         if( descrizione != null ){
            result = result && (descrizione.equalsIgnoreCase(other.getDescrizione()));
         } else {
            result = result && other.getDescrizione() == null;
         }
         
         if( valore != null ){
            result = result && (valore.equalsIgnoreCase(other.getValore()));     
         } else {
            result = result && other.getValore() == null;
         }
         
         result = result && (flag_esclusione.equalsIgnoreCase(other.getFlag_esclusione()));
         result = result && (flag_comprova_offerta.equalsIgnoreCase(other.getFlag_comprova_offerta()));
         result = result && (flag_avvalimento.equalsIgnoreCase(other.getFlag_avvalimento()));
         result = result && (flag_bando_tipo.equalsIgnoreCase(other.getFlag_bando_tipo()));
         result = result && (flag_riservatezza.equalsIgnoreCase(other.getFlag_riservatezza()));
         
         result = result && ( lotti_associati.equals(other.getLotti_associati()) );
         result = result && ( getOrderedList(documenti).equals(getOrderedList(other.getDocumenti())) );
         
      } else {
         result = false; 
      }
      return result;
   }
   
   //Ordina una lista di documenti in base al codice tipo documento
   private List<Documento> getOrderedList(List<Documento> lista){
      Collections.sort(lista, new DocumentoComparator());
      return lista;
   }
   
   @Override
   public String toString() {
      return
      "[" + codice_requisito_gara
      + ", " + codice_dettaglio
      + ", " + codice
      + ", " + descrizione
      + ", " + valore
      + ", " + flag_esclusione
      + ", " + flag_comprova_offerta
      + ", " + flag_avvalimento
      + ", " + flag_bando_tipo
      + ", " + flag_riservatezza
      + "]";
   }

   
   public RequisitoGara fakeCopy(){
      RequisitoGara newMe = new RequisitoGara();
      newMe.setCodice_requisito_gara(codice_requisito_gara);
      newMe.setCodice_dettaglio(codice_dettaglio);
      newMe.setCodice(codice);
      newMe.setDescrizione(descrizione);
      newMe.setValore(valore);
      newMe.setFlag_esclusione(flag_esclusione);
      newMe.setFlag_comprova_offerta(flag_comprova_offerta);
      newMe.setFlag_avvalimento(flag_avvalimento);
      newMe.setFlag_bando_tipo(flag_bando_tipo);
      newMe.setFlag_riservatezza(flag_riservatezza);
      newMe.setDocumenti(documenti);
      return newMe;
   }
   
   
}

