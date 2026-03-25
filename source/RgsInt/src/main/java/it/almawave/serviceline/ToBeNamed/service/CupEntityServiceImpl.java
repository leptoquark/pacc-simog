package it.almawave.serviceline.ToBeNamed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almawave.serviceline.ToBeNamed.dto.CupItem;
import it.almawave.serviceline.ToBeNamed.dto.CupRequest;
import it.almawave.serviceline.ToBeNamed.dto.CupResponse;
import it.almawave.serviceline.ToBeNamed.entity.CupEntity;
import it.almawave.serviceline.ToBeNamed.entity.ElencoCup;
import it.almawave.serviceline.ToBeNamed.repository.CupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CupEntityServiceImpl implements CupEntityService {

    @Autowired
    private CupRepository repository;

    @Override
    public ResponseEntity<CupResponse> getCup(CupRequest cupRequest) {
        List<CupItem> items = new ArrayList<>();

        cupRequest.getCupList().forEach(cupId -> {
            ElencoCup elencoCup = repository.findByElencoCUP(cupId).orElse(null);


            if(elencoCup != null) {
                List<CupEntity> cupEntities = elencoCup.getElencoCUP();

                cupEntities.forEach(cupEntity -> {
                    String codiceInvestimento = cupEntity != null ? cupEntity.getDettaglio().getCodiceInvestimento() : null;
                    String tipoPiano = cupEntity != null ? cupEntity.getDettaglio().getTipoPiano() : null;

                    Integer error = cupEntity != null ? 0 : 1;

                    CupItem item = new CupItem(cupId, codiceInvestimento, tipoPiano, error);
                    items.add(item);
                });
            } else {
                CupItem item = new CupItem(cupId, null, null, 1);
                items.add(item);
            }

        });

        return ResponseEntity.ok(new CupResponse(items));
    }

//    @Value("classpath:mock/cup_mock.json")
//    Resource resourceFile;

    @Override
    public ResponseEntity<CupResponse> getCupPostMock(CupRequest cupRequest) {
        ObjectMapper mapper = new ObjectMapper();
        List<CupItem> items = new ArrayList<>();

        cupRequest.getCupList().forEach(s -> {
            try {
                String cup = String.format("{\"codiceCUP\":\"%s\",\"dettaglio\":{\"codiceInvestimento\":\"PNC-B.1.1\",\"descrizioneInvestimento\":\"Citt\\u00e0 e paesi sicuri, sostenibili e connessi (A)\",\"descrizioneNorma\":\"Descrizione Norma 2\",\"tipoPiano\":\"PNC\",\"importoFinanziato\":123456788.11,\"datascarico\":\"1651570243\",\"ts\":{\"$date\":\"2022-05-26T14:23:07.504Z\"}},\"storico\":[{\"codiceInvestimento\":\"PNC-B.1.1\",\"descrizioneInvestimento\":\"Citt\\u00e0 e paesi sicuri, sostenibili e connessi (A)\",\"descrizioneNorma\":\"Descrizione Norma 2\",\"tipoPiano\":\"PNC\",\"importoFinanziato\":123456788.11,\"datascarico\":\"1651570243\",\"ts\":{\"$date\":\"2022-05-26T14:23:07.504Z\"}},{\"codiceInvestimento\":\"PNC-B.1.1\",\"descrizioneInvestimento\":\"Citt\\u00e0 e paesi sicuri, sostenibili e connessi (A)\",\"descrizioneNorma\":\"Descrizione Norma 1\",\"tipoPiano\":\"PNC\",\"importoFinanziato\":12345.11,\"datascarico\":\"1651570232\",\"ts\":{\"$date\":\"2022-01-26T12:22:02.224Z\"}}]}", s);
                CupEntity cupEntity = mapper.readValue(cup, CupEntity.class);

                String cupId = cupEntity.getCodiceCUP();
                String codiceInvestimento = cupEntity.getDettaglio().getCodiceInvestimento();
                String tipoPiano = cupEntity.getDettaglio().getTipoPiano();
//                J77H20003350004
                Integer error = s.equalsIgnoreCase("000000000000000") ? 1 : 0;

                items.add(new CupItem(cupId, codiceInvestimento, tipoPiano, error));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        return ResponseEntity.ok(new CupResponse(items));
    }

    @Override
    public ResponseEntity<CupResponse> getCupGetMock() {
        ObjectMapper mapper = new ObjectMapper();
        List<CupItem> items = new ArrayList<>();

        try {
            CupEntity cupEntity = mapper.readValue("{\"codiceCUP\":\"Z79H11000890007\",\"dettaglio\":{\"codiceInvestimento\":\"PNC-B.1.1\",\"descrizioneInvestimento\":\"Citt\\u00e0 e paesi sicuri, sostenibili e connessi (A)\",\"descrizioneNorma\":\"Descrizione Norma 2\",\"tipoPiano\":\"PNC\",\"importoFinanziato\":123456788.11,\"datascarico\":\"1651570243\",\"ts\":{\"$date\":\"2022-05-26T14:23:07.504Z\"}},\"storico\":[{\"codiceInvestimento\":\"PNC-B.1.1\",\"descrizioneInvestimento\":\"Citt\\u00e0 e paesi sicuri, sostenibili e connessi (A)\",\"descrizioneNorma\":\"Descrizione Norma 2\",\"tipoPiano\":\"PNC\",\"importoFinanziato\":123456788.11,\"datascarico\":\"1651570243\",\"ts\":{\"$date\":\"2022-05-26T14:23:07.504Z\"}},{\"codiceInvestimento\":\"PNC-B.1.1\",\"descrizioneInvestimento\":\"Citt\\u00e0 e paesi sicuri, sostenibili e connessi (A)\",\"descrizioneNorma\":\"Descrizione Norma 1\",\"tipoPiano\":\"PNC\",\"importoFinanziato\":12345.11,\"datascarico\":\"1651570232\",\"ts\":{\"$date\":\"2022-01-26T12:22:02.224Z\"}}]}", CupEntity.class);

            String cupId = cupEntity.getCodiceCUP();
            String codiceInvestimento = cupEntity.getDettaglio().getCodiceInvestimento();
            String tipoPiano = cupEntity.getDettaglio().getTipoPiano();
            Integer error = 1;

            items.add(new CupItem(cupId, codiceInvestimento, tipoPiano, error));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(new CupResponse(items));
    }
}
