package github.mmusica.ppmtool.web;

import github.mmusica.ppmtool.domain.ProjectTask;
import github.mmusica.ppmtool.services.MapValidationErrorService;
import github.mmusica.ppmtool.services.ProjectTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private final ProjectTaskService projectTaskService;

    private final MapValidationErrorService mapValidationErrorService;

    @Autowired
    public BacklogController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id) {

        ResponseEntity<?> errorMap = mapValidationErrorService.getFieldNameErrorMap(result);
        if (errorMap != null) {
            return errorMap;
        }
        ProjectTask PTtoBeAdded = projectTaskService.addProjectTask(backlog_id, projectTask);
        return new ResponseEntity<>(PTtoBeAdded, HttpStatus.CREATED);
    }
}
