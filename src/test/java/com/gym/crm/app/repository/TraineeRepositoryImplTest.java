package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.utils.EntityTestData;
import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class TraineeRepositoryImplTest extends AbstractTestRepository<TraineeRepository> {

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = EntityTestData.getTransientTraineeJohnDoe();
        entityManager.persist(expected);

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
        List<Trainee> trainees = addTraineeList();

        // when
        List<Trainee> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual).containsAll(trainees);
    }

    @Test
    @DisplayName("Test find trainings by criteria functionality")
    public void givenValidCriteria_whenFindTrainings_thenReturnTrainings() {
        // given
        List<Training> trainings = addTrainingList();
        Training training = trainings.get(1);

        String username = training.getTrainee().getUser().getUsername();
        LocalDate from = training.getTrainingDate();
        LocalDate to = training.getTrainingDate();
        String trainerName = training.getTrainer().getUser().getFirstName();
        String trainingType = training.getTrainingType().getTrainingTypeName();

        // when
        List<Training> actual = repository.findTrainingsByCriteria(username, from, to, trainerName, trainingType);

        // then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).contains(training);
    }

    @Test
    @DisplayName("Test find trainings by null criteria functionality")
    public void givenNullCriteria_whenFindTrainings_thenReturnTrainings() {
        // given
        addTrainingList();

        // when
        List<Training> trainings = repository.findTrainingsByCriteria(null, null, null, null, null);

        // then
        assertThat(trainings.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test find trainings by blank criteria functionality")
    public void givenBlankCriteria_whenFindTrainings_thenReturnTrainings() {
        // given
        addTrainingList();

        // when
        List<Training> trainings = repository.findTrainingsByCriteria("", null, null, "", "");

        // then
        assertThat(trainings.size()).isEqualTo(2);
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
    public void givenTrainee_whenUpdateTrainee_thenTraineeIsUpdated() {
        // given
        Trainee traineeToSave = EntityTestData.getTransientTraineeJohnDoe();
        entityManager.persist(traineeToSave);

        String updatedAddress = "updated address";

        Trainee traineeToUpdate = entityManager.find(Trainee.class, traineeToSave.getId());
        traineeToUpdate = traineeToUpdate.toBuilder().address(updatedAddress).build();

        // when
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
        entityManager.persist(trainee);

        // when
        repository.deleteById(trainee.getId());

        // then
        entityManager.clear();
        Trainee actual = entityManager.find(Trainee.class, trainee.getId());

        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test find trainee by username functionality")
    public void givenUsername_whenFindByUsername_thenTraineeIsFound() {
        // given
        Trainee expected = EntityTestData.getTransientTraineeJohnDoe();
        entityManager.persist(expected);

        // when
        Optional<Trainee> actual = repository.findByUsername(expected.getUser().getUsername());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainee by incorrect username functionality")
    public void givenIncorrectUsername_whenFindByUsername_thenTraineeIsNotFound() {
        // given
        Trainee expected = EntityTestData.getTransientTraineeJohnDoe();
        entityManager.persist(expected);

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
        entityManager.persist(trainee);
        String username = trainee.getUser().getUsername();

        // when
        repository.deleteByUsername(username);

        // then
        entityManager.clear();
        Trainee actual = entityManager.find(Trainee.class, trainee.getId());

        assertThat(actual).isNull();
    }

    private List<Trainee> addTraineeList() {
        Trainee trainee1 = EntityTestData.getTransientTraineeJohnDoe();
        Trainee trainee2 = EntityTestData.getTransientTraineeJaneSmith();
        Trainee trainee3 = EntityTestData.getTransientTraineeMichaelJohnson();

        entityManager.persist(trainee1);
        entityManager.persist(trainee2);
        entityManager.persist(trainee3);

        return List.of(trainee1, trainee2, trainee3);
    }

    private List<Training> addTrainingList() {
        Training training1 = EntityTestData.getTransientTrainingEmilyDavis();
        Training training2 = EntityTestData.getTransientTrainingDavidBrown();

        entityManager.persist(training1);
        entityManager.persist(training2);

        return List.of(training1, training2);
    }
}
