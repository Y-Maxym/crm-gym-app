package com.gym.crm.app.rest;

import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.rest.model.GetTrainingTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/training-types")
@RequiredArgsConstructor
public class TrainingTypeRestControllerV1 {

    private final ServiceFacade service;

    @GetMapping
    public ResponseEntity<List<GetTrainingTypeResponse>> getAllTrainingTypes() {
        List<GetTrainingTypeResponse> trainingTypes = service.getTrainingTypes();

        return ResponseEntity.status(200).body(trainingTypes);
    }
}
