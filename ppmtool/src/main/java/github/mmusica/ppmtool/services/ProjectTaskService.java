package github.mmusica.ppmtool.services;

import github.mmusica.ppmtool.domain.ProjectTask;
import github.mmusica.ppmtool.repositories.BacklogRepository;
import github.mmusica.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    private final BacklogRepository backlogRepository;
    private final ProjectTaskRepository projectTaskRepository;

    @Autowired
    public ProjectTaskService(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
    }


    public ProjectTask addProjectTask(ProjectTask projectTask) {

        //PT to be added to a specific project, project !=NULL which means BL exists
        //set backlog to PT
        //we want project sequence to be like this: IDPRO-1, IDPRO-2
        //we want to update the BL sequence

        //INITIAL priority when priority null
        //INITIAL status when status is null


        return null;
    }
}
