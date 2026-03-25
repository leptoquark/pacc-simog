package it.almawave.serviceline.ToBeNamed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CupItem {

    private String cupId;

    private String codiceInvestimento;

    private String tipoPiano;

    private Integer error;

//    //TODO ADD LIST OF CUPENTITY OR LIST OF TWO REQUESTED FIELDS
//    private List<CupEntity> cupEntityList;

}
