package kg.amanturov.megacom.anaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudentSearchResponse {
    private List<StudentDTO> yourSearchResult;
}
