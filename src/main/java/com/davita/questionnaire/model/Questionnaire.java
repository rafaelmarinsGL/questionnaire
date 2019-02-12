package com.davita.questionnaire.model;

import com.davita.questionnaire.enums.QuestionnaireStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @NotNull
    @ApiModelProperty(notes = "Person", dataType = "Person")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_id")
    @NotNull
    @ApiModelProperty(notes = "Form", dataType = "Form")
    private Form form;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionnaire")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Submission> submissions;

    public Questionnaire(QuestionnaireStatus status, @NotNull Person person, @NotNull Form form, List<Submission> submissions) {
        this.status = status;
        this.person = person;
        this.form = form;
        this.submissions = submissions;
    }
}
