package com.texoit.movie.domain;


import com.texoit.movie.enums.SeverityEnum;

/**
 * @author Lucas Darú Miguel
 * @linkedin https://www.linkedin.com/in/lucas-daru-miguel-642901233/
 */
public interface BaseExceptionDetails {

    String getMessage();

    default String getTitle() {
        return "Erro";
    }

    default String getInternalErrorCode() {
        return null;
    }

    default SeverityEnum getSeverity(){
        return SeverityEnum.DANGER;
    }

}
