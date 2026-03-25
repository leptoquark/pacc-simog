package it.avlp.simog.validatore;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.RetroController;
import it.avlp.simog.util.SimogProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



/**
 * @author ppientini
 *
 * Classe che individua il corretto flusso di acquisizione schede
 */
public class SimogFlusso {

   // elenco schede ammesse per il flusso individuato
   private List<IdentificativoSchede> schede = new ArrayList<IdentificativoSchede>();

   private String tipoSettore;
   private String tipoContratto;
   private String flagEscluso;
   private int modoReal;
   private String dataPubb;
   private float  importo;
   private TipoFlusso tipoFlusso; 
   private int delega; //TICKET ALM - 3.04.3
   private String dataCreazione;
   private int idSvolgimento;
    
    /** Costruttore
    * @param tipoSettore
    * @param tipoContratto
    * @param flagEscluso
    * @param modoReal
    * @param dataPubb
    * @param importo
    */
   public SimogFlusso(String tipoSettore, String tipoContratto, String flagEscluso, int modoReal, String dataPubb, float  importo){
       this.tipoSettore = tipoSettore;
       this.tipoContratto = tipoContratto;
       this.modoReal = modoReal;
       this.flagEscluso = flagEscluso;
       this.dataPubb = dataPubb;
       this.importo = importo;
       this.delega = 0;
       this.dataCreazione = "19700101";
       this.tipoFlusso = determinaFlusso();
   }
   
   /** Costruttore con delega
    * TICKET ALM - 3.04.3
   * @param tipoSettore
   * @param tipoContratto
   * @param flagEscluso
   * @param modoReal
   * @param dataPubb
   * @param importo
   * @param delega
   */
  public SimogFlusso(String tipoSettore, String tipoContratto, String flagEscluso, 
		             int modoReal, String dataPubb, float  importo, int delega, 
		             String dataCreazione, int idSvolgimento){
      this.tipoSettore = tipoSettore;
      this.tipoContratto = tipoContratto;
      this.modoReal = modoReal;
      this.flagEscluso = flagEscluso;
      this.dataPubb = dataPubb;
      this.importo = importo;
      this.delega = delega;
      this.dataCreazione = dataCreazione;
      this.idSvolgimento = idSvolgimento;
      this.tipoFlusso = determinaFlusso();
  }
   
        
   /** determina il tipo di flusso in base ai parametri discriminanti
    * @return tipo flusso o null
    */  
   private TipoFlusso  determinaFlusso(){
      
	   //TICKET ALM #13575 - 3.04.4.1
	   if(isConcorsoProgettazioneIdee()) {
		   schede.add(IdentificativoSchede.getDatiComuni());
	       schede.add(IdentificativoSchede.getAggiudicazione());
	       return TipoFlusso.AGGIUDICAZIONE;
	   }
	   
      //Ordinari e speciali Sotto 150k escluso adesione senza scc -> niente!
//	   3.04.8 34190 fix
      if (isEscluso() && (isAdesioneNoDC() || isConcessioneNoDC()) && isSottoSoglia())
         return null;

      //Ordinari e speciali Sotto 150k escluso adesione con  scc -> niente!
//      3.04.8 34190 fix
      if (isEscluso() && (isAdesioneDC() || isConcessioneDC()) && isSottoSoglia())
         return null;

      //Ordinari e speciali Sotto 150k escluso accordo quadro -> niente!
      if (isEscluso() && isAccordo() && isSottoSoglia())
         return null;

      //Ordinari e speciali Sotto 150k escluso -> niente!
      if (isEscluso() && isSottoSoglia())
         return null;

      // Sopra 150k escluso accordo quadro
      if (isEscluso() && isAccordo() 
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getEscluso());
         schede.add(IdentificativoSchede.getInizioLavori());
         //TICKET ALM #14626 - 3.04.5
         if(dataCreazione.compareTo(Costanti.DATA_DL50) >= 0)
	         schede.add(IdentificativoSchede.getVarianti());        	 
      
         return TipoFlusso.ESCLUSO;
      }
         
