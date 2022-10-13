package kg.amanturov.megacom.anaserver.services;

import kg.amanturov.megacom.anaserver.models.UniversityModel;
import kg.amanturov.megacom.anaserver.repositories.UniversitiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UniversitiesService {
    private final UniversitiesRepository universitiesRepository;

    public UniversitiesService(UniversitiesRepository universitiesRepository) {
        this.universitiesRepository = universitiesRepository;
    }

    @Transactional
    public void createUniversity(UniversityModel universityModel){
        universitiesRepository.save(universityModel);
    }

    public Optional<UniversityModel> trueOrFalseUniversity(String title){
        return universitiesRepository.findByTitle(title);
    }

}
