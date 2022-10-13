package kg.amanturov.megacom.anaserver.utils;

import kg.amanturov.megacom.anaserver.models.StudentModel;
import kg.amanturov.megacom.anaserver.services.UniversitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StudentValidator implements Validator {
    private final UniversitiesService universitiesService;

    @Autowired
    public StudentValidator(UniversitiesService universitiesService) {
        this.universitiesService = universitiesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentModel studentModel = (StudentModel) target;
        if (studentModel.getUniversity() == null)
            return;
        if (universitiesService.trueOrFalseUniversity(studentModel.getUniversity().getTitle()).isEmpty())
            errors.rejectValue("university", "","Такого университета нет. По крайней мере ещё не посторили в нашем городке");
    }
}
