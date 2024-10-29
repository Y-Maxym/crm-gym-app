package com.gym.crm.app.rest.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.facade.validator.ActivateDeactivateProfileValidator;
import com.gym.crm.app.facade.validator.ChangePasswordValidator;
import com.gym.crm.app.facade.validator.UserCredentialsValidator;
import com.gym.crm.app.rest.AuthController;
import com.gym.crm.app.rest.model.ActivateDeactivateProfileRequest;
import com.gym.crm.app.rest.model.ChangePasswordRequest;
import com.gym.crm.app.rest.model.UserCredentials;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gym.crm.app.rest.AuthUtil.getAuthenticatedUser;

@RestController
@RequestMapping("${api.base-path}")
@RequiredArgsConstructor
public class AuthControllerV1 implements AuthController {

    private final ServiceFacade service;
    private final ChangePasswordValidator changePasswordValidator;
    private final UserCredentialsValidator userCredentialsValidator;
    private final ActivateDeactivateProfileValidator activateDeactivateProfileValidator;

    @InitBinder("changePasswordRequest")
    public void initChangePasswordValidatorBinder(WebDataBinder binder) {
        binder.addValidators(changePasswordValidator);
    }

    @InitBinder("userCredentials")
    public void initUserCredentialsBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsValidator);
    }

    @InitBinder("activateDeactivateProfileRequest")
    public void initActivateDeactivateValidatorBinder(WebDataBinder binder) {
        binder.addValidators(activateDeactivateProfileValidator);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated UserCredentials credentials,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        service.authenticate(credentials, bindingResult);

        String username = credentials.getUsername();
        HttpHeaders headers = new HttpHeaders();
        accessToken(username, headers);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @Override
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Validated ChangePasswordRequest request,
                                            BindingResult bindingResult) {
        User sessionUser = getAuthenticatedUser();
        service.changePassword(request, bindingResult, sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PatchMapping("/{username}/activate")
    public ResponseEntity<?> activateDeactivateProfile(@PathVariable String username,
                                                       @RequestBody @Validated ActivateDeactivateProfileRequest request,
                                                       BindingResult bindingResult) {
        User sessionUser = getAuthenticatedUser();
        service.activateDeactivateProfile(username, request, bindingResult, sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private void accessToken(String username, HttpHeaders headers) {
        String token = service.generateAccessToken(username);
        headers.set("Authorization", "Bearer " + token);
    }
}
