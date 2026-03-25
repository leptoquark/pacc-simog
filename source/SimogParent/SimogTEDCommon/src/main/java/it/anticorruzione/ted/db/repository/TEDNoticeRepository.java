package it.anticorruzione.ted.db.repository;

import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.enums.TypeNoticeEnum;

public interface TEDNoticeRepository {

	public TEDNotice findByNoDocExt(String noDocExt);
	
	public TEDNotice findByNoDocOjs(String noDocOjs,TypeNoticeEnum typeNotice, long idLotto, long idGara);

	public TEDNotice findByTypeAndIdGara(TypeNoticeEnum type, long idgara);
	public TEDNotice findByTypeAndIdLotto(TypeNoticeEnum type, long idlotto);
	
	public boolean insertTEDNotice(TEDNotice notice);
	
	public TEDNotice find(Long idTEDNotice);

	boolean updateTEDNotice(TEDNotice notice);
	
}
