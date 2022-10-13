package kg.amanturov.megacom.anaserver.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
@Getter
@Setter
public class StudentModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @Size(min = 5, max = 50)
    @NotEmpty
    private String fullName;
    @Column(name = "age")
    @Min(18)
    @Max(25)
    private int age;
    @ManyToOne
    @JoinColumn(name = "university", referencedColumnName = "title")
    @NotNull
    private UniversityModel university;
}
