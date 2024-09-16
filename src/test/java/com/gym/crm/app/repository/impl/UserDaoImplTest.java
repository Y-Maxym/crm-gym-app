package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.storage.Storage;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class UserDaoImplTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private UserDaoImpl repository;

    @Test
    @DisplayName("Test find user by id functionality")
    public void givenId_whenFindById_thenUserIsReturned() {
        // given
        User expected = DataUtils.getUserJohnDoePersisted();
        long id = expected.getId();

        given(storage.get(id, User.class))
                .willReturn(expected);

        // when
        Optional<User> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find user by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        long id = 1L;

        given(storage.get(id, User.class))
                .willReturn(null);

        // when
        Optional<User> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all user functionality")
    public void givenUsers_whenFindAll_thenUsersIsReturned() {
        // given
        User user1 = DataUtils.getUserJohnDoePersisted();
        User user2 = DataUtils.getUserJaneSmithPersisted();
        User user3 = DataUtils.getUserMichaelJohnsonPersisted();

        List<User> expected = List.of(user1, user2, user3);

        given(storage.getAll(User.class))
                .willReturn(expected);

        // when
        List<User> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save user without id functionality")
    public void givenUser_whenSaveUser_thenStorageIsCalled() {
        // given
        User user = spy(DataUtils.getUserJohnDoeTransient());
        User persisted = DataUtils.getUserJohnDoePersisted();

        given(storage.put(anyLong(), any(User.class)))
                .willReturn(persisted);

        // when
        User actual = repository.save(user);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualTo(persisted);

        verify(user).toBuilder();
        verify(storage, only()).put(anyLong(), any(User.class));
    }

    @Test
    @DisplayName("Test save user with id functionality")
    public void givenUserWithId_whenSaveUser_thenStorageIsCalled() {
        // given
        User user = spy(DataUtils.getUserJohnDoePersisted());
        long id = user.getId();

        given(storage.put(id, user))
                .willReturn(user);

        // when
        User actual = repository.save(user);

        // then
        assertThat(actual).isEqualTo(user);

        verify(user, never()).toBuilder();
        verify(storage, only()).put(id, user);
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUser_whenUpdateUser_thenStorageIsCalled() {
        // given
        User user = spy(DataUtils.getUserJohnDoePersisted());
        long id = user.getId();

        given(storage.put(id, user))
                .willReturn(user);

        // when
        User actual = repository.update(user);

        // then
        assertThat(actual).isEqualTo(user);

        verify(storage, only()).put(id, user);
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        long id = 1L;

        doNothing().when(storage).remove(id, User.class);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id, User.class);
    }
}
