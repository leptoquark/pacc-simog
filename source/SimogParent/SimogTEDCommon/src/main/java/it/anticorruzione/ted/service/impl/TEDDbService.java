package it.anticorruzione.ted.service.impl;

import java.util.Date;
import java.util.List;

import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.entity.Pubblicazioni;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.db.entity.TEDSubmit;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.GaraRepository;
import it.anticorruzione.ted.db.repository.LottoRepository;
import it.anticorruzione.ted.db.repository.PubblicazioniRepository;
import it.anticorruzione.ted.db.repository.TEDNoDocExtRepository;
import it.anticorruzione.ted.db.repository.TEDNoticeRepository;
import it.anticorruzione.ted.db.repository.TEDStatusRepository;
import it.anticorruzione.ted.db.repository.TEDSubmitRepository;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.GaraRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.LottoRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.PubblicazioniRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TEDNoDocExtRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TEDNoticeRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TEDStatusRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TEDSubmitRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.anticorruzione.ted.service.ITEDDbService;
import it.anticorruzione.ted.util.MarshallerTED;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;

public class TEDDbService implements ITEDDbService {

	private TEDNoDocExtRepository tedNoDocExtRepository = new TEDNoDocExtRepositoryImpl();
	private TEDNoticeRepository tedNoticeRepository = new TEDNoticeRepositoryImpl();
	private TEDStatusRepository tedStatusRepository = new TEDStatusRepositoryImpl();
	private TEDSubmitRepository tedSubmitRepository = new TEDSubmitRepositoryImpl();
	private GaraRepository garaRepository = new GaraRepositoryImpl();
	private LottoRepository lottoRepository = new LottoRepositoryImpl();
	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();
	private PubblicazioniRepository pubbRepository = new PubblicazioniRepositoryImpl();
	
	@Override
	public String createNoDocExt() {
		return tedNoDocExtRepository.createNoDocExt(UtilityClass.getCurrentYear());
	}

	@Override
	public TEDNotice insertTEDNotice(String noDocExt, Long idgara, Long idlotto, TypeNoticeEnum typeNotice) {
		TEDNotice notice = new TEDNotice();
		notice.setIdGara(idgara);
		notice.setIdLotto(idlotto);
		notice.setNoDocExt(noDocExt);
		notice.setIdTedTypeNotice(typeNotice.getIdTipo());
		boolean success = tedNoticeRepository.insertTEDNotice(notice);
		if(success)
			return notice;
		return null;
		
	}


	@Override
	public TEDStatus getLastTEDStatus(Long idTedNotice) {
			return tedStatusRepository.getLastNoticeStatus(idTedNotice);

	}

	/**
	 * Salva lo stato di un notice
	 */
	@Override
	public boolean insertTEDStatus(TEDNoticeInformation status, Long idTedNotice) {

			TEDStatus tedStatus = new TEDStatus();
			tedStatus.setIdTedNotice(idTedNotice);
			tedStatus.setJsonResponse(status.toString());
			tedStatus.setIdTedTypeStatus(StatusNoticeEnum.findStatusByStr(status.getStatus()).getIdStato());
			tedStatus.setUpdateData(new Date());
			return tedStatusRepository.saveTEDStatus(tedStatus);

	}

	@Override
	public boolean cancelPublication(String noDocExt, String motivazione) {
		TEDNotice notice = tedNoticeRepository.findByNoDocExt(noDocExt);
		if(notice!=null) {
			TEDStatus tedStatus = new TEDStatus();
			tedStatus.setIdTedNotice(notice.getIdTedNotice());
			tedStatus.setJsonResponse(motivazione);
			tedStatus.setIdTedTypeStatus(StatusNoticeEnum.NOT_PUBLISHED.getIdStato());
			tedStatus.setUpdateData(new Date());
			tedStatus.setCancelByUser(true);
			return tedStatusRepository.saveTEDStatus(tedStatus);
		}
		
		return false;
		
	}

	@Override
	public TEDNotice findByTypeAndIdGara(TypeNoticeEnum type, long idgara) {
		return tedNoticeRepository.findByTypeAndIdGara(type,idgara);
	}
	
	@Override
	public TEDNotice findByTypeAndIdLotto(TypeNoticeEnum type, long idlotto) {
		return tedNoticeRepository.findByTypeAndIdLotto(type, idlotto);
	}