      // Sopra 150k escluso adesione senza scc
      //3.04.8 34190 fix
      if (isEscluso() && (isAdesioneNoDC() || isConcessioneNoDC()) 
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAdesione());
         schede.add(IdentificativoSchede.getInizioLavori()); //TICKET ALM #20681
         schede.add(IdentificativoSchede.getAvanzamenti());
         schede.add(IdentificativoSchede.getConclusione());
         schede.add(IdentificativoSchede.getCollaudo());
         schede.add(IdentificativoSchede.getRitardo());
         schede.add(IdentificativoSchede.getAccordi());
         schede.add(IdentificativoSchede.getSospensioni());
         schede.add(IdentificativoSchede.getSubAppalti());
         schede.add(IdentificativoSchede.getVarianti());
         	
         return TipoFlusso.ADESIONE;
      }

      // Sopra 150k escluso adesione con scc
//      3.04.8 34190 fix
      if (isEscluso() && (isAdesioneDC() || isConcessioneDC()) 
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getEscluso());
         schede.add(IdentificativoSchede.getInizioLavori());
         
         return TipoFlusso.ESCLUSO;
      }
      
      // Sopra 150k escluso
      if (isEscluso() 
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getEscluso());
         schede.add(IdentificativoSchede.getInizioLavori());
         
         return TipoFlusso.ESCLUSO;
      }
      
      // Ordinari Sopra 150k adesione con scc
      //3.04.8 34190 fix
      if (!isSpeciale() && (isAdesioneDC() || isConcessioneDC())
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAggiudicazione());
         if(!isDelega()) {
	         schede.add(IdentificativoSchede.getInizioLavori());
	// PP escludo in visualizzazione         if(importo > Costanti.IMPORTO_LOTTO_500000 || importo == Costanti.IMPORTO_FUORI_SCALA)
	         schede.add(IdentificativoSchede.getAvanzamenti());
	         schede.add(IdentificativoSchede.getConclusione());
	         schede.add(IdentificativoSchede.getCollaudo());
	         schede.add(IdentificativoSchede.getRitardo());
	         schede.add(IdentificativoSchede.getAccordi());
	         schede.add(IdentificativoSchede.getSospensioni());
	         schede.add(IdentificativoSchede.getSubAppalti());
	         schede.add(IdentificativoSchede.getVarianti());
         }
         return TipoFlusso.AGGIUDICAZIONE;
      }     

      // Speciali Sopra 150k adesione con scc
      //3.04.8 34190 fix
      if (isSpeciale() && (isAdesioneDC() || isConcessioneDC())
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAggiudicazione());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
         if(!isDelega()) 
             schede.add(IdentificativoSchede.getConclusione());

         return TipoFlusso.AGGIUDICAZIONE;
      }
      // Ordinari Sopra 150k accordo quadro
      //TICKET ALM #11169 - 3.04.4
      if (!isSpeciale() && isAccordo() && !isSottoSoglia() 
//    		  && isSopraSoglia() MAC 3.04.6
    		  ){
//      if (!isSpeciale() && isAccordo()){   
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAggiudicazione());
         if(isDelegaStipula())
        	 schede.add(IdentificativoSchede.getStipula());
         if(!isDelega()) {
	         schede.add(IdentificativoSchede.getStipula());
	         schede.add(IdentificativoSchede.getConclusione());
	         schede.add(IdentificativoSchede.getSubAppalti());
         }
       //TICKET ALM #14626 - 3.04.5
         if(dataCreazione.compareTo(Costanti.DATA_DL50) >= 0)
	         schede.add(IdentificativoSchede.getVarianti()); 
         return TipoFlusso.STIPULA;
      }

      // Speciali Sopra 150k accordo quadro
      //TICKET ALM #11169 - 3.04.4
      if (isSpeciale() && isAccordo() && !isSottoSoglia() && isSopraSoglia()){
//      if (isSpeciale() && isAccordo()){
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAggiudicazione());
         
         if(!isDelega()) 
             schede.add(IdentificativoSchede.getConclusione());
         
       //TICKET ALM #14626 - 3.04.5
         if(dataCreazione.compareTo(Costanti.DATA_DL50) >= 0)
	         schede.add(IdentificativoSchede.getVarianti()); 
         
         return TipoFlusso.AGGIUDICAZIONE;
      }
         
      // Ordinari Sopra 150k adesione senza scc
