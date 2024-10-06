package com.gym.crm.app.rest;

import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.rest.model.GetTraineeProfileResponse;
import com.gym.crm.app.rest.model.TraineeCreateRequest;
import com.gym.crm.app.rest.model.UpdateTraineeProfileRequest;
import com.gym.crm.app.rest.model.UpdateTraineeProfileResponse;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.validator.CreateTraineeValidator;
import com.gym.crm.app.validator.UpdateTraineeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/trainees")
@RequiredArgsConstructor
public class TraineeRestControllerV1 {

    private final ServiceFacade service;
    private final CreateTraineeValidator createValidator;
    private final UpdateTraineeValidator updateValidator;

    @InitBinder("traineeCreateRequest")
    public void initCreateValidatorBinder(WebDataBinder binder) {
        binder.addValidators(createValidator);
    }

    @InitBinder("updateTraineeProfileRequest")
    public void initUpdateValidatorBinder(WebDataBinder binder) {
        binder.addValidators(updateValidator);
    }

    @PostMapping("/register")
    public ResponseEntity<UserCredentials> traineeRegister(@RequestBody @Validated TraineeCreateRequest request,
                                                           BindingResult bindingResult) {
        UserCredentials profile = service.createTraineeProfile(request, bindingResult);

        return ResponseEntity.status(200).body(profile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetTraineeProfileResponse> getTraineeProfile(@PathVariable String username) {
        GetTraineeProfileResponse trainee = service.findTraineeProfileByUsername(username);

        return ResponseEntity.status(200).body(trainee);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UpdateTraineeProfileResponse> updateTraineeProfile(@PathVariable String username,
                                                                             @RequestBody @Validated UpdateTraineeProfileRequest request,
                                                                             BindingResult bindingResult) {
        UpdateTraineeProfileResponse profile = service.updateTraineeProfile(username, request, bindingResult);

        return ResponseEntity.status(200).body(profile);
    }
}
