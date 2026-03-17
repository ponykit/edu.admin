package com.edu.admin.model.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private int code;
    private String codeName;
    private String codeMessage;
    private String message;

    public ErrorMessage(ErrorCode code, String message) {
        this.code = code.getCode();
        this.codeName = code.name();
        this.codeMessage = code.getMsg();
        this.message = message;
    }
}