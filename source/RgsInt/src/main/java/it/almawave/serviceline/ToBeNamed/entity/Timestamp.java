package it.almawave.serviceline.ToBeNamed.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Timestamp {

    @JsonProperty("$date")
    private Date date;

}
