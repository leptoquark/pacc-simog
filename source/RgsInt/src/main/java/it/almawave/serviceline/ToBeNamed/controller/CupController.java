package it.almawave.serviceline.ToBeNamed.controller;

import it.almawave.serviceline.ToBeNamed.dto.CupRequest;
import it.almawave.serviceline.ToBeNamed.dto.CupResponse;
import it.almawave.serviceline.ToBeNamed.service.CupEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cup")
public class CupController {

    @Autowired
    private CupEntityService service;

    @PostMapping
    public ResponseEntity<CupResponse> getParameters(@RequestBody CupRequest cupRequest) {
        return service.getCup(cupRequest);
    }

    @PostMapping("/mock")
    public ResponseEntity<CupResponse> getParametersPostMock(@RequestBody CupRequest cupRequest) {
        return service.getCupPostMock(cupRequest);
    }

    @GetMapping("/mock")
    public ResponseEntity<CupResponse> getParametersGetMock() {
        return service.getCupGetMock();
    }

}
