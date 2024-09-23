package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
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
class TrainerRepositoryImplTest extends AbstractTestRepository<TrainerRepository> {

    @Test
    @DisplayName("Test find trainer by id functionality")
    public void givenId_whenFindById_thenTrainerIsReturned() {
        // given
        Trainer expected = EntityTestData.getTransientTrainerEmilyDavis();
        entityManager.persist(expected);

        // when
        Optional<Trainer> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainer by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Trainer expected = EntityTestData.getPersistedTrainerDavidBrown();

        // when
        Optional<Trainer> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all trainer functionality")
    public void givenTrainers_whenFindAll_thenTrainersIsReturned() {
        // given
        Trainer trainer1 = EntityTestData.getTransientTrainerEmilyDavis();
        Trainer trainer2 = EntityTestData.getTransientTrainerDavidBrown();

        entityManager.persist(trainer1);
        entityManager.persist(trainer2);

        // when
        List<Trainer> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).containsAll(List.of(trainer1, trainer2));
    }

    @Test
    @DisplayName("Test save trainer without id functionality")
    public void givenTrainer_whenSaveTrainer_thenTrainerIsSaved() {
        // given
        Trainer trainer = EntityTestData.getTransientTrainerEmilyDavis();

        // when
        Trainer actual = repository.save(trainer);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test save trainer with id functionality")
    public void givenTrainerWithId_whenSaveTrainer_thenExceptionIsThrown() {
        // given
        Trainer trainer = EntityTestData.getPersistedTrainerEmilyDavis();

        // when
        InvalidDataAccessApiUsageException ex = assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(trainer));

        // then
        assertThat(ex).hasCauseInstanceOf(PersistentObjectException.class);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenTrainer_whenUpdateTrainer_thenTrainerIsUpdated() {
        // given
        Trainer trainer = EntityTestData.getTransientTrainerEmilyDavis();
        entityManager.persist(trainer);

        // when
        Trainer actual = repository.update(trainer);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Test delete trainer by id functionality")
    public void givenId_whenDeleteById_thenTrainerIsDeleted() {
        // given
        Trainer trainer = EntityTestData.getTransientTrainerEmilyDavis();
        entityManager.persist(trainer);

        // when
        repository.deleteById(trainer.getId());

        // then
        entityManager.clear();
        Trainer actual = entityManager.find(Trainer.class, trainer.getId());

        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test find trainer by username functionality")
    public void givenUsername_whenFindByUsername_thenTrainerIsFound() {
        // given
        Trainer expected = EntityTestData.getTransientTrainerEmilyDavis();
        entityManager.persist(expected);

        // when
        Optional<Trainer> actual = repository.findByUsername(expected.getUser().getUsername());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainer by incorrect username functionality")
    public void givenIncorrectUsername_whenFindByUsername_thenTrainerIsFound() {
        // given
        Trainer expected = EntityTestData.getTransientTrainerEmilyDavis();
        entityManager.persist(expected);

        // when
        Optional<Trainer> actual = repository.findByUsername("username");

        // then
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test get trainers not assigned by trainee username")
    public void givenTrainers_whenGetTrainers_thenTrainersIsReturned() {
        // given
        Training training1 = EntityTestData.getTransientTrainingEmilyDavis();
        Training training2 = EntityTestData.getTransientTrainingDavidBrown();

        entityManager.persist(training1);
        entityManager.persist(training2);

        String username = "John.Doe";

        // when
        List<Trainer> actual = repository.getTrainersNotAssignedByTraineeUsername(username);

        // then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getUser().getUsername()).isEqualTo("Emily.Davis");
    }
}
