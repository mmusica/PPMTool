package github.mmusica.ppmtool.web;

import github.mmusica.ppmtool.domain.Project;
import github.mmusica.ppmtool.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errorMessage = new HashMap<>();
            result.getFieldErrors().
                    forEach(fieldError -> errorMessage.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        var projectCreated = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(projectCreated, HttpStatus.CREATED);
    }
}
