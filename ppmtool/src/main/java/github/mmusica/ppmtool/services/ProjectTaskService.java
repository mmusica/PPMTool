package github.mmusica.ppmtool.services;

import github.mmusica.ppmtool.domain.Backlog;
import github.mmusica.ppmtool.domain.ProjectTask;
import github.mmusica.ppmtool.exceptions.ProjectNotFoundException;
import github.mmusica.ppmtool.repositories.BacklogRepository;
import github.mmusica.ppmtool.repositories.ProjectRepository;
import github.mmusica.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    private final BacklogRepository backlogRepository;
    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectTaskService(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
    }


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            //PT to be added to a specific project, project !=NULL which means BL exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

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
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project with id: %s not found".formatted(projectIdentifier));
        }
    }


    public Iterable<ProjectTask> findBacklog(String backlogId) {
        if (projectRepository.findByprojectIdentifier(backlogId) == null) {
            throw new ProjectNotFoundException("Project with id %s not found".formatted(backlogId).toUpperCase());
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }
}
