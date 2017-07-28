package gov.samhsa.c2s.masteruiapi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason ="Cannot get access token" )
public class AccessTokenNotPresentException extends RuntimeException {
    public AccessTokenNotPresentException() {
    }

    public AccessTokenNotPresentException(String message) {
        super(message);
    }

    public AccessTokenNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenNotPresentException(Throwable cause) {
        super(cause);
    }

    public AccessTokenNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
