package github.mmusica.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ProjectNotFoundExceptionResponse {
    @Getter
    @Setter
    String projectNotFound;

    public ProjectNotFoundExceptionResponse(String errorMessage) {
        this.projectNotFound = errorMessage;
    }
}
