package nl.minfin.eindopdracht.repositories;

import nl.minfin.eindopdracht.objects.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
