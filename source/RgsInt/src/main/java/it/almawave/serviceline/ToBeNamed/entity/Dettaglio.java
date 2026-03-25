package it.almawave.serviceline.ToBeNamed.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dettaglio {

    @JsonProperty("codiceInvestimento")
    private String codiceInvestimento;

    @JsonProperty("descrizioneInvestimento")
    private String descrizioneInvestimento;

    @JsonProperty("descrizioneNorma")
    private String descrizioneNorma;

    @JsonProperty("tipoPiano")
    private String tipoPiano;

    @JsonProperty("importoFinanziato")
    private Double importoFinanziato;

    @JsonProperty("datascarico")
    private String datascarico;

    @JsonProperty("ts")
    private Timestamp timestamp;
}