//      3.04.8 34190 fix
      if (!isSpeciale() && (isAdesioneNoDC()|| isConcessioneNoDC()) 
    		  && !isSottoSoglia() 
    		  //&& isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAdesione());
         
         if(!isDelega()) {
	         schede.add(IdentificativoSchede.getInizioLavori());
	// PP escludo in visualizzazione         if(importo > Costanti.IMPORTO_LOTTO_500000 || importo == Costanti.IMPORTO_FUORI_SCALA)
	            schede.add(IdentificativoSchede.getAvanzamenti());
	         schede.add(IdentificativoSchede.getConclusione());
	         schede.add(IdentificativoSchede.getCollaudo());
	         schede.add(IdentificativoSchede.getRitardo());
	         schede.add(IdentificativoSchede.getAccordi());
	         schede.add(IdentificativoSchede.getSospensioni());
	         schede.add(IdentificativoSchede.getSubAppalti());
	         schede.add(IdentificativoSchede.getVarianti());
         }
         return TipoFlusso.ADESIONE;
      }
         
      // Speciali Sopra 150k adesione senza scc
//      3.04.8 34190 fix
      if (isSpeciale() && (isAdesioneNoDC() || isConcessioneNoDC())
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAdesione());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
         if(!isDelega()) 
            schede.add(IdentificativoSchede.getConclusione());

         return TipoFlusso.ADESIONE;
      }

      // Ordinari Sopra 150k 
      if (!isSpeciale() 
    		  && !isSottoSoglia() 
//    		  && isSopraSoglia()
    		  ){
       
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAggiudicazione());
         
         if(!isDelega()) {
	         schede.add(IdentificativoSchede.getInizioLavori());
	// PP escludo in visualizzazione         if(importo > Costanti.IMPORTO_LOTTO_500000 || importo == Costanti.IMPORTO_FUORI_SCALA)
	            schede.add(IdentificativoSchede.getAvanzamenti());
	         schede.add(IdentificativoSchede.getConclusione());
	         schede.add(IdentificativoSchede.getCollaudo());
	         schede.add(IdentificativoSchede.getRitardo());
	         schede.add(IdentificativoSchede.getAccordi());
	         schede.add(IdentificativoSchede.getSospensioni());
	         schede.add(IdentificativoSchede.getSubAppalti());
	         schede.add(IdentificativoSchede.getVarianti());
         }
         return TipoFlusso.AGGIUDICAZIONE;
      }

      // Speciali Sopra 150k 
      if (isSpeciale() 
    		  && !isSottoSoglia() && isSopraSoglia()
    		  ){
       
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAggiudicazione());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
         if(!isDelega()) 
            schede.add(IdentificativoSchede.getConclusione());
         
         return TipoFlusso.AGGIUDICAZIONE;
      }

      // Sotto 150k adesione senza scc
//      3.04.8 34190 fix
      if ((isAdesioneNoDC()|| isConcessioneNoDC()) && isSottoSoglia()){
         
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getAdesione());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
         return TipoFlusso.ADESIONE;
      }
         
      // Sotto 150k adesione con scc
//      3.04.8 34190 fix
      if ((isAdesioneDC() || isConcessioneDC()) && isSottoSoglia()){
      
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getSottosoglia());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
         return TipoFlusso.SOTTOSOGLIA;
      }
      
      // Sotto 150k accordo quadro
      if (isAccordo() && isSottoSoglia()){
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getSottosoglia());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
       //TICKET ALM #14626 - 3.04.5
         if(dataCreazione.compareTo(Costanti.DATA_DL50) >= 0)
	         schede.add(IdentificativoSchede.getVarianti()); 
         
         return TipoFlusso.SOTTOSOGLIA;
      }

      // Sotto 150k 
      if (isSottoSoglia()){
         schede.add(IdentificativoSchede.getDatiComuni());
         schede.add(IdentificativoSchede.getSottosoglia());
         schede.add(IdentificativoSchede.getInizioLavori()); //ALM 21296 - 3.04.5.2
         
         return TipoFlusso.SOTTOSOGLIA;
      }

      return null;
   }
   
   /** ritorna l'importo di riferimento per il limite inferio dei sottosoglia
    * @param tipoContratto
    * @param dataCreazione
    * @return importo
    */
   public static float getImportoRif(String tipoContratto, String dataCreazione) {
       boolean nuovaSotto = (Costanti.DATA_NUOVI_SOTTO.compareTo(dataCreazione)<= 0);
       
       return nuovaSotto 
               ? Costanti.IMPORTO_LOTTO_40000 
               : (Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto) 
                               ? Costanti.IMPORTO_LOTTO_40000 
                               : Costanti.IMPORTO_LOTTO_20000);
   }

   /** verifica se ci sono i presupposti per inserire schede aggiuntive , usato da web
    * @param importo
    * @param tipoContratto
    * @return
    */
   public static boolean checkOkSchede(double importo, String tipoContratto, String dataCreazione) {
       
       if(importo >= getImportoRif(tipoContratto, dataCreazione)
           || importo == Costanti.IMPORTO_FUORI_SCALA)     
           return true;
       else
           return false;
   }

   
   /***
    *  restituisce il codice per la logica JSP della pagina dati comuni
    * @return
    * 
    * is3027_SOGLIAActive questa funzione è obsoleta a partre da is30233_RFWEBSC00Active
    * e non viene più mantenuta
    */
