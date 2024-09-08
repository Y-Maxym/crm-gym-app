package com.gym.crm.app.model.service.implementation;

import com.gym.crm.app.exception.EntityNotFoundException;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.util.MessageUtils;
import com.gym.crm.app.util.UserUtils;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_USER_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private MessageUtils messageUtils;

    @Mock
    private UserUtils userUtils;

    @Mock
    private EntityDao<Long, User> repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    @DisplayName("Test find user by id functionality")
    public void givenId_whenFindById_thenUserIsReturned() {
        // given
        User expected = DataUtils.getUserJohnDoe();
        Long id = expected.getId();

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

        given(repository.findById(id))
                .willReturn(Optional.empty());

        given(messageUtils.getMessage(ERROR_USER_WITH_ID_NOT_FOUND, id))
                .willReturn(message);

        // when
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save user with full data functionality")
    public void givenSaveUserWithFullData_whenSave_thenRepositoryIsCalled() {
        // given
        User userToSave = DataUtils.getUserJohnDoe();
        String username = userToSave.getFirstName() + "." + userToSave.getLastName();
        String password = "1234567890";
        userToSave.setUsername(username);
        userToSave.setPassword(password);

        // when
        service.save(userToSave);

        // then
        verify(repository, only()).saveOrUpdate(userToSave);
    }

    @Test
    @DisplayName("Test save user without username and password functionality")
    public void givenSaveUserWithoutUsernamePassword_whenSave_thenRepositoryIsCalled() {
        // given
        User userToSave = DataUtils.getUserJohnDoe();
        String firstName = userToSave.getFirstName();
        String lastName = userToSave.getLastName();
        String username = "%s.%s".formatted(firstName, lastName);
        int passwordLength = 10;
        String password = "1234567890";

        given(userUtils.generateUsername(firstName, lastName))
                .willReturn(username);

        given(userUtils.generatePassword(passwordLength))
                .willReturn(password);

        // when
        service.save(userToSave);

        // then
        assertThat(userToSave.getUsername()).isEqualTo(username);
        assertThat(userToSave.getPassword()).isEqualTo(password);

        verify(repository, only()).saveOrUpdate(userToSave);
        verify(userUtils, times(1)).generateUsername(userToSave.getFirstName(), userToSave.getLastName());
        verify(userUtils, times(1)).generatePassword(10);
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUpdatedUser_whenUpdate_thenRepositoryIsCalled() {
        // given
        User userToUpdate = DataUtils.getUserJohnDoe();

        // when
        service.update(userToUpdate);

        // then
        verify(repository, only()).saveOrUpdate(userToUpdate);
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    public void givenId_whenDeleteById_thenRepositoryIsCalled() {
        // given
        Long id = 1L;

        // when
        service.deleteById(id);

        // then
        verify(repository, only()).deleteById(id);
    }

}