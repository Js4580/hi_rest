package kg.amanturov.megacom.anaserver.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "university")
@Getter
@Setter
public class UniversityModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    @NotEmpty
    @Size(min = 4, max = 50)
    @Pattern(regexp = "([А-Я][а-я]+|[А-Я]+|[а-я]+ [А-Я][а-я]+ [А-Я][а-я]+)", message = "Название университета должно быть такого рода записано: БГТУ, Горный, имени Амантурова Назара")
    private String title;
}
