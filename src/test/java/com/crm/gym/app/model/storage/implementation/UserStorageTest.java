package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserStorageTest {

    @InjectMocks
    private UserStorage userStorage;

    @Test
    @DisplayName("Test get user by key functionality")
    public void givenId_whenGet_thenUserIsReturned() {
        // given
        User user = DataUtils.getUserDavidBrown();
        Long id = user.getId();

        userStorage.put(id, user);

        // when
        User actual = userStorage.get(id);

        // then
        assertThat(actual).isEqualTo(user);
    }

    @Test
    @DisplayName("Test get all users functionality")
    public void givenUsers_whenGetAll_thenUsersIsReturned() {
        // given
        User user1 = DataUtils.getUserDavidBrown();
        User user2 = DataUtils.getUserEmilyDavis();

        userStorage.put(user1.getId(), user1);
        userStorage.put(user2.getId(), user2);

        // when
        List<User> actual = userStorage.getAll();

        // then
        assertThat(actual).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    @DisplayName("Test put user functionality")
    public void givenUser_whenPut_thenNullIsReturned() {
        // given
        User expected = DataUtils.getUserDavidBrown();

        // when
        User previous = userStorage.put(expected.getId(), expected);
        User actual = userStorage.get(expected.getId());

        // then
        assertThat(previous).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test put user functionality")
    public void givenId_whenRemove_thenUserIsRemoved() {
        // given
        User expected = DataUtils.getUserDavidBrown();
        Long id = expected.getId();

        userStorage.put(id, expected);

        // when
        userStorage.remove(id);

        User actual = userStorage.get(id);

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test clear user storage functionality")
    public void givenUsers_whenClear_thenUsersIsRemoved() {
        // given
        User user1 = DataUtils.getUserDavidBrown();
        User user2 = DataUtils.getUserEmilyDavis();

        userStorage.put(user1.getId(), user1);
        userStorage.put(user2.getId(), user2);

        // when
        userStorage.clear();

        List<User> actual = userStorage.getAll();

        // then
        assertThat(actual).isEmpty();
    }
}