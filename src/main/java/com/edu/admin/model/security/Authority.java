package com.edu.admin.model.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Authority {
    private Integer roleNo;
    private String roleId;
}