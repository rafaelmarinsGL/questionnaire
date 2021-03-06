package com.davita.questionnaire.controller;

import com.davita.questionnaire.enums.QuestionnaireStatus;
import com.davita.questionnaire.model.*;
import com.davita.questionnaire.service.QuestionnaireService;
import com.davita.questionnaire.service.SubmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RestController
@Api(value = "Questionnaire Endpoint", description = "CRUD operations for questionnaires")
@RequestMapping("/api/v1")
public class QuestionnaireController {

    @Autowired
    QuestionnaireService questionnaireService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    PersonController personController;

    @Autowired
    FormController formController;

    @GetMapping("/questionnaire")
    @ApiOperation(value = "Get all the questionnaires", notes = "Returns a list of all the questionnaires", response = Questionnaire.class, responseContainer = "List")
    ResponseEntity<?> getQuestionnaires() {
        return ResponseEntity.ok(questionnaireService.findAll());
    }

    @GetMapping("/questionnaire/{id}")
    @ApiOperation(value = "Get a questionnaire by id", notes = "Returns the questionnaire with the given id", response = Questionnaire.class)
    ResponseEntity<?> getQuestionnaire(@PathVariable Integer id) {
        return questionnaireService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/questionnaire")
    @ApiOperation(value = "Create a questionnaire", notes = "Creates a questionnaire and returns the new questionnaire", response = Questionnaire.class)
    ResponseEntity<?> postQuestionnaire(@Valid @RequestBody Questionnaire questionnaire){
        return ResponseEntity.ok(questionnaireService.save(questionnaire));
    }

    @DeleteMapping("/questionnaire/{id}")
    @ApiOperation(value = "Delete a questionnaire", notes = "Deletes the questionnaire with the given id")
    ResponseEntity<?> deleteQuestionnaire(@PathVariable Integer id) {
        return questionnaireService.findById(id)
                .map(q -> {
                    questionnaireService.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/questionnaire/{id}")
    @ApiOperation(value = "Update a questionnaire", notes = "Updates the questionnaire with the given id", response = Questionnaire.class)
    ResponseEntity<?> updateQuestionnaire(@PathVariable Integer id, @RequestBody Questionnaire questionnaire) {
        return questionnaireService.findById(id)
                .map(q -> {
                    questionnaire.setId(id);
                    return questionnaireService.save(questionnaire);
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/questionnaire/{id}/submit")
    @ApiOperation(value = "Create a submission")
    ResponseEntity<?> submitQuestionnaire(@PathVariable Integer id, @RequestBody Submission submission) {
        return questionnaireService.findById(id)
                .map(q -> {
                    q.setStatus(QuestionnaireStatus.WAITING_REVIEW);
                    return questionnaireService.save(q);
                })
                .map(q -> {
                    submission.setQuestionnaire(q);
                    submission.setForm(q.getForm());
                    return ResponseEntity.ok(submissionService.save(submission));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/questionnaire/{id}/submissions")
    @ApiOperation(value = "Get a questionnaire submissions")
    ResponseEntity<?> getQuestionnaireSubmissions(@PathVariable Integer id) {
        return questionnaireService.findById(id)
                .map(Questionnaire::getSubmissions)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
