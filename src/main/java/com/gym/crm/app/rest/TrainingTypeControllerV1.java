package com.gym.crm.app.rest;

import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.rest.model.GetTrainingTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/training-types")
@RequiredArgsConstructor
public class TrainingTypeControllerV1 {

    private final ServiceFacade service;

    @GetMapping
    public ResponseEntity<List<GetTrainingTypeResponse>> getAllTrainingTypes() {
        List<GetTrainingTypeResponse> trainingTypes = service.getTrainingTypes();

        return ResponseEntity.status(HttpStatus.OK).body(trainingTypes);
    }
}
