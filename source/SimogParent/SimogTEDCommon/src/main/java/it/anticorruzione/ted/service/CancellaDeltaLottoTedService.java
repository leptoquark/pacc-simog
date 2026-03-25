/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.beans.ResponseMessageTED;

public interface CancellaDeltaLottoTedService {
	public ResponseMessageTED execute(String cig, String idStazioneAppaltante);
}