package kg.amanturov.megacom.anaserver.dto;

import kg.amanturov.megacom.anaserver.models.UniversityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

@Getter
@Setter
public class StudentDTO {
    @NotEmpty
    private String fullName;

    @Min(18)
    @Max(25)
    private int age;

    @NotNull
    private UniversityDTO university;
}
