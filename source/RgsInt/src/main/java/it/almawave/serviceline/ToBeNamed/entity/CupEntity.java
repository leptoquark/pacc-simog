package it.almawave.serviceline.ToBeNamed.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CupEntity {

    @JsonProperty("codiceCUP")
    private String codiceCUP;

    @JsonProperty("dettaglio")
    private Dettaglio dettaglio;

    @JsonProperty("storico")
    private List<Dettaglio> storicoList;

}
