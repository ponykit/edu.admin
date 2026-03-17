package com.edu.admin.exception;

import com.edu.admin.model.common.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

public class ApiHttpStatusCodeException extends Throwable {
    protected HttpStatusCodeException orgException;

    @Getter
    protected ApiError error;

    private ObjectMapper mapper = new ObjectMapper();

    public ApiHttpStatusCodeException(HttpStatusCodeException e) throws IOException {
        this.orgException = e;

        parseApiError();
    }

    private void parseApiError() throws IOException {
        this.error = mapper.readValue(orgException.getResponseBodyAsString(), ApiError.class);
    }
}
