package github.mmusica.ppmtool.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(updatable = false)
    private String projectSequence;

    @Getter
    @Setter
    private String summary;

    @Getter
    @Setter
    private String acceptanceCriteria;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private Integer priority;

    @Getter
    @Setter
    private Date dueDate;

    @Getter
    private Date create_At;

    @Getter
    private Date update_At;

    @Getter
    @Setter
    @Column(updatable = false)
    private String projectIdentifier;

    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    public ProjectTask() {
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", create_At=" + create_At +
                ", update_At=" + update_At +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                '}';
    }
}
