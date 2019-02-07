package com.davita.questionnaire.controller;

import com.davita.questionnaire.model.Questionnaire;
import com.davita.questionnaire.service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Questionnaire Endpoint", description = "CRUD operations for questionnaires")
@RequestMapping("/api/v1")
public class QuestionnaireController {

    @Autowired
    QuestionnaireService questionnaireService;

    @GetMapping("/questionnaire")
    @ApiOperation(value = "Get all the questionnaires")
    ResponseEntity<?> getQuestionnaires() {
        return ResponseEntity.ok(questionnaireService.findAll());
    }

    @GetMapping("/questionnaire/{id}")
    @ApiOperation(value = "Get a questionnaire by id")
    ResponseEntity<?> getQuestionnaire(@PathVariable Integer id) {
        return questionnaireService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/questionnaire")
    @ApiOperation(value = "Create a questionnaire")
    ResponseEntity<?> postQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return ResponseEntity.ok(questionnaireService.save(questionnaire));
    }

    @DeleteMapping("/questionnaire/{id}")
    @ApiOperation(value = "Delete a questionnaire by id")
    ResponseEntity<?> deleteQuestionnaire(@PathVariable Integer id) {
        return questionnaireService.findById(id)
                .map(q -> {
                    questionnaireService.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/questionnaire/{id}")
    @ApiOperation(value = "Update a questionnaire")
    ResponseEntity<?> updateQuestionnaire(@PathVariable Integer id, @RequestBody Questionnaire questionnaire) {
        return questionnaireService.findById(id)
                .map(q -> {
                    questionnaire.setId(id);
                    return questionnaireService.save(questionnaire);
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
