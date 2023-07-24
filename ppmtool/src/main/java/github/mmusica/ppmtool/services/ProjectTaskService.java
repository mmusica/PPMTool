package github.mmusica.ppmtool.services;

import github.mmusica.ppmtool.domain.Backlog;
import github.mmusica.ppmtool.domain.ProjectTask;
import github.mmusica.ppmtool.exceptions.ProjectIdException;
import github.mmusica.ppmtool.exceptions.ProjectNotFoundException;
import github.mmusica.ppmtool.repositories.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        try {
            //PT to be added to a specific project, project !=NULL which means BL exists
            var foundProject = projectService.findProjectByIdentifier(projectIdentifier, username);
            Backlog backlog = foundProject.getBacklog();

            //set backlog to PT
            projectTask.setBacklog(backlog);
            //we want project sequence to be like this: IDPRO-1, IDPRO-2
            backlog.setPTSequence(backlog.getPTSequence() + 1);
            Integer backlogSeq = backlog.getPTSequence();
            projectTask.setProjectSequence("%s-%d".formatted(projectIdentifier, backlogSeq));
            projectTask.setProjectIdentifier(projectIdentifier);
            //INITIAL priority when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            //INITIAL status when status is null
            if (projectTask.getStatus() == null || projectTask.getStatus().isEmpty()) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        } catch (ProjectNotFoundException ex) {
            throw new ProjectNotFoundException("Project not found on this user account");
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '%s' already exists".formatted(projectIdentifier.toUpperCase()));
        }
    }


    public Iterable<ProjectTask> findBacklog(String backlogId, String username) {
        //backlog id is the same as project id
        if (projectService.findProjectByIdentifier(backlogId, username) == null) {
            throw new ProjectNotFoundException("Project with id %s not found".formatted(backlogId));
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }

    public ProjectTask findPTbyProjectSequence(String backlog_id, String pt_id, String username) {

        var backlog = projectService.findProjectByIdentifier(backlog_id, username);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project with id %s not found".formatted(backlog_id));
        }
        var pt = projectTaskRepository.findByProjectSequence(pt_id);
        if (pt == null) {
            throw new ProjectNotFoundException("Project Task with id %s not found".formatted(pt_id));
        }
        if (!pt.getBacklog().getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task with id %s doesn't exist in Project: %s".formatted(pt_id, backlog_id));
        }

        return pt;
    }


    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        var projectTask = findPTbyProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteByProjectSequence(String backlog_id, String pt_id, String username) {

        var projectTask = findPTbyProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);

    }
}
