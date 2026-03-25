package it.almawave.serviceline.ToBeNamed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CupResponse {

    @JsonProperty
    private List<CupItem> items;

}
