package com.gym.crm.app.repository;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.utils.EntityTestData;
import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class UserRepositoryImplTest extends AbstractTestRepository<UserRepository> {

    @Test
    @DisplayName("Test find user by id functionality")
    public void givenId_whenFindById_thenUserIsReturned() {
        // given
        User expected = EntityTestData.getTransientUserJohnDoe();
        entityManager.persist(expected);

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

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

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
    public void givenUser_whenUpdateUser_thenUserIsUpdated() {
        // given
        User userToSave = EntityTestData.getTransientUserJohnDoe();
        entityManager.persist(userToSave);

        User userToUpdate = entityManager.find(User.class, userToSave.getId());
        userToUpdate = userToUpdate.toBuilder().isActive(false).build();

        // when
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
        entityManager.persist(user);

        // when
        repository.deleteById(user.getId());

        // then
        entityManager.clear();
        User actual = entityManager.find(User.class, user.getId());

        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test find user by username functionality")
    public void givenUsername_whenFindByUsername_thenUserIsFound() {
        // given
        User expected = EntityTestData.getTransientUserJohnDoe();
        entityManager.persist(expected);

        // when
        Optional<User> actual = repository.findByUsername(expected.getUsername());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find user by incorrect username functionality")
    public void givenIncorrectUsername_whenFindByUsername_thenUserIsNotFound() {
        // given
        User expected = EntityTestData.getTransientUserJohnDoe();
        entityManager.persist(expected);

        // when
        Optional<User> actual = repository.findByUsername("username");

        // then
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test get next serial number")
    void whenGetNextSerialNumber_thenUserIsReturned() {
        // when
        Long actual = repository.getNextSerialNumber();

        // then
        assertThat(actual).isNotNull();
    }
}