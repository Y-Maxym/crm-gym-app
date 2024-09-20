package com.gym.crm.app.repository;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.utils.EntityTestData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDao repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test find user by id functionality")
    public void givenId_whenFindById_thenUserIsReturned() {
        // given
        User expected = EntityTestData.getTransientUserJohnDoe();
        repository.save(expected);

        // when
        Optional<User> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find user by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        User expected = EntityTestData.getPersistedUserJohnDoe();

        // when
        Optional<User> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all user functionality")
    public void givenUsers_whenFindAll_thenUsersIsReturned() {
        // given
        User user1 = EntityTestData.getTransientUserJohnDoe();
        User user2 = EntityTestData.getTransientUserJaneSmith();
        User user3 = EntityTestData.getTransientUserMichaelJohnson();

        repository.saveAll(user1, user2, user3);

        // when
        List<User> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual).containsAll(List.of(user1, user2, user3));
    }

    @Test
    @DisplayName("Test save user without id functionality")
    public void givenUser_whenSaveUser_thenUserIsSaved() {
        // given
        User user = EntityTestData.getTransientUserJohnDoe();

        // when
        User actual = repository.save(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test save user with id functionality")
    public void givenUserWithId_whenSaveUser_thenExceptionIsThrown() {
        // given
        User user = EntityTestData.getPersistedUserJohnDoe();

        // when
        InvalidDataAccessApiUsageException ex = assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(user));

        // then
        assertThat(ex).hasCauseInstanceOf(PersistentObjectException.class);
    }

    @Test
    @DisplayName("Test update user functionality")
    @SuppressWarnings("all")
    public void givenUser_whenUpdateUser_thenUserIsUpdated() {
        // given
        User userToSave = EntityTestData.getTransientUserJohnDoe();
        repository.save(userToSave);

        // when
        User userToUpdate = repository.findById(userToSave.getId())
                .orElse(null);
        userToUpdate = userToUpdate.toBuilder().isActive(false).build();

        User actual = repository.update(userToUpdate);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isActive()).isFalse();
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    public void givenId_whenDeleteById_thenUserIsDeleted() {
        // given
        User user = EntityTestData.getTransientUserJohnDoe();
        repository.save(user);

        // when
        repository.deleteById(user.getId());

        entityManager.clear();

        Optional<User> actual = repository.findById(user.getId());

        // then
        assertThat(actual.isEmpty()).isTrue();
    }
}
