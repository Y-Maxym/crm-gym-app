package com.gym.crm.app.rest;

import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.rest.model.ActivateDeactivateProfileRequest;
import com.gym.crm.app.rest.model.GetTrainerProfileResponse;
import com.gym.crm.app.rest.model.TrainerCreateRequest;
import com.gym.crm.app.rest.model.TrainerProfileWithUsername;
import com.gym.crm.app.rest.model.UpdateTrainerProfileRequest;
import com.gym.crm.app.rest.model.UpdateTrainerProfileResponse;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.validator.CreateTrainerValidator;
import com.gym.crm.app.validator.UpdateTrainerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainers")
@RequiredArgsConstructor
public class TrainerRestControllerV1 {

    private final ServiceFacade service;
    private final CreateTrainerValidator createTrainerValidator;
    private final UpdateTrainerValidator updateTrainerValidator;

    @InitBinder("trainerCreateRequest")
    public void initCreateValidatorBinder(WebDataBinder binder) {
        binder.addValidators(createTrainerValidator);
    }

    @InitBinder("updateTrainerProfileRequest")
    public void initUpdateValidatorBinder(WebDataBinder binder) {
        binder.addValidators(updateTrainerValidator);
    }

    @PostMapping("/register")
    public ResponseEntity<UserCredentials> trainerRegister(@RequestBody @Validated TrainerCreateRequest request,
                                                           BindingResult bindingResult) {
        UserCredentials profile = service.createTrainerProfile(request, bindingResult);

        return ResponseEntity.status(200).body(profile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetTrainerProfileResponse> getTrainerProfile(@PathVariable String username) {
        GetTrainerProfileResponse trainer = service.findTrainerProfileByUsername(username);

        return ResponseEntity.status(200).body(trainer);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UpdateTrainerProfileResponse> updateTrainerProfile(@PathVariable String username,
                                                                             @RequestBody @Validated UpdateTrainerProfileRequest request,
                                                                             BindingResult bindingResult) {
        UpdateTrainerProfileResponse profile = service.updateTrainerProfile(username, request, bindingResult);

        return ResponseEntity.status(200).body(profile);
    }

    @GetMapping("/not-assigned/{username}")
    public ResponseEntity<List<TrainerProfileWithUsername>> getTrainersNotAssigned(@PathVariable String username) {
        List<TrainerProfileWithUsername> trainers = service.getTrainersNotAssignedByTraineeUsername(username);

        return ResponseEntity.status(200).body(trainers);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<?> activateTrainerProfile(@PathVariable String username,
                                                    @RequestBody ActivateDeactivateProfileRequest request) {
        if (request.getIsActive()) {
            service.activateProfile(username);
        } else {
            service.deactivateProfile(username);
        }

        return ResponseEntity.status(200).build();
    }
}
