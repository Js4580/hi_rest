package kg.amanturov.megacom.anaserver.repositories;

import kg.amanturov.megacom.anaserver.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentsRepository extends JpaRepository<StudentModel, Integer> {
    List<StudentModel> findByFullNameIgnoreCase(String name);
}
