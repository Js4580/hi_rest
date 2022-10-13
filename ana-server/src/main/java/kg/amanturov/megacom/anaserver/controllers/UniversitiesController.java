package kg.amanturov.megacom.anaserver.controllers;

import kg.amanturov.megacom.anaserver.dto.UniversityDTO;
import kg.amanturov.megacom.anaserver.models.UniversityModel;
import kg.amanturov.megacom.anaserver.services.UniversitiesService;
import kg.amanturov.megacom.anaserver.utils.UniversityBadBuildException;
import kg.amanturov.megacom.anaserver.utils.UniversityExceptionResponse;
import kg.amanturov.megacom.anaserver.utils.UniversityValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversitiesController {
    private final UniversitiesService universitiesService;
    private final ModelMapper modelMapper;
    private final UniversityValidator universityValidator;

    @Autowired
    public UniversitiesController(UniversitiesService universitiesService,
                                  ModelMapper modelMapper, UniversityValidator universityValidator) {
        this.universitiesService = universitiesService;
        this.modelMapper = modelMapper;
        this.universityValidator = universityValidator;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> buildUniversity(@RequestBody @Valid UniversityDTO universityDTO,
                                                      BindingResult bindingResult){
        UniversityModel universityModel = convertToUniversityModel(universityDTO);
        universityValidator.validate(universityModel, bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder stringErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error:
                 errors)
                stringErrors.append("поле: ").append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append(";");
            throw new UniversityBadBuildException(stringErrors.toString());
        }
        universitiesService.createUniversity(universityModel);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<UniversityExceptionResponse> handleBadBuildException(UniversityBadBuildException e){
        UniversityExceptionResponse university = new UniversityExceptionResponse(
                e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(university, HttpStatus.BAD_REQUEST);
    }

    private UniversityModel convertToUniversityModel(UniversityDTO universityDTO) {
        return modelMapper.map(universityDTO, UniversityModel.class);
    }
}
