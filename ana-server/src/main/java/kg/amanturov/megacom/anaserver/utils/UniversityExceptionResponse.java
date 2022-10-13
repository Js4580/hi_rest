package kg.amanturov.megacom.anaserver.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UniversityExceptionResponse {
    private String message;
    private Date timestamp;
}
