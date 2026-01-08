package de.codedbygruba.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResult<T> {
    public ApiResult(int responseCode, T responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    private int responseCode;
    private T responseBody;
}
