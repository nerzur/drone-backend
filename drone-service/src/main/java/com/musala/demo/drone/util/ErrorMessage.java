package com.musala.demo.drone.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorMessage {

    private String code;
    private List<Map<String, String>> messages;
}