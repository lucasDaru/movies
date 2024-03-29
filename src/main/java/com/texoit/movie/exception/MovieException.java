package com.texoit.movie.exception;

import com.texoit.movie.domain.BaseExceptionDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Darú Miguel
 * @linkedin https://www.linkedin.com/in/lucas-daru-miguel-642901233/
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class MovieException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    @AllArgsConstructor
    public enum Message implements BaseExceptionDetails {
        FILE_NOT_FOUND("File not found to import."),
        TYPE_OF_FILE("File type not supported to import."),
        EMPTY_FILE("Could not import file is empty."),
        FILE_TOO_LARGE("Could not import file is biggest than expected."),
        ERROR_READ_FILE("Could not read the file.");

        private String message;
    }

    public MovieException(Message message, Object... params) {
        super(message, params);
    }
}
