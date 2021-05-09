package com.myexaminer.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("HIDDEN")
    HIDDEN,
    @JsonProperty("OPEN")
    OPEN,
    @JsonProperty("CLOSED")
    CLOSED,
    @JsonProperty("DRAFT")
    DRAFT,
    @JsonProperty("CHECKED")
    CHECKED;
}