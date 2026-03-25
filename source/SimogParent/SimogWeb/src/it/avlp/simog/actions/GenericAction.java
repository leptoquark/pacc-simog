package it.avlp.simog.actions;

import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServletRichAnnullamento;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class GenericAction extends BaseAction {

	public GenericAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		// TODO Auto-generated constructor stub
	}

	/*******************************************************************************************
	 * Ottiene la richiesta di annullamento
	 * @param request HttpServletRequest
	 * @return RichiestaAnnullamento
	 * @throws ActionException
	 */
	public  RichiestaAnnullamento getAnnullamentoBean(HttpServletRequest request) throws ActionException{
		RichiestaAnnullamento raBean = new RichiestaAnnullamento();
		
		raBean.setId_record(getStringReqParameter(request, null, ParametriServletRichAnnullamento.FIELD_NAME_ID_RECORD));
		raBean.setData_inizio_record(getTimestampReqParameter(request, null, ParametriServletRichAnnullamento.FIELD_NAME_DATA_INIZIO_RECORD));
		raBean.setId_richiesta(getLongReqParameter(request, -1, ParametriServletRichAnnullamento.FIELD_NAME_ID_RICHIESTA));
		raBean.setBlocco(getStringReqParameter(request, null, ParametriServletRichAnnullamento.FIELD_NAME_BLOCCO));
		raBean.setEsito(getStringReqParameter(request, null, ParametriServletRichAnnullamento.FIELD_NAME_ESITO));
		raBean.setMotivo_esito(getStringReqParameter(request, null, ParametriServletRichAnnullamento.FIELD_NAME_MOTIVO_ESITO));
		return raBean;
		
	}
}
