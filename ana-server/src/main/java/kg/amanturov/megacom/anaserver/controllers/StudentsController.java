package kg.amanturov.megacom.anaserver.controllers;

import kg.amanturov.megacom.anaserver.dto.*;
import kg.amanturov.megacom.anaserver.models.StudentModel;
import kg.amanturov.megacom.anaserver.services.StudentsService;
import kg.amanturov.megacom.anaserver.utils.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    private final StudentsService studentsService;
    private final ModelMapper modelMapper;
    private final StudentValidator studentValidator;

    @Autowired
    public StudentsController(StudentsService studentsService,
                              ModelMapper modelMapper, StudentValidator studentValidator) {
        this.studentsService = studentsService;
        this.modelMapper = modelMapper;
        this.studentValidator = studentValidator;
    }

    @GetMapping()
    public StudentResponse allStudents(@RequestParam( required = false) Integer nazar,
                                       @RequestParam( required = false) Integer sizePage){
        return new StudentResponse(studentsService.getStudents(nazar, sizePage).stream().map(this::convertToStudentDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public StudentDTO oneStudent(@PathVariable("id")int id){
        return convertToStudentDTO(studentsService.getStudent(id));
    }

    @PostMapping("/search")
    public StudentSearchResponse searchStudentByFullName(@RequestBody @Valid StudentSearchDTO studentSearchDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder stringErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors)
                stringErrors.append("поле: ").append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage());
            throw new StudentBadSearchException(stringErrors.toString());
        }
        String  search = studentSearchDTO.getFullName();
        return new StudentSearchResponse(studentsService.search(search).stream().map(this::convertToStudentDTO).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid StudentDTO studentDTO, BindingResult bindingResult){
        StudentModel studentModel = convertToStudentModel(studentDTO);
        studentValidator.validate(studentModel, bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder stringErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors)
                stringErrors.append("поле: ").append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage());
            throw new StudentBadCreateException(stringErrors.toString());
        }
        studentsService.registrationToUniversity(studentModel);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<StudentExceptionResponse> handleNotFoundException(StudentNotFoundException e){
        StudentExceptionResponse student = new StudentExceptionResponse(
                "Такого студента нет",
                new Date()
        );
        return new ResponseEntity<>(student, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<StudentExceptionResponse> handleBadSearchException(StudentBadSearchException e){
        StudentExceptionResponse student = new StudentExceptionResponse(
                e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(student, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<StudentExceptionResponse> handleBadCreateException(StudentBadCreateException e){
        StudentExceptionResponse student = new StudentExceptionResponse(
                e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(student, HttpStatus.BAD_REQUEST);
    }
    private StudentModel convertToStudentModel(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, StudentModel.class);
    }

    private StudentDTO convertToStudentDTO(StudentModel studentModel) {
        return modelMapper.map(studentModel, StudentDTO.class);
    }
}
