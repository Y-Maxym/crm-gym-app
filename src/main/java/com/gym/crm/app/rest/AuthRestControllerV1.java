package com.gym.crm.app.rest;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.rest.model.ChangePasswordRequest;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.validator.ChangePasswordValidator;
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
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthRestControllerV1 {

    private final ServiceFacade service;
    private final ChangePasswordValidator changePasswordValidator;

    @InitBinder("changePasswordRequest")
    public void initChangePasswordValidatorBinder(WebDataBinder binder) {
        binder.addValidators(changePasswordValidator);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        User user = service.authenticate(credentials);

        return ResponseEntity.status(200).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Validated ChangePasswordRequest request,
                                            BindingResult bindingResult) {
        service.changePassword(request, bindingResult);

        return ResponseEntity.status(200).build();
    }
}