//   3.04.8 34190 fix
  public static String getLogicaJsp(){
      
      String testo = "";
      String acapo = "\n";
      
      testo += "function getImportoRif(tipo, data){" + acapo; 
      testo += "   var nuovaSotto = ('" + Costanti.DATA_NUOVI_SOTTO + "' <= data);" + acapo; 
      testo += "   return nuovaSotto " + acapo; 
      testo += "           ? " + Costanti.IMPORTO_LOTTO_40000 +  acapo; 
      testo += "           : ('" + Costanti.TIPO_SCHEDA_LAVORI + "' == tipo " + acapo; 
      testo += "              ? " + Costanti.IMPORTO_LOTTO_40000 + " " + acapo; 
      testo += "              : " + Costanti.IMPORTO_LOTTO_20000 + ");" + acapo; 
      testo += "}"+ acapo;

      testo += "function getTipoFlusso(importoLotto, dataPub){" + acapo; 
      testo += "   var retVal = '.';" + acapo;     
      testo += "   var tipoContratto = document.getElementById('" + ParametriServlet.FIELD_NAME_TIPO_CONTRATTO + "').value;" + acapo; 
      testo += "   var modoReal = document.getElementById('" + ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE + "').value;" + acapo; 
      testo += "   var flagEscluso = document.getElementById('check5Y').checked;" + acapo; 
      testo += "   var importoRif = getImportoRif(tipoContratto, dataPub);" + acapo; 
     // testo += "   if(modoReal == '" + Costanti.MODOREAL_ACCORDO + "'){" + acapo; 
      //TICKET ALM #2847
      testo += "   if(modoReal == '" + Costanti.MODOREAL_ACCORDO + "' || modoReal == '"+Costanti.MODOREAL_ACCORDO_QUADRO+"' || modoReal == '"+Costanti.MODOREAL_CONVENZIONE+"'){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.STIPULA + "';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(modoReal == '" + Costanti.MODOREAL_ADESIONE + "'){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.ADESIONE +"';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(modoReal == '" + Costanti.MODOREAL_CONCESSIONE + "'){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.ADESIONE +"';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(modoReal == '" + Costanti.MODOREAL_ADESIONE_NOCOMPET + "'){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.ADESIONE + "';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(modoReal == '" + Costanti.MODOREAL_CONCESSIONE_NOCOMPET + "'){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.ADESIONE + "';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(flagEscluso == true && (importoLotto >= importoRif || importoLotto == " + Costanti.IMPORTO_FUORI_SCALA + ")){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.ESCLUSO +"';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(importoLotto <= " + Costanti.IMPORTO_LOTTO_150000 + " && importoLotto >= importoRif){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.SOTTOSOGLIA + "';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(importoLotto > " + Costanti.IMPORTO_LOTTO_150000 + " || importoLotto == " + Costanti.IMPORTO_FUORI_SCALA + "){" + acapo; 
      testo += "       retVal = '" + IdentificativoSchede.AGGIUDICAZIONE + "';" + acapo; 
      testo += "   }" + acapo; 
      testo += "   return retVal;" + acapo; 
      testo += "}" + acapo; 

      testo += "function getDescrizioneTipoFlusso(importo, data){" + acapo; 
      testo += "   var campo = document.getElementById('tipoFlusso');" + acapo;
      testo += "   var tipo = getTipoFlusso(importo, data);" + acapo; 
      testo += "   if(tipo == '" + IdentificativoSchede.STIPULA + "'){" + acapo; 
      testo += "       campo.innerHTML = 'CONTRATTI DI ACCORDO QUADRO / CONVENZIONE';" + acapo;   
      testo += "   }" + acapo; 
      testo += "   else if(tipo == '" + IdentificativoSchede.ADESIONE + "'){" + acapo; 
      testo += "       campo.innerHTML = 'CONTRATTI DI ADESIONE ACCORDO QUADRO / CONVENZIONE';" + acapo;  
      testo += "   }" + acapo; 
      testo += "else if(tipo == '" + IdentificativoSchede.ESCLUSO +"'){" + acapo; 
      testo += "       campo.innerHTML = 'CONTRATTI ESCLUSI DEL TUTTO O IN PARTE DA AMBITO DI APPLICAZIONE DEL CODICE';" + acapo;   
      testo += "   }" + acapo; 
      testo += "   else if(tipo == '" + IdentificativoSchede.SOTTOSOGLIA + "'){" + acapo; 
      testo += "       campo.innerHTML = 'CONTRATTI SOTTO LA SOGLIA DEI 150.000 EURO'; " + acapo; 
      testo += "   }" + acapo; 
      testo += "   else if(tipo == '" + IdentificativoSchede.AGGIUDICAZIONE + "'){" + acapo; 
      testo += "       var tipo = document.getElementById('"+ ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE +"').value;" + acapo; 
      testo += "       var buff = '';" + acapo; 
      testo += "       if (tipo == 'S')" + acapo; 
      testo += "           buff = ' IN SETTORI SPECIALI';" + acapo; 
      testo += "       else if (tipo == 'O')" + acapo; 
      testo += "           buff = ' IN SETTORI ORDINARI';" + acapo; 
      testo += "       campo.innerHTML = 'CONTRATTI SOPRA LA SOGLIA DEI 150.000 EURO' + buff;" + acapo;   
      testo += "   }" + acapo; 
      testo += "   else" + acapo; 
      testo += "       campo.innerHTML = '*** NON PREVISTA RILEVAZIONE PER LA COMBINAZIONE INDICATA ***';" + acapo; 
      testo += "}" + acapo; 
            
      return testo;
   }
   
   public String getDescrizioneTipoFlusso(){ 
   
      String retVal = "*** NON PREVISTA RILEVAZIONE PER LA COMBINAZIONE INDICATA ***"; 
      
      if (this.tipoFlusso == null)
         return retVal;
      
      switch (this.tipoFlusso ) {
      
      case STIPULA:
         retVal = "CONTRATTI DI ACCORDO QUADRO / CONVENZIONE";
         break;
   
      case ADESIONE:
         retVal = "CONTRATTI DI ADESIONE ACCORDO QUADRO / CONVENZIONE";
         break;
   
      case ESCLUSO:
    	  //TICKET ALM - 3.04.3
    	  //Se la gara e' successiva l'attivazione della 3.04.3, l'aggiudicazione deve essere quella ordinaria
    	  boolean res3043 = SimogProperties.getInstance().isDataCreatedAfter3043(this.dataCreazione);
    	  if(!SimogFlags.is3043Active() || !res3043)
              retVal = "CONTRATTI ESCLUSI DEL TUTTO O IN PARTE DA AMBITO DI APPLICAZIONE DEL CODICE";
    	  else 
    		  retVal = calcolaFlusso();
    	  
         break;
   
      case SOTTOSOGLIA:
         retVal = "CONTRATTI SOTTO LA SOGLIA DEI 150.000 EURO";
         break;
   
      case AGGIUDICAZIONE:
         retVal = calcolaFlusso();
         break;
   
      default:
         break;
      }   
         
      return retVal;
   }

   private String calcolaFlusso() {
	   String retVal="";
	   boolean isNuovaSoglia = RetroController.is3027_SOGLIA(this.dataPubb);
       String importo = PageHelper.formattaImporto(new BigDecimal(isNuovaSoglia ? Costanti.IMPORTO_LOTTO_40000 : Costanti.IMPORTO_LOTTO_150000));
       retVal = "CONTRATTI SOPRA LA SOGLIA DEI " + importo + " EURO IN SETTORI "
             + (Costanti.TIPO_ENTE_SPECIALE.equals(this.tipoSettore) ? "SPECIALI" : "ORDINARI");
       return retVal;
   }
   
   private boolean isEscluso(){
	   //TICKET ALM - 3.04.3 #7303
	   //Se la gara e' stata creata prima dell'attivazione di Simog 3.04.3, mantieni il controllo sul flusso escluso
	   if(!SimogProperties.getInstance().isDataCreatedAfter3043(this.dataCreazione))
           return Costanti.FLAG_VALORE_SI.equals(this.flagEscluso);
       else
        return false;
   }
   
   //TICKET ALM - 3.04.5 #13575
   private boolean isConcorsoProgettazioneIdee(){
	   return SimogProperties.getInstance().isDataCreatedAfter3045(this.dataCreazione) && this.modoReal==Costanti.ID_MODO_REAL_IDEE;
         
   }
   
   private boolean isAdesioneNoDC(){
      return Costanti.MODOREAL_ADESIONE_NOCOMPET == this.modoReal;
   }
   
   private boolean isAdesioneDC(){
      return Costanti.MODOREAL_ADESIONE == this.modoReal;
   }
   
//   3.04.8 34190 fix
   private boolean isConcessioneNoDC(){
	      return Costanti.MODOREAL_CONCESSIONE_NOCOMPET == this.modoReal;
	   }
	   
   private boolean isConcessioneDC(){
      return Costanti.MODOREAL_CONCESSIONE == this.modoReal;
   }

   private boolean isAccordo(){
	   return SimogFlags.isAccordoQuadroOrConvenzione(this.modoReal) || SimogFlags.isSvolgimentoAccordoQuadro(idSvolgimento);
   }

   protected boolean isSpeciale(){
	   
	   //TICKET ALM #11228 - 3.04.4
	   //Se la gara e' stata creata prima della data degli obblighi comunicativi per i settori speciali, mantieni il controllo precedente, altrimenti e' sempre falso
	   if(!SimogProperties.getInstance().isDataAfterObblighiComunicativiSpeciali(this.dataPubb))
           return Costanti.TIPO_ENTE_SPECIALE.equals(this.tipoSettore);
	   else
		   return false;
  }

   protected boolean isSottoSoglia(){

         boolean isNuovaSoglia = RetroController.is3027_SOGLIA(this.dataPubb);
         if(!isNuovaSoglia && this.importo <= Costanti.IMPORTO_LOTTO_150000 && this.importo >= getImportoRif(tipoContratto, dataPubb))
            return true;
         else
            return false; // con la nuova soglia il sottosoglia sparisce come fattispecie
 
   } 

   protected boolean isSopraSoglia(){

         boolean isNuovaSoglia = RetroController.is3027_SOGLIA(this.dataPubb);
         
         if(!isNuovaSoglia && (this.importo > Costanti.IMPORTO_LOTTO_150000 || this.importo == Costanti.IMPORTO_FUORI_SCALA))
            return true;
         else if(isNuovaSoglia && (this.importo >= Costanti.IMPORTO_LOTTO_40000 || this.importo == Costanti.IMPORTO_FUORI_SCALA))
            return true;
         else if(isNuovaSoglia && (this.importo > Costanti.IMPORTO_LOTTO_40000 || this.importo == Costanti.IMPORTO_FUORI_SCALA))
            return true;
         else
            return false;
  }

   public String getTipoSettore() {
      return tipoSettore;
   }

   public String getTipoContratto() {
      return tipoContratto;
   }

   public String getFlagEscluso() {
      return flagEscluso;
   }

   public int getModoReal() {
      return modoReal;
   }

   public String getDataPubb() {
      return dataPubb;
   }

   public float getImporto() {
      return importo;
   }

   public TipoFlusso getTipoFlusso() {
      return tipoFlusso;
   }

   public List<IdentificativoSchede> getSchede() {
      return schede;
   }
   
   public boolean isDelega() {
	   return delega!=0;
   }

   public boolean isDelegaStipula() {
	   return delega==Costanti.DELEGA2;
   }
   
	public int getIdSvolgimento() {
		return idSvolgimento;
	}
	
	public void setIdSvolgimento(int idSvolgimento) {
		this.idSvolgimento = idSvolgimento;
	}
   
   
}
