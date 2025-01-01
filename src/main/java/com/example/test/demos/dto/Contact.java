package com.example.test.demos.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Contact {
    @SerializedName("_events")
    private Events events;
    
    @SerializedName("_eventsCount")
    private Integer eventsCount;
    
    private String id;
    private Payload payload;
}