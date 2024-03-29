package com.texoit.movie.exception;

import com.texoit.movie.domain.BaseExceptionDetails;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

/**
 * @author Lucas Darú Miguel
 * @linkedin https://www.linkedin.com/in/lucas-daru-miguel-642901233/
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class BaseRuntimeException extends RuntimeException {

    @Getter
    private BaseExceptionDetails data;

    private Object[] parameters;

    public BaseRuntimeException() {
        super();
    }

    protected BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(BaseExceptionDetails message,
                                Object... params) {
        super(message.getMessage());
        this.parameters = params;
        this.data = message;
    }

    public BaseRuntimeException(BaseExceptionDetails message,
                                Throwable cause,
                                Object... params) {
        super(message.getMessage(), cause);
        this.parameters = params;
        this.data = message;
    }

    public BaseRuntimeException(BaseExceptionDetails message,
                                Throwable cause) {
        super(message.getMessage(), cause);
        this.data = message;
    }

    public BaseRuntimeException(Exception ex) {
        super(ex.getMessage(), ex);
    }

    public String getMessage() {
        String message = super.getMessage();
        return parameters == null ? message : MessageFormat.format(message, parameters);
    }

}
