package com.texoit.movie.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lucas Darú Miguel
 * @linkedin https://www.linkedin.com/in/lucas-daru-miguel-642901233/
 */
@Getter
@AllArgsConstructor
public enum SeverityEnum {
    SUCCESS("success"),
    WARNING("warning"),
    DANGER("danger");

    private String label;

}
