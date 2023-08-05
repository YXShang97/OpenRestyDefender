package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

@JsonTypeInfo(use = DEDUCTION)
@JsonSubTypes({@JsonSubTypes.Type(IPFrequencyRule.class), @JsonSubTypes.Type(GeoIPRule.class), @JsonSubTypes.Type(AndRule.class), @JsonSubTypes.Type(OrRule.class)})
public interface Rule {
    boolean check(HttpRequest httpRequest);
}
