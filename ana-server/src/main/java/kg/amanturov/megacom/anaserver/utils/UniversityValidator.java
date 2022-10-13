package kg.amanturov.megacom.anaserver.utils;

import kg.amanturov.megacom.anaserver.models.UniversityModel;
import kg.amanturov.megacom.anaserver.services.UniversitiesService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UniversityValidator implements Validator {
    private final UniversitiesService universitiesService;

    public UniversityValidator(UniversitiesService universitiesService) {
        this.universitiesService = universitiesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UniversityModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UniversityModel universityModel = (UniversityModel) target;
        if (universitiesService.trueOrFalseUniversity(universityModel.getTitle()).isPresent())
            errors.rejectValue("title","","Такой университет с таким именем уже есть в нашем городке");
    }
}
