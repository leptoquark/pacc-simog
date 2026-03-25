package it.anticorruzione.ted.service;

import java.util.Date;
import java.util.List;

import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.db.entity.TEDSubmit;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;

public interface ITEDDbService {

	//Delta
	public DeltaGaraTED getDeltaGara(long idgara);
	public DeltaLottoTED getDeltaLotto(String cig);
	public List<TedDelta> getListaDeltaLotto(long idgara);
	
	//TED_NO_DOC_EXT
	public String createNoDocExt();
	
	
	
	public String getLotNo(String cig);
	
	//TEDNotice
	public TEDNotice findNoticeByNoDocExt(String noDocExt);
	public TEDNotice findNoticeById(Long idTedNotice);
	public TEDNotice findNoticeByNoDocOjsByLotto(String noDocOjs,TypeNoticeEnum typeNotice, long idLotto);
	public TEDNotice findNoticeByNoDocOjsByGara(String noDocOjs,TypeNoticeEnum typeNotice, long idGara);
	public TEDNotice findByTypeAndIdGara(TypeNoticeEnum type, long idgara);
	public TEDNotice findByTypeAndIdLotto(TypeNoticeEnum type, long idlotto);
	public TEDNotice insertTEDNotice(String noDocExt, Long idgara, Long idlotto, TypeNoticeEnum typeNotice);
	public boolean updateTEDNotice(TEDNotice notice);
	
	//TEDSubmit
	public boolean insertSubmit(String xmlNotice, Long idTedNotice);
	public TEDSubmit findSubmitByIdNotice(Long idTedNotice);
	
	//TEDStatus
	/**
	 * Recupera l'ultimo stato noto del formulario richiesto
	 * @param idTedNotice l'id del formulario
	 * @return lo stato del formulario
	 */
	public TEDStatus getLastTEDStatus(Long idTedNotice);
	public boolean insertTEDStatus(TEDNoticeInformation status, Long idNotice);
	public boolean updateTEDStatus(TEDStatus lastStatus);
	public boolean cancelPublication(String noDocExt, String motivazione);
	
	//Gara
	public Gara getGaraByIdAndSA(Long idGara, String idStazioneAppaltante);
	
	//Lotto
	public List<Lotto> getListaLotti(Long idGara);
	public Lotto findByCigAndIdStazioneAppaltante(String cig, String idStazioneAppaltante);
	
	//Pubblicazioni
	public boolean updatePubbTed(Long idGara, String dataPubbTed, String noOjs);
}
