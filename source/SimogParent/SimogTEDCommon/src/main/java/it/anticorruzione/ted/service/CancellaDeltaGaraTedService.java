/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.beans.ResponseMessageTED;

public interface CancellaDeltaGaraTedService {
	public ResponseMessageTED execute(Long idGara, String idStazioneAppaltante);
}