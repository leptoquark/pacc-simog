package it.avlp.simog.rettifica;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.avlp.simog.beans.InfoRettificaBean;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.util.PageHelper;

public class InfoRettifica {


	//Ticket #20055
	public boolean checkRettifica (GaraManager gm,String idGara) throws SQLException, Exception {

		boolean result = false;

		InfoRettificaBean infoRettifica = gm.getInfoRettifica(idGara);

		if(infoRettifica== null) {
			return false;
		}

		String currentDate = PageHelper.getViewDate(PageHelper.getCurrentDate());
		String dataScadenzaPagamenti = PageHelper.getViewDate(infoRettifica.getDataScadenzaPagamenti());
		String dataScadenzaInvito = PageHelper.getViewDate(infoRettifica.getDataScadenzaInvito());

		Date currentDateFormatter = new SimpleDateFormat("dd/MM/yyyy").parse(currentDate); 
		Date DataScadenzaPagamentiFormatter = null;
		if(dataScadenzaPagamenti!=null)
			DataScadenzaPagamentiFormatter = new SimpleDateFormat("dd/MM/yyy").parse(dataScadenzaPagamenti);
		else
			DataScadenzaPagamentiFormatter = new SimpleDateFormat("dd/MM/yyy").parse(dataScadenzaInvito);

		if(currentDateFormatter.before(DataScadenzaPagamentiFormatter)) {

			result = true;
		} else if (currentDateFormatter.equals(DataScadenzaPagamentiFormatter)) {

			result =  true;
		}else {

			result = false;
		}

		if(result) {	 

			if(!infoRettifica.getFlagSospeso().equals("S")) {
				result = false;
			}
		}

		return result;
	}
	
	public InfoRettificaBean getInfoRettifica(GaraManager gm,String idGara) throws SQLException, Exception {
		
		InfoRettificaBean infoRettifica = gm.getInfoRettifica(idGara);
		return infoRettifica;
	}

}
