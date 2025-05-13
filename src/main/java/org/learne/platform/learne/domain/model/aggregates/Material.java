package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.learne.platform.learne.domain.model.commands.Material.CreateMaterialCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
public class Material extends AuditableAbstractAggregateRoot<Material> {

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String format;

    @Column(nullable = false)
    private String link;

    public Material() {
    }

    public Material(CreateMaterialCommand command){
        this.course=new Course(command.courseId());
        this.title = command.title();
        this.format = command.format();
        this.link = command.link();

    }

}
