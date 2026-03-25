package it.almawave.serviceline.ToBeNamed.service;

import it.almawave.serviceline.ToBeNamed.dto.CupRequest;
import it.almawave.serviceline.ToBeNamed.dto.CupResponse;
import org.springframework.http.ResponseEntity;

public interface CupEntityService {

    ResponseEntity<CupResponse> getCup(CupRequest cupRequest);

    ResponseEntity<CupResponse> getCupPostMock(CupRequest cupRequest);

    ResponseEntity<CupResponse> getCupGetMock();

}
