package com.gym.crm.app.rest;

import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.rest.model.TrainerCreateRequest;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.validator.CreateTrainerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/trainers")
@RequiredArgsConstructor
public class TrainerRestControllerV1 {

    private final ServiceFacade service;
    private final CreateTrainerValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @PostMapping("/register")
    public ResponseEntity<UserCredentials> trainerRegister(@RequestBody @Validated TrainerCreateRequest request,
                                                           BindingResult bindingResult) {
        UserCredentials profile = service.createTrainerProfile(request, bindingResult);

        return ResponseEntity.status(200).body(profile);
    }
}
