package com.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class BfhlRequest {

    @NotNull(message = "'data' must be provided and must be an array")
    @JsonProperty("data")
    private List<Object> data;

    public List<Object> getData() {
        return data;
    }
    public void setData(List<Object> data) {
        this.data = data;
    }
}