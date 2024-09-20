package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;
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
class TraineeDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TraineeDao repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = EntityTestData.getTransientTraineeJohnDoe();
        repository.save(expected);

        // when
        Optional<Trainee> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainee by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Trainee expected = EntityTestData.getPersistedTraineeJohnDoe();

        // when
        Optional<Trainee> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all trainee functionality")
    public void givenTrainees_whenFindAll_thenTraineesIsReturned() {
        // given
        Trainee trainee1 = EntityTestData.getTransientTraineeJohnDoe();
        Trainee trainee2 = EntityTestData.getTransientTraineeJaneSmith();
        Trainee trainee3 = EntityTestData.getTransientTraineeMichaelJohnson();

        repository.saveAll(trainee1, trainee2, trainee3);

        // when
        List<Trainee> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual).containsAll(List.of(trainee1, trainee2, trainee3));
    }

    @Test
    @DisplayName("Test save trainee without id functionality")
    public void givenTrainee_whenSaveTrainee_thenTraineeIsSaved() {
        // given
        Trainee trainee = EntityTestData.getTransientTraineeJohnDoe();

        // when
        Trainee actual = repository.save(trainee);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test save trainee with id functionality")
    public void givenTraineeWithId_whenSaveTrainee_thenExceptionIsThrown() {
        // given
        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();

        // when
        InvalidDataAccessApiUsageException ex = assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(trainee));

        // then
        assertThat(ex).hasCauseInstanceOf(PersistentObjectException.class);
    }

    @Test
    @DisplayName("Test update trainee functionality")
    @SuppressWarnings("all")
    public void givenTrainee_whenUpdateTrainee_thenTraineeIsUpdated() {
        // given
        Trainee traineeToSave = EntityTestData.getTransientTraineeJohnDoe();
        repository.save(traineeToSave);

        String updatedAddress = "updated address";

        // when
        Trainee traineeToUpdate = repository.findById(traineeToSave.getId())
                .orElse(null);
        traineeToUpdate = traineeToUpdate.toBuilder().address(updatedAddress).build();

        Trainee actual = repository.update(traineeToUpdate);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getAddress()).isEqualTo(updatedAddress);
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
    public void givenId_whenDeleteById_thenTraineeIsDeleted() {
        // given
        Trainee trainee = EntityTestData.getTransientTraineeJohnDoe();
        repository.save(trainee);

        // when
        repository.deleteById(trainee.getId());

        entityManager.clear();

        Optional<Trainee> actual = repository.findById(trainee.getId());

        // then
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
    public void whenDeleteAll_thenTraineesIsDeleted() {
        // given
        Trainee trainee1 = EntityTestData.getTransientTraineeJohnDoe();
        Trainee trainee2 = EntityTestData.getTransientTraineeJaneSmith();
        Trainee trainee3 = EntityTestData.getTransientTraineeMichaelJohnson();

        repository.saveAll(trainee1, trainee2, trainee3);

        // when
        repository.deleteAll();

        entityManager.clear();

        List<Trainee> actual = repository.findAll();

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test find trainee by username functionality")
    public void givenUsername_whenFindByUsername_thenTraineeIsFound() {
        // given
        Trainee expected = EntityTestData.getTransientTraineeJohnDoe();
        repository.save(expected);

        // when
        Optional<Trainee> actual = repository.findByUsername(expected.getUser().getUsername());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainee by incorrect username functionality")
    public void givenIncorrectUsername_whenFindByUsername_thenTraineeIsFound() {
        // given
        Trainee expected = EntityTestData.getTransientTraineeJohnDoe();
        repository.save(expected);

        // when
        Optional<Trainee> actual = repository.findByUsername("username");

        // then
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test delete trainee by username functionality")
    public void givenUsername_whenDeleteByUsername_thenTraineeIsDeleted() {
        // given
        Trainee trainee = EntityTestData.getTransientTraineeJohnDoe();
        repository.save(trainee);

        String username = trainee.getUser().getUsername();
        // when
        repository.deleteByUsername(username);

        entityManager.clear();

        Optional<Trainee> actual = repository.findById(trainee.getId());

        // then
        assertThat(actual.isEmpty()).isTrue();
    }
}
