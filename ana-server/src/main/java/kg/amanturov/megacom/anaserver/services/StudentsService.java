package kg.amanturov.megacom.anaserver.services;

import kg.amanturov.megacom.anaserver.models.StudentModel;
import kg.amanturov.megacom.anaserver.repositories.StudentsRepository;
import kg.amanturov.megacom.anaserver.utils.StudentNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentsService {
    private final StudentsRepository studentsRepository;
    private final UniversitiesService universitiesService;

    public StudentsService(StudentsRepository studentsRepository,
                           UniversitiesService universitiesService) {
        this.studentsRepository = studentsRepository;
        this.universitiesService = universitiesService;
    }

    public List<StudentModel> getStudents(Integer page, Integer sizePage){
        if (page == null || sizePage == null)
            return studentsRepository.findAll();
        else
            return studentsRepository.findAll(PageRequest.of(page, sizePage)).getContent();
    }

    public StudentModel getStudent(int id){
        return studentsRepository.findById(id).stream().findAny().orElseThrow(StudentNotFoundException::new);
    }

    @Transactional
    public void registrationToUniversity(StudentModel studentModel){
        enrichStudent(studentModel);
        studentsRepository.save(studentModel);
    }

    public List<StudentModel> search(String fullName){
        return studentsRepository.findByFullNameIgnoreCase(fullName);
    }

    private void enrichStudent(StudentModel studentModel) {
        studentModel.setUniversity(universitiesService.
                trueOrFalseUniversity(studentModel.getUniversity().getTitle()).get());
    }
}
