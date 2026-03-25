package it.almawave.serviceline.ToBeNamed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupRequest {

    @JsonProperty("elencoCup")
    private List<String> cupList;

}
