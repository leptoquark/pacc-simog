/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.beans.ResponseMessageTED;

public interface DeltaLottoTedService {
	public ResponseMessageTED execute(String cig, String idStazioneAppaltante, String deltaLottoTed, Long noLot);
}