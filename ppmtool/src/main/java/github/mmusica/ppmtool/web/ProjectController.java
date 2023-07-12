package github.mmusica.ppmtool.web;

import github.mmusica.ppmtool.domain.Project;
import github.mmusica.ppmtool.services.MapValidationErrorService;
import github.mmusica.ppmtool.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final MapValidationErrorService mapValidationErrorService;

    @Autowired
    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        var errorMap = mapValidationErrorService.getFieldNameErrorMap(result);
        if (errorMap != null) return errorMap;
        var projectCreated = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(projectCreated, HttpStatus.CREATED);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

        var project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        var projects = projectService.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
