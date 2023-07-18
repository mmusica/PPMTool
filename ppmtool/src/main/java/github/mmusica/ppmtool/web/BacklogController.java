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

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
        return projectTaskService.findBacklog(backlog_id.toUpperCase());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        var foundPT = projectTaskService.findPTbyProjectSequence(backlog_id.toUpperCase(), pt_id.toUpperCase());
        return new ResponseEntity<>(foundPT, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                               BindingResult result,
                                               @PathVariable String backlog_id,
                                               @PathVariable String pt_id
    ) {

        ResponseEntity<?> errorMap = mapValidationErrorService.getFieldNameErrorMap(result);
        if (errorMap != null) {
            return errorMap;
        }
        var updatedProjectTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id.toUpperCase(), pt_id.toUpperCase());
        return new ResponseEntity<>(updatedProjectTask, HttpStatus.OK);
    }


    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        projectTaskService.deleteByProjectSequence(backlog_id.toUpperCase(), pt_id.toUpperCase());
        return new ResponseEntity<String>("Project task %s was deleted successfully".formatted(pt_id.toUpperCase()), HttpStatus.OK);
    }
}
