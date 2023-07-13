package github.mmusica.ppmtool.services;


import github.mmusica.ppmtool.domain.Project;
import github.mmusica.ppmtool.exceptions.ProjectIdException;
import github.mmusica.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '%s' already exists".formatted(project.getProjectIdentifier().toUpperCase()));
        }
    }

    public Project findProjectByIdentifier(String identifier) {

        Project project = projectRepository.findByprojectIdentifier(identifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '%s' doesn't exist".formatted(identifier.toUpperCase()));
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String identifier) {
        Project project = projectRepository.findByprojectIdentifier(identifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID '%s' doesn't exist so it cannot be deleted".formatted
                    (identifier.toUpperCase()));
        }
        projectRepository.delete(project);
    }

    public Project updateProjectByIdentifier(Project project) {
        var foundProject = projectRepository.findByprojectIdentifier(project.getProjectIdentifier().toUpperCase());
        if (foundProject == null) {
            throw new ProjectIdException("Project ID '%s' doesn't exist so it cannot be deleted".formatted
                    (project.getProjectIdentifier().toUpperCase()));
        }
        foundProject.setProjectName(project.getProjectName());
        foundProject.setDescription(project.getDescription());
        foundProject.setStart_date(project.getStart_date());
        foundProject.setEnd_date(project.getEnd_date());
        projectRepository.save(foundProject);
        return foundProject;
    }
}
