package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.service.EntityExceptionHelper;
import com.gym.crm.app.service.UserProfileService;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_USER_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private EntityExceptionHelper exceptionHelper;

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private EntityDao<Long, User> repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    @DisplayName("Test find user by id functionality")
    public void givenId_whenFindById_thenUserIsReturned() {
        // given
        User expected = DataUtils.getUserJohnDoePersisted();
        Long id = expected.getId();

        doNothing().when(exceptionHelper).checkId(id);

        given(repository.findById(id))
                .willReturn(Optional.of(expected));

        // when
        User actual = service.findById(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find user by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        Long id = 1L;
        String message = "User with id %s not found".formatted(id);

        doNothing().when(exceptionHelper).checkId(id);

        given(repository.findById(id))
                .willReturn(Optional.empty());

        given(messageHelper.getMessage(ERROR_USER_WITH_ID_NOT_FOUND, id))
                .willReturn(message);

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save user functionality")
    public void givenSaveUser_whenSave_thenRepositoryIsCalled() {
        // given
        User user = DataUtils.getUserJohnDoePersisted();
        String username = user.getFirstName() + "." + user.getLastName();
        String password = "1234567890";

        user = user.toBuilder().username(username).password(password).build();

        doNothing().when(exceptionHelper).checkEntity(user);

        // when
        service.save(user);

        // then
        verify(repository, only()).save(user);
    }

    @Test
    @DisplayName("Test prepare user without username and password functionality")
    public void givenSaveUserWithoutUsernamePassword_whenSave_thenRepositoryIsCalled() {
        // given
        User user = DataUtils.getUserJohnDoeTransient();

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        int passwordLength = 10;
        String password = "1234567890";

        given(repository.findAll())
                .willReturn(Collections.emptyList());

        given(userProfileService.generatePassword(passwordLength))
                .willReturn(password);

        // when
        User actual = ReflectionTestUtils.invokeMethod(service, "prepareUserForSave", user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo("%s.%s", firstName, lastName);
        assertThat(actual.getPassword()).isEqualTo(password);

        verify(userProfileService, never()).generateUsernameWithSerialNumber(user.getFirstName(), user.getLastName());
        verify(userProfileService, times(1)).generatePassword(10);
    }

    @Test
    @DisplayName("Test prepare user with full data functionality")
    public void givenSaveUserWithFullData_whenPrepareUserForSave_thenUserIsReturned() {
        // given
        User persisted = DataUtils.getUserJohnDoePersisted();
        String username = persisted.getFirstName() + "." + persisted.getLastName();
        String password = "1234567890";

        User user = persisted.toBuilder().username(username).password(password).build();

        // when
        User actual = ReflectionTestUtils.invokeMethod(service, "prepareUserForSave", user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(user);
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUpdatedUser_whenUpdate_thenRepositoryIsCalled() {
        // given
        User user = DataUtils.getUserJohnDoePersisted();

        doNothing().when(exceptionHelper).checkEntity(user);
        doNothing().when(exceptionHelper).checkId(user.getId());

        // when
        service.update(user);

        // then
        verify(repository, only()).update(user);
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    public void givenId_whenDeleteById_thenRepositoryIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(exceptionHelper).checkId(id);

        // when
        service.deleteById(id);

        // then
        verify(repository, only()).deleteById(id);
    }

    @Test
    @DisplayName("Test generate username without duplication")
    public void givenFirstNameAndLastName_whenGenerateUsername_thenReturnsUsernameWithSerialNumberWithoutNumber() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        given(repository.findAll())
                .willReturn(Collections.emptyList());

        // when
        String username = ReflectionTestUtils.invokeMethod(service, "generateUsername", firstName, lastName);

        // then
        assertThat(username).isEqualTo("John.Doe");
    }

    @Test
    @DisplayName("Test generate username with duplication")
    public void givenDuplicatedUsername_whenGenerateUsername_thenUserProfileServiceIsCalled() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        User existingUser = User.builder()
                .username("John.Doe")
                .build();

        given(repository.findAll())
                .willReturn(Collections.singletonList(existingUser));


        // when
        ReflectionTestUtils.invokeMethod(service, "generateUsername", firstName, lastName);

        // then
        verify(userProfileService, times(1)).generateUsernameWithSerialNumber(firstName, lastName);
    }

    @Test
    @DisplayName("Test is duplicated username when username is not duplicated")
    public void givenUsername_whenIsDuplicatedUsername_thenReturnsFalse() {
        // given
        String username = "uniqueUsername";

        given(repository.findAll())
                .willReturn(Collections.emptyList());

        // when

        Boolean isDuplicated = ReflectionTestUtils.invokeMethod(service, "isDuplicatedUsername", username);

        // then
        assertThat(isDuplicated).isFalse();
    }

    @Test
    @DisplayName("Test isDuplicatedUsername when username is duplicated")
    public void givenUsername_whenIsDuplicatedUsername_thenReturnsTrue() {
        // given
        String username = "John.Doe";

        User existingUser = User.builder()
                .username("John.Doe")
                .build();

        given(repository.findAll())
                .willReturn(Collections.singletonList(existingUser));

        // when
        Boolean isDuplicated = ReflectionTestUtils.invokeMethod(service, "isDuplicatedUsername", username);

        // then
        assertThat(isDuplicated).isTrue();
    }
}
