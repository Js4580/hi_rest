package kg.amanturov.megacom.anaserver.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentSearchDTO {
    @NotEmpty
    private String fullName;
}
