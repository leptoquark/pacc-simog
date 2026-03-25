package it.avlp.simog.beans;

import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;

import java.util.ArrayList;
import java.util.List;

public class EsitoControlloStatiSchede extends EsitoOperazioneBean {

	private List<SchedaSpecificaValidationBean> listOfValidationBeans = new ArrayList<SchedaSpecificaValidationBean>();

	/**
	 * @return
	 */
	public List<SchedaSpecificaValidationBean> getListOfValidationBeans() {
		return listOfValidationBeans;
	}

	/**
	 * @param listOfValidationBeans
	 */
	public void setListOfValidationBeans(List<SchedaSpecificaValidationBean> listOfValidationBeans) {
		this.listOfValidationBeans = listOfValidationBeans;
	}
	/**
	 * @param validationBean
	 */
	public void addListOfValidationBeans(SchedaSpecificaValidationBean validationBean) {
		this.listOfValidationBeans = super.addElements(listOfValidationBeans, validationBean);
	}
	/**
	 * @param listOfValidationBeans
	 */
	public void addListOfValidationBeans(List<SchedaSpecificaValidationBean> listOfValidationBeans) {
		if(this.listOfValidationBeans == null) this.listOfValidationBeans = new ArrayList<SchedaSpecificaValidationBean>();
		this.listOfValidationBeans.addAll(listOfValidationBeans);
	}
	
	/**
	 * @param message
	 * @param nomeScheda
	 * @param statoScheda
	 * @return
	 */
	public static SchedaSpecificaValidationBean getValidationBean(String message, String nomeScheda, StatoScheda statoScheda){
		try{
			return new SchedaSpecificaValidationBean(message, ValidationBean.VALBEAN_SEV_ERR, nomeScheda, statoScheda.getCig(), statoScheda.getCui(),
					statoScheda.getIdRecordAsString(), statoScheda.getIdLocale());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @param nomeScheda
	 * @param statoScheda
	 * @return
	 */
	public static SchedaSpecificaValidationBean getValidationBeanInRichAnn(String nomeScheda, StatoScheda statoScheda){
		String message = Messaggi.SIMOG_MASSLOADER_194.replace("$1", Costanti.IN_RICHIESTA_ANNULLAMENTO);
		return getValidationBean(message, nomeScheda, statoScheda);
	}
	/**
	 * @param nomeScheda
	 * @param statoScheda
	 * @return
	 */
	public static SchedaSpecificaValidationBean getValidationBeanInCanc(String nomeScheda, StatoScheda statoScheda){
		String message = Messaggi.SIMOG_MASSLOADER_194.replace("$1", Costanti.IN_CANCELLAZIONE);
		return getValidationBean(message, nomeScheda, statoScheda);
	}
	/**
	 * @param nomeScheda
	 * @param statoScheda
	 * @return
	 */
	public static SchedaSpecificaValidationBean getValidationBeanInDefinizione(String nomeScheda, StatoScheda statoScheda){
		String message = Messaggi.SIMOG_MASSLOADER_194.replace("$1", Costanti.IN_DEFINIZIONE);
		return getValidationBean(message, nomeScheda, statoScheda);
	}
	
}
