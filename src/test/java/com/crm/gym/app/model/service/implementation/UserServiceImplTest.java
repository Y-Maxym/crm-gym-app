package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.util.MessageUtils;
import com.crm.gym.app.util.UserUtils;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        Long id = 1L;
        User expected = DataUtils.getUserJohnDoe();

        BDDMockito.given(repository.findById(anyLong()))
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

        BDDMockito.given(repository.findById(anyLong()))
                .willReturn(Optional.empty());

        BDDMockito.given(messageUtils.getMessage(anyString(), any()))
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
        User user = DataUtils.getUserJohnDoe();
        String username = user.getFirstName() + "." + user.getLastName();
        String password = "1234567890";
        user.setUsername(username);
        user.setPassword(password);

        BDDMockito.doNothing().when(repository).save(any(User.class));

        // when
        service.save(user);

        // then
        verify(repository, only()).save(user);
    }

    @Test
    @DisplayName("Test save user without username and password functionality")
    public void givenSaveUserWithoutUsernamePassword_whenSave_thenRepositoryIsCalled() {
        // given
        User user = DataUtils.getUserJohnDoe();
        String username = user.getFirstName() + "." + user.getLastName();
        String password = "1234567890";

        BDDMockito.doNothing().when(repository).save(any(User.class));

        BDDMockito.given(userUtils.generateUsername(anyString(), anyString()))
                .willReturn(username);

        BDDMockito.given(userUtils.generatePassword(anyInt()))
                .willReturn(password);

        // when
        service.save(user);

        // then
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPassword()).isEqualTo(password);

        verify(repository, only()).save(user);
        verify(userUtils, times(1)).generateUsername(user.getFirstName(), user.getLastName());
        verify(userUtils, times(1)).generatePassword(10);
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUpdatedUser_whenUpdate_thenRepositoryIsCalled() {
        // given
        User user = DataUtils.getUserJohnDoe();

        BDDMockito.doNothing().when(repository).update(any(User.class));

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

        BDDMockito.doNothing().when(repository).deleteById(anyLong());

        // when
        service.deleteById(id);

        // then
        verify(repository, only()).deleteById(id);
    }

}