package github.mmusica.ppmtool.web;

import github.mmusica.ppmtool.domain.Project;
import github.mmusica.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<Project> createNewProject(@RequestBody Project project) {

        var projectCreated = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(projectCreated, HttpStatus.CREATED);
    }
}