	@Override
	public boolean insertSubmit(String xmlNotice, Long idTedNotice) {
		TEDSubmit submit = new TEDSubmit();
		submit.setXmlRequest(xmlNotice);
		submit.setIdTedNotice(idTedNotice);
		submit.setDataRequest(new Date());
		return tedSubmitRepository.insertSubmit(submit);
		
	}

	@Override
	public TEDNotice findNoticeByNoDocOjsByLotto(String noDocOjs,TypeNoticeEnum typeNotice, long idLotto) {
		return tedNoticeRepository.findByNoDocOjs(noDocOjs,typeNotice, idLotto,0);
	}

	@Override
	public TEDNotice findNoticeByNoDocOjsByGara(String noDocOjs,TypeNoticeEnum typeNotice, long idGara) {
		return tedNoticeRepository.findByNoDocOjs(noDocOjs,typeNotice, 0,idGara);
	}

	@Override
	public String getLotNo(String cig) {
		Lotto lotto = lottoRepository.findByCigAndIdStazioneAppaltante(cig, null);
		return lotto != null ? "1" : null;
	}

	@Override
	public DeltaGaraTED getDeltaGara(long idgara) {

		
//		CreateMockup mockup = new CreateMockup();
//		
//		
//		return mockup.createDeltaGaraTED().getDeltaGara();
		TedDelta tedDeltaDb = tedDeltaRepository.getDeltaGaraValido(idgara);
		if(tedDeltaDb!=null)
			return MarshallerTED.unmarshalDeltaGaraTED(tedDeltaDb.getXmlDelta());
		
		return null;
	}

	@Override
	public DeltaLottoTED getDeltaLotto(String cig) {
		
//		CreateMockup mockup = new CreateMockup();
//		return mockup.createDeltaLotto().getDeltaLotto();
		TedDelta tedDeltaDb = tedDeltaRepository.getDeltaLottoValidoByCIG(cig);
		if(tedDeltaDb!=null)
			return MarshallerTED.unmarshalDeltaLottoTED(tedDeltaDb.getXmlDelta());
		
		return null;
	}

	@Override
	public List<TedDelta> getListaDeltaLotto(long idgara) {
		return tedDeltaRepository.getListaDetaLottoByIdGara(idgara);
	}

	@Override
	public boolean updateTEDStatus(TEDStatus lastStatus) {
		lastStatus.setUpdateData(new Date());
		return tedStatusRepository.saveTEDStatus(lastStatus);
		
	}

	@Override
	public TEDNotice findNoticeByNoDocExt(String noDocExt) {
		return tedNoticeRepository.findByNoDocExt(noDocExt);
	}

	@Override
	public boolean updateTEDNotice(TEDNotice notice) {
		return tedNoticeRepository.updateTEDNotice(notice);
	}

	@Override
	public TEDNotice findNoticeById(Long idTedNotice) {
		return tedNoticeRepository.find(idTedNotice);
	}

	@Override
	public TEDSubmit findSubmitByIdNotice(Long idTedNotice) {
		return tedSubmitRepository.getLastSubmit(idTedNotice);
	}

	@Override
	public Gara getGaraByIdAndSA(Long idGara, String idStazioneAppaltante) {
		return garaRepository.findByIdGaraAndIdStazioneAppaltante(idGara, idStazioneAppaltante);
	}

	@Override
	public List<Lotto> getListaLotti(Long idGara) {
		return lottoRepository.getByIdGara(idGara);
	}

	@Override
	public Lotto findByCigAndIdStazioneAppaltante(String cig, String idStazioneAppaltante) {
		return lottoRepository.findByCigAndIdStazioneAppaltante(cig, idStazioneAppaltante);
	}

	@Override
	public boolean updatePubbTed(Long idGara, String dataPubbTed, String noOjs) {
		Gara gara = garaRepository.findByIdGaraAndIdStazioneAppaltante(idGara, null);
		Pubblicazioni pubb = pubbRepository.find(gara.getIdPubblicazione(), gara.getDataInizioPubb());
		pubb.setDataGuce(dataPubbTed);
		pubb.setNumeroGuce(noOjs);
		
		return pubbRepository.merge(pubb);
	}

	

	
	
	
}
