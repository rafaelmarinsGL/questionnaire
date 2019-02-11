package com.davita.questionnaire.model;

import com.davita.questionnaire.enums.QuestionnaireStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"person_id", "form_id"})
})
@ApiModel( value = "Questionnaire", description = "Person / Form / Submission relation")
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Autogenerated id", dataType = "Integer")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Questionnaire status", dataType = "String")
    private QuestionnaireStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    public Questionnaire(Integer id, QuestionnaireStatus status, Person person, Form form) {
        this.id = id;
        this.status = status;
        this.person = person;
        this.form = form;
    }

    public Questionnaire() {
    }

}
