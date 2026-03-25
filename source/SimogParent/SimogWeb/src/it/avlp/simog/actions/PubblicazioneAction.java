package it.avlp.simog.actions;

import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class PubblicazioneAction extends BaseAction {

	public PubblicazioneAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
	public PubblicazioneBean getBean(HttpServletRequest request)throws ActionException{
		PubblicazioneBean pubBean = new PubblicazioneBean();
		pubBean.setDataAlbo(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_ALBO_PRETORIO));
		pubBean.setDataGuce(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE));
		pubBean.setDataGuri(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI));
		pubBean.setProfiloCommitente(request.getParameter(ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE));
		pubBean.setSitoOsservatorioCP(request.getParameter(ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP));
		pubBean.setSitoMinisteroInfTrasp(request.getParameter(ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP));
		
		pubBean.setQuotidianiNaz(getIntReqParameter(request, 0, ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI));
		pubBean.setQuotidianiReg(getIntReqParameter(request, 0, ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI));
		pubBean.setIdPubblicazione(getLongReqParameter(request, -1, ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE));
		pubBean.setDataInizioPubblicazione(getTimestampReqParameter(request, null, ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB));
		
		pubBean.setDataBore(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_BOLLETTINO_REGIONALE));
		pubBean.setPeriodici(getIntReqParameter(request, 0, ParametriServlet.FIELD_NAME_PERIODICI));
		
		// gm nuovo codice pubblicazione bando 3.0
		pubBean.setNumeroGuce(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_NUMERO_GUCE));
		pubBean.setNumeroGuri(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_NUMERO_GURI));
		pubBean.setNumeroBore(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_NUMERO_BORE));
		pubBean.setLinkSitoCommittente(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_LINK_SITO_COMMITTENTE));
		// gm fine nuovo codice pubblicazione bando 3.0
		
		//gm nuovo codice estensione pubblicazione bandi
		pubBean.setFlag_benicult(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_BENICULT));
		
		pubBean.setFlag_sospeso(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SOSPESO));
		
		//MARRA MEV 34470 3.04.8
		pubBean.setLinkAffidamentoDiretto(getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO));
		//FINE MEV
		
		//3.04.10 MEV 43345
		pubBean.setDerogaQualificazioneSA(request.getParameter(PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA));
		if (request.getParameter(ParametriServlet.FLAG_IS_KO) != null
				&& !"".equals(request.getParameter(ParametriServlet.FLAG_IS_KO))) {
			pubBean.setFlagIsQualificataKO(request.getParameter(ParametriServlet.FLAG_IS_KO));
		}
		return pubBean;
	}
	
	
   public List<CupLottoAggExt> getListaCupLottoByRequest(HttpServletRequest request)
   {
      List<CupLottoAggExt> listaCUP = new LinkedList<CupLottoAggExt>();
      for(int idx = 0; ; idx++)
      {
         String prefix = "hiddenrowCUP";
         String pv_cig = getStringReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_CIG);
         
         if(pv_cig == null) break;
         
         String pv_cup = getStringReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_CUP);
         Long pv_id_lotto = getLongReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_ID_LOTTO);
         Long pv_id_agg = getLongReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_ID_AGG);
         Timestamp pv_data_agg = getTimestampReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_DATA_INIZIO_AGG);
         String pv_valido = getStringReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_VALIDO);

         // solo CUP validati DIPE
         if (Costanti.FLAG_VALORE_SI.equals(pv_valido)){
            CupLottoAggExt item = new CupLottoAggExt();
            item.setCig( pv_cig );
            item.setCup(pv_cup);
            item.setIdLotto( pv_id_lotto );
            item.setIdAggiudicazione( pv_id_agg);
            item.setDataInizioAgg( pv_data_agg );
            item.setOkUtente( Costanti.FLAG_VALORE_SI );
            listaCUP.add( item );
         }
      }
      return listaCUP;
   }

}
