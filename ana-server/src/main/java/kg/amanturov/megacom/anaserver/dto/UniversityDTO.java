package kg.amanturov.megacom.anaserver.dto;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
@Setter
public class UniversityDTO {
    @NotEmpty
    @Size(min = 4, max = 50)
    @Pattern(regexp = "([А-Я][а-я]+|[А-Я]+|[а-я]+ [А-Я][а-я]+ [А-Я][а-я]+)", message = "Название университета должно быть такого рода записано: БГТУ, Горный, имени Амантурова Назара")
    private String title;
}
