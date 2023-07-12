package github.mmusica.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "Project name cannot be blank")
    private String projectName;

    @Getter
    @Setter
    @NotBlank(message = "Project Identifier cannot be blank")
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @Getter
    @Setter
    @NotBlank(message = "Project description cannot be blank")
    private String description;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date start_date;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date end_date;

    @Getter
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;

    @Getter
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }
}
