package github.mmusica.ppmtool.services;


import github.mmusica.ppmtool.domain.Backlog;
import github.mmusica.ppmtool.domain.Project;
import github.mmusica.ppmtool.exceptions.ProjectIdException;
import github.mmusica.ppmtool.exceptions.ProjectNotFoundException;
import github.mmusica.ppmtool.repositories.BacklogRepository;
import github.mmusica.ppmtool.repositories.ProjectRepository;
import github.mmusica.ppmtool.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        var userOpt = userRepository.findByUsername(username);
        try {
            if (project.getId() == null) {
                project.setUser(userOpt.get());
                project.setProjectLead(userOpt.get().getUsername());
                project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
                var backlog = new Backlog();
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
                project.setBacklog(backlog);
                backlog.setProject(project);
            } else {
                if (!project.getProjectLead().equals(userOpt.get().getUsername())) {
                    throw new ProjectNotFoundException("Project not found on this user account");
                }
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        } catch (ProjectNotFoundException ex) {
            throw new ProjectNotFoundException("Project not found on this user account");
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '%s' already exists".formatted(project.getProjectIdentifier().toUpperCase()));
        }
    }

    public Project findProjectByIdentifier(String identifier, String username) {


        Project project = projectRepository.findByprojectIdentifier(identifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '%s' doesn't exist".formatted(identifier.toUpperCase()));
        } else {
            if (!project.getProjectLead().equals(username))
                throw new ProjectNotFoundException("Project not found on this user account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLead(username);
    }

    public void deleteProjectByIdentifier(String identifier, String username) {
        Project project = projectRepository.findByprojectIdentifier(identifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID '%s' doesn't exist so it cannot be deleted".formatted
                    (identifier.toUpperCase()));
        } else {
            if (!project.getProjectLead().equals(username))
                throw new ProjectNotFoundException("Project not found on this user account");
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
