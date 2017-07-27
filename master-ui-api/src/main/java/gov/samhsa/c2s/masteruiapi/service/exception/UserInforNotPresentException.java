package gov.samhsa.c2s.masteruiapi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason ="Invalid access scope for the given role." )
public class UserInforNotPresentException extends RuntimeException {
    public UserInforNotPresentException() {
    }

    public UserInforNotPresentException(String message) {
        super(message);
    }

    public UserInforNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInforNotPresentException(Throwable cause) {
        super(cause);
    }

    public UserInforNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
