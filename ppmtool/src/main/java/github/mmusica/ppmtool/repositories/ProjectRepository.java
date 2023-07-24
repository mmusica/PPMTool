package github.mmusica.ppmtool.repositories;

import github.mmusica.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByprojectIdentifier(String projectIdentifier);

    Iterable<Project> findAllByProjectLead(String username);
}
