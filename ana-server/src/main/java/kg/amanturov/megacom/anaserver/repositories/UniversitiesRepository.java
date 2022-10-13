package kg.amanturov.megacom.anaserver.repositories;

import kg.amanturov.megacom.anaserver.models.StudentModel;
import kg.amanturov.megacom.anaserver.models.UniversityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversitiesRepository extends JpaRepository<UniversityModel, Integer> {
    Optional<UniversityModel> findByTitle(String title);
}